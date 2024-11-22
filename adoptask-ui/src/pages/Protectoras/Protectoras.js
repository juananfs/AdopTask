import './Protectoras.css'
import { useAuth } from '../../AuthContext';
import { useState, useRef, useEffect, useCallback } from 'react';
import Header from '../../components/Header/Header';
import { Card, ListGroup, Button, Modal, Alert, Spinner } from 'react-bootstrap';
import Footer from '../../components/Footer/Footer';
import { CirclePlus, CircleAlert, TriangleAlert } from 'lucide-react';
import AltaModal from './AltaModal';

const Protectoras = () => {
    const { token, access, logout } = useAuth();

    const [protectoras, setProtectoras] = useState([]);
    const [page, setPage] = useState(0);
    const [isLoading, setIsLoading] = useState(false);
    const [hasMore, setHasMore] = useState(true);
    const [loadError, setLoadError] = useState('');
    const [modalShow, setModalShow] = useState(false);
    const [modalTitle, setModalTitle] = useState('');
    const [modalText, setModalText] = useState('');
    const [modalError, setModalError] = useState('');
    const [altaShow, setAltaShow] = useState(false);

    const initialLoadDone = useRef(false);

    const fetchProtectoras = async (pageNumber) => {
        setLoadError('');
        setIsLoading(true);

        fetch(`/protectoras?page=${pageNumber}&size=10`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error("Ocurrió un error en el servidor. Intentalo más tarde.");
            })
            .then(data => {
                if (data._embedded)
                    setProtectoras((prev) => [...prev, ...data._embedded.resumenProtectoraDtoList]);
                setHasMore(data.page.number < data.page.totalPages - 1);
            })
            .catch(error => {
                setLoadError(error.message);
            })
            .finally(() => setIsLoading(false));
    };

    const handleScroll = useCallback(() => {
        if (isLoading || !hasMore) return;

        const scrollTop = document.documentElement.scrollTop;
        const scrollHeight = document.documentElement.scrollHeight;
        const clientHeight = document.documentElement.clientHeight;

        if (scrollTop + clientHeight >= scrollHeight - 100) {
            setPage((prev) => prev + 1);
        }
    }, [isLoading, hasMore]);

    const handleLoggedOut = () => {
        setModalTitle("No has iniciado sesión");
        setModalText("Debes iniciar sesión para poder acceder a una protectora.");
        setModalShow(true);
    }

    const handleAccess = (id) => {
        setModalError('');

        if (token) {
            fetch(`/protectoras/${id}/acceso`, {
                method: 'POST',
                headers: { 'Authorization': 'Bearer ' + token }
            })
                .then(response => {
                    if (response.ok) {
                        window.location.href = `/protectoras/${id}/tareas`;
                        return;
                    }
                    if (response.status === 401) {
                        logout();
                        handleLoggedOut();
                        return;
                    }
                    if (response.status === 403) {
                        setModalTitle("No tienes acceso");
                        setModalText("Ponte en contacto con el administrador de esta protectora para obtener acceso.");
                        setModalShow(true);
                        return;
                    }
                    throw new Error(response.statusText);
                })
                .catch(error => {
                    setModalError(error.message);
                });
        } else {
            handleLoggedOut();
        }
    };

    const handleAdd = () => {
        if (token)
            setAltaShow(true);
        else
            handleLoggedOut();
    };

    useEffect(() => {
        if (page === 0) {
            if (initialLoadDone.current) return;
            initialLoadDone.current = true;
        }
        fetchProtectoras(page);
    }, [page]);

    useEffect(() => {
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, [handleScroll]);

    return (
        <div className='protectoras'>
            <Header />
            <h2 className='text-center'>Protectoras</h2>
            {loadError ?
                <p className='text-center'>{loadError}</p>
                :
                <div className='wrap'>
                    {protectoras.map((protectora) => (
                        <Card key={protectora.id}>
                            {protectora.logotipo ?
                                <Card.Img variant="top" src={`/protectoras/${protectora.id}/${protectora.logotipo}`} alt={protectora.nombre} />
                                :
                                <Card.Img variant="top" src="/protectora.webp" alt="Logotipo por defecto para una protectora de animales con la silueta de un perro y un gato abrazándose dentro de un corazón" />}
                            <Card.Body>
                                <Card.Title>{protectora.nombre}</Card.Title>
                                <Card.Text>{protectora.descripcion}</Card.Text>
                            </Card.Body>
                            <ListGroup className="list-group-flush">
                                <ListGroup.Item>{protectora.email}</ListGroup.Item>
                                <ListGroup.Item>{protectora.ubicacion}</ListGroup.Item>
                                {protectora.telefono && <ListGroup.Item>{protectora.telefono}</ListGroup.Item>}
                                {protectora.web && <ListGroup.Item>{protectora.web}</ListGroup.Item>}
                            </ListGroup>
                            <Button onClick={() => handleAccess(protectora.id)}>ACCEDER</Button>
                        </Card>
                    ))}
                    <Card id='add' onClick={handleAdd}>
                        <CirclePlus />
                    </Card>
                </div>}
            {isLoading && <Spinner animation="grow" variant="dark" />}
            <Footer />
            <Modal
                show={modalShow}
                onHide={() => setModalShow(false)}
                aria-labelledby="modal-title"
                centered
            >
                <Modal.Header closeButton>
                    {modalError ?
                        <Modal.Title id="modal-title">
                            <TriangleAlert />
                            Ha ocurrido un error
                        </Modal.Title>
                        :
                        <Modal.Title id="modal-title">
                            <CircleAlert />
                            {modalTitle}
                        </Modal.Title>}
                </Modal.Header>
                {modalError ?
                    <Alert variant="danger">
                        <Alert.Heading>{modalError}</Alert.Heading>
                    </Alert>
                    :
                    <Modal.Body>
                        <p>{modalText}</p>
                    </Modal.Body>}
                <Modal.Footer>
                    <Button onClick={() => setModalShow(false)}>Cerrar</Button>
                </Modal.Footer>
            </Modal>
            <AltaModal
                show={altaShow}
                onHide={() => setAltaShow(false)}
            />
        </div>
    );
};

export default Protectoras;