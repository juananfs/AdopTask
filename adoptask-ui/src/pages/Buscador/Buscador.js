import './Buscador.css'
import { useState, useRef, useCallback, useEffect } from 'react';
import Header from '../../components/Header/Header';
import { Form, Button, Image, Spinner, Offcanvas } from 'react-bootstrap';
import Select from 'react-select';
import { Search } from 'lucide-react';
import Filtros from '../../components/Buscador/Filtros';
import Footer from '../../components/Footer/Footer';
import AnimalModal from '../../components/Buscador/AnimalModal';

const Buscador = () => {
    const [busqueda, setBusqueda] = useState('');
    const [busquedaTemp, setBusquedaTemp] = useState('');
    const [orden, setOrden] = useState({ orden: 'fechaPublicacion', ascendente: false });
    const [expanded, setExpanded] = useState(false);
    const [categorias, setCategorias] = useState([]);
    const [sexos, setSexos] = useState([]);
    const [protectoras, setProtectoras] = useState([]);
    const [animales, setAnimales] = useState([]);
    const [page, setPage] = useState(0);
    const [isLoading, setIsLoading] = useState(true);
    const [hasMore, setHasMore] = useState(true);
    const [loadError, setLoadError] = useState('');
    const [animalSelected, setAnimalSelected] = useState('');
    const [animalShow, setAnimalShow] = useState(false);

    const prevPage = useRef(-1);
    const initialLoadDone = useRef(false);
    const containerRef = useRef(null);

    const fetchProtectoras = useCallback(() => {
        if (!initialLoadDone.current) {
            initialLoadDone.current = true;
            return;
        }

        if (page === prevPage.current) {
            setAnimales([]);
            if (page !== 0) {
                setPage(0);
                return;
            }
        }

        setLoadError('');
        setIsLoading(true);

        const busquedaData = {
            busqueda: busqueda,
            orden: orden.orden,
            ascendente: orden.ascendente,
            categorias: categorias,
            sexos: sexos,
            protectoras: protectoras,
            page: page,
            size: 10
        };

        fetch('/publicaciones/busqueda', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(busquedaData)
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error("Ocurrió un error en el servidor. Intentalo más tarde.");
            })
            .then(data => {
                if (data._embedded)
                    setAnimales((prev) => [...prev, ...data._embedded.resumenAnimalDtoList]);
                setHasMore(data.page.number < data.page.totalPages - 1);
            })
            .catch(error => {
                setLoadError(error.message);
            })
            .finally(() => setIsLoading(false));

        prevPage.current = page;
    }, [busqueda, orden, categorias, sexos, protectoras, page]);

    useEffect(() => {
        fetchProtectoras();
    }, [fetchProtectoras]);

    const handleScroll = useCallback(() => {
        if (isLoading || !hasMore) return;

        const container = containerRef.current;
        if (container) {
            const scrollTop = container.scrollTop;
            const scrollHeight = container.scrollHeight;
            const clientHeight = container.clientHeight;

            if (scrollTop + clientHeight >= scrollHeight - 100) {
                setPage((prev) => prev + 1);
            }
        }
    }, [isLoading, hasMore]);

    useEffect(() => {
        const container = containerRef.current;

        if (container && !isLoading && hasMore) {
            const scrollHeight = container.scrollHeight;
            const clientHeight = container.clientHeight;

            if (scrollHeight - 160 <= clientHeight) {
                setPage((prev) => prev + 1);
            }
        }
    }, [isLoading, hasMore]);

    useEffect(() => {
        const container = containerRef.current;

        if (container) {
            container.addEventListener('scroll', handleScroll);
        }

        return () => {
            if (container) {
                container.removeEventListener('scroll', handleScroll);
            }
        };
    }, [handleScroll]);

    const handleBusqueda = (event) => {
        event.preventDefault();
        setBusqueda(busquedaTemp);
    };

    const ordenOptions = [
        { value: { orden: 'fechaPublicacion', ascendente: false }, label: 'Más Recientes' },
        { value: { orden: 'fechaPublicacion', ascendente: true }, label: 'Menos Recientes' },
        { value: { orden: 'likes', ascendente: false }, label: 'Más Populares' },
        { value: { orden: 'likes', ascendente: true }, label: 'Menos Populares' }
    ];

    const customStyles = {
        control: (provided, state) => ({
            ...provided,
            borderColor: state.isFocused ? '#629677' : '#dee2e6',
            boxShadow: state.isFocused ? '0 0 0 0.25rem rgba(40, 167, 69, 0.25)' : 'none',
            '&:hover': {
                borderColor: '#629677',
            }
        })
    };

    const customTheme = (theme) => ({
        ...theme,
        borderRadius: 5,
        colors: {
            ...theme.colors,
            primary25: '#f2f4f3',
            primary50: '#357266',
            primary: '#629677'
        }
    });

    const handleOpen = (idAnimal) => {
        setAnimalSelected(idAnimal);
        setAnimalShow(true);
    };

    const onHideAnimal = () => {
        setAnimalSelected('');
        setAnimalShow(false)
    }

    return (
        <div id='buscador'>
            <Header />
            <div id='header'>
                <h2>Animales</h2>
                <Form id='buscador-form' onSubmit={handleBusqueda}>
                    <Form.Control
                        type="text"
                        placeholder="Buscar"
                        value={busquedaTemp}
                        onChange={(e) => setBusquedaTemp(e.target.value)}
                    />
                    <Button variant='light' type="submit"><Search /></Button>
                </Form>
                <div className='d-flex gap-1'>
                    <Button onClick={() => setExpanded(true)} variant='light' className='d-xl-none'>
                        Filtrar
                    </Button>
                    <Select
                        value={ordenOptions.find(option =>
                            option.value.orden === orden.orden && option.value.ascendente === orden.ascendente
                        )}
                        onChange={(selectedOption) => setOrden(selectedOption.value)}
                        options={ordenOptions}
                        styles={customStyles}
                        theme={customTheme}
                        className='select'
                    />
                </div>
            </div>
            <div ref={containerRef} id='container-1' className='contenido'>
                <div id='container-2'>
                    <Offcanvas
                        id='offcanvas-filtros'
                        show={expanded}
                        onHide={() => setExpanded(false)}
                        responsive='xl'
                    >
                        <Offcanvas.Header closeButton />
                        <Offcanvas.Body>
                            <Filtros
                                categorias={categorias}
                                setCategorias={setCategorias}
                                sexos={sexos}
                                setSexos={setSexos}
                                protectoras={protectoras}
                                setProtectoras={setProtectoras}
                            />
                        </Offcanvas.Body>
                    </Offcanvas>
                    {loadError ?
                        <p className='text-center'>{loadError}</p>
                        :
                        <div id='animales'>
                            {animales.map((animal) => (
                                <Image
                                    key={animal.id}
                                    src={`/protectoras/${animal.idProtectora}/animales/${animal.id}/imagenes/${animal.imagen}`}
                                    alt={animal.nombre}
                                    onClick={() => handleOpen(animal.id)}
                                    rounded
                                />
                            ))}
                            {isLoading && <Spinner animation="grow" variant="dark" />}
                        </div>
                    }
                </div>
                <Footer />
            </div>
            <AnimalModal
                id='publicacion'
                show={animalShow}
                onHide={onHideAnimal}
                idAnimal={animalSelected}
            />
        </div>
    );
};

export default Buscador;