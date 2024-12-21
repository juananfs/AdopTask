import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import { useState, useRef, useCallback, useEffect } from 'react';
import { Modal, Tabs, Tab, Spinner, Alert } from 'react-bootstrap';
import { FileText } from 'lucide-react';
import DatosAnimal from './DatosAnimal';
import ImagenesAnimal from './ImagenesAnimal';
import DocumentosAnimal from './DocumentosAnimal';

const AnimalModal = ({ idAnimal, onUpdate, ...props }) => {
    const { token, logout } = useAuth();
    const { id } = useParams();

    const [animal, setAnimal] = useState(undefined);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');

    const initialFetch = useRef(true);

    const fetchAnimal = useCallback(() => {
        setError('');
        setIsLoading(true);

        fetch(`/protectoras/${id}/animales/${idAnimal}`, {
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
                setAnimal(data);
            })
            .catch(error => {
                setError(error.message);
            })
            .finally(() => setIsLoading(false));
    }, [id, idAnimal, logout, token]);

    useEffect(() => {
        if (initialFetch.current) {
            initialFetch.current = false;
            return;
        }
        if (!idAnimal) {
            setAnimal(undefined);
            return;
        }
        fetchAnimal();
    }, [idAnimal, fetchAnimal]);

    return (
        <Modal
            {...props}
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title>
                    <FileText />
                    Ficha del animal
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Tabs defaultActiveKey="datos" className="mb-3">
                    <Tab eventKey="datos" title="Datos">
                        {animal &&
                            <DatosAnimal
                                animal={animal}
                                reload={fetchAnimal}
                                onUpdate={onUpdate}
                                onDelete={() => {
                                    onUpdate();
                                    props.onHide();
                                }}
                            />
                        }
                    </Tab>
                    <Tab eventKey="imagenes" title="Imágenes">
                        {animal &&
                            <ImagenesAnimal
                                idAnimal={animal.id}
                                imagenes={animal.imagenes}
                                reload={fetchAnimal}
                            />
                        }
                    </Tab>
                    <Tab eventKey="documentos" title="Documentos">
                        {animal &&
                            <DocumentosAnimal
                                idAnimal={animal.id}
                                documentos={animal.documentos}
                                reload={fetchAnimal}
                            />
                        }
                    </Tab>
                </Tabs>
                {isLoading && <Spinner animation="grow" variant="dark" />}
                {error &&
                    <Alert variant="danger" className="text-center">
                        {error}
                    </Alert>
                }
            </Modal.Body>
        </Modal>
    );
};

export default AnimalModal;
