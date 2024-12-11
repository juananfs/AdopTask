import './Animales.css';
import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import { useState, useRef, useEffect, useCallback } from 'react';
import Layout from '../Layout';
import Select from 'react-select';
import { Button, Image, Spinner } from 'react-bootstrap';
import { Plus } from 'lucide-react';
import Footer from '../../../components/Footer/Footer';
import AltaModal from '../../../components/Gestión/Animales/AltaModal';
import AnimalModal from '../../../components/Gestión/Animales/AnimalModal';

const Animales = () => {
    const { token, logout, isAdmin, permisos } = useAuth();
    const { id } = useParams();

    const [categoria, setCategoria] = useState('');
    const [estado, setEstado] = useState('');
    const [altaShow, setAltaShow] = useState(false);
    const [animales, setAnimales] = useState([]);
    const [page, setPage] = useState(0);
    const [isLoading, setIsLoading] = useState(true);
    const [hasMore, setHasMore] = useState(true);
    const [loadError, setLoadError] = useState('');
    const [animalSelected, setAnimalSelected] = useState('');
    const [animalShow, setAnimalShow] = useState(false);

    const initialFetch = useRef(true);
    const containerRef = useRef(null);

    const categoriaOptions = [
        { value: '', label: 'Todas' },
        { value: 'PERROS', label: 'Perros' },
        { value: 'GATOS', label: 'Gatos' },
        { value: 'ROEDORES', label: 'Roedores' },
        { value: 'AVES', label: 'Aves' },
        { value: 'GRANJA', label: 'Granja' },
        { value: 'HURONES', label: 'Hurones' },
        { value: 'REPTILES', label: 'Reptiles' },
        { value: 'ACUATICOS', label: 'Acuáticos' },
        { value: 'SILVESTRES', label: 'Silvestres' },
        { value: 'OTROS', label: 'Otros' }
    ];

    const estadoOptions = [
        { value: '', label: 'Todos' },
        { value: 'PENDIENTE', label: 'Pendiente' },
        { value: 'EN_ADOPCION', label: 'En adopción' },
        { value: 'CEDIDO', label: 'Cedido' },
        { value: 'ACOGIDO', label: 'Acogido' },
        { value: 'RESERVADO', label: 'Reservado' },
        { value: 'ADOPTADO', label: 'Adoptado' },
        { value: 'FALLECIDO', label: 'Fallecido' }
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
            primary: '#629677',
        }
    });

    const fetchAnimales = useCallback((pageNumber) => {
        if (pageNumber < 0)
            return;

        setLoadError('');
        setIsLoading(true);

        const params = new URLSearchParams({
            page: pageNumber,
            size: 10,
        });
        if (categoria)
            params.append('categoria', categoria);
        if (estado)
            params.append('estado', estado);

        fetch(`/protectoras/${id}/animales?${params.toString()}`, {
            headers: { 'Authorization': 'Bearer ' + token }
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                if (response.status === 401) {
                    logout();
                    return;
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
    }, [categoria, estado, id, logout, token]);

    const handleScroll = useCallback(() => {
        if (isLoading || !hasMore) return;

        const container = containerRef.current;
        if (container) {
            const scrollTop = container.scrollTop;
            const scrollHeight = container.scrollHeight;
            const clientHeight = container.clientHeight;

            if (scrollTop + clientHeight >= scrollHeight - 10) {
                setPage((prev) => prev + 1);
            }
        }
    }, [isLoading, hasMore]);

    const reload = (categoria, estado) => {
        setPage(-1);
        setHasMore(false);
        setAnimales([]);
        if (categoria == null && estado == null) {
            setCategoria('');
            setEstado('');
        } else if (categoria != null)
            setCategoria(categoria);
        else
            setEstado(estado);
        setHasMore(true);
    };

    const handleOpen = (idAnimal) => {
        setAnimalSelected(idAnimal);
        setAnimalShow(true);
    };

    const onHideAnimal = () => {
        setAnimalSelected('');
        setAnimalShow(false)
    }

    useEffect(() => {
        if (initialFetch.current) {
            initialFetch.current = false;
            return;
        }
        fetchAnimales(page);
    }, [page, fetchAnimales]);

    useEffect(() => {
        const container = containerRef.current;

        if (container && !isLoading && hasMore) {
            const scrollHeight = container.scrollHeight;
            const clientHeight = container.clientHeight;

            if (scrollHeight <= clientHeight) {
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

    const Animales = () => (
        <div className='contenido' id="animales">
            <h3>Animales</h3>
            <div ref={containerRef} id="contenedor">
                <div id='header'>
                    <div className='filtro'>
                        <label>Categoría:</label>
                        <Select
                            value={categoriaOptions.find(option => option.value === categoria)}
                            onChange={(selectedOption) => reload(selectedOption.value)}
                            options={categoriaOptions}
                            styles={customStyles}
                            theme={customTheme}
                        />
                    </div>
                    <div className='filtro'>
                        <label>Estado:</label>
                        <Select
                            value={estadoOptions.find(option => option.value === estado)}
                            onChange={(selectedOption) => reload(undefined, selectedOption.value)}
                            options={estadoOptions}
                            styles={customStyles}
                            theme={customTheme}
                        />
                    </div>
                    {permisos && (isAdmin || permisos.includes("CREATE_ANIMALES")) &&
                        <Button onClick={() => setAltaShow(true)}>
                            <Plus size={15} /> Añadir animal
                        </Button>
                    }
                </div>
                {loadError ?
                    <p className='text-center'>{loadError}</p>
                    :
                    <div id='animales'>
                        {animales.map((animal) => (
                            <Image
                                key={animal.id}
                                src={`/protectoras/${id}/animales/${animal.id}/imagenes/${animal.imagen}`}
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
            <AltaModal
                show={altaShow}
                onHide={() => setAltaShow(false)}
                onAlta={reload}
                customStyles={customStyles}
                customTheme={customTheme}
            />
            <AnimalModal
                id='animal'
                show={animalShow}
                onHide={onHideAnimal}
                onChange={reload}
                idAnimal={animalSelected}
            />
        </div >
    );

    return (
        <Layout contenido={Animales} />
    );
};

export default Animales;