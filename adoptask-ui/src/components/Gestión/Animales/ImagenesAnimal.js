import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { Image as ImageIcon, Trash2 } from 'lucide-react';
import { Image, Alert, Modal, OverlayTrigger, Tooltip, Button, Carousel } from 'react-bootstrap';
import ImagenAdd from './ImagenAdd';

const ImagenesAnimal = ({ idAnimal, imagenes, reload }) => {
    const { token, logout, permisos, isAdmin } = useAuth();
    const { id } = useParams();

    const [index, setIndex] = useState(0);
    const [show, setShow] = useState(false);
    const [error, setError] = useState('');
    const [errorModal, setErrorModal] = useState('');

    const showImagen = (index) => {
        setIndex(index);
        setShow(true);
    };

    const handleDelete = () => {
        setErrorModal('');

        fetch(`/protectoras/${id}/animales/${idAnimal}/imagenes/${imagenes[index]}`, {
            method: 'DELETE',
            headers: { 'Authorization': 'Bearer ' + token }
        })
            .then(response => {
                if (response.ok) {
                    reload();
                    return;
                }
                if (response.status === 401) {
                    logout();
                }
                return response.json();
            })
            .then(data => {
                if (data.estado && data.mensaje)
                    throw new Error(data.mensaje + '.');
            })
            .catch(error => {
                setErrorModal(error.message);
            });
    }

    return (
        <div id='imagenes-animal'>
            <h5><ImageIcon size={20} />({imagenes.length}/5)</h5>
            <div id='imagenes'>
                {imagenes.map((imagen, index) => (
                    <Image
                        key={imagen}
                        src={`/protectoras/${id}/animales/${idAnimal}/imagenes/${imagen}`}
                        alt={imagen}
                        onClick={() => showImagen(index)}
                    />
                ))}
                {imagenes.length < 5 && (
                    <ImagenAdd idAnimal={idAnimal} size={60} reload={reload} onError={setError} />
                )}
            </div>
            {error &&
                <Alert variant="danger" className="text-center mt-4">
                    {error}
                </Alert>
            }
            <Modal
                className='carousel-modal'
                show={show}
                onHide={() => setShow(false)}
                backdropClassName="nested-backdrop"
                style={{ zIndex: 1056 }}
                size='lg'
                centered
            >
                <Modal.Header closeButton />
                <Modal.Body>
                    {permisos && (isAdmin || permisos.includes("UPDATE_ANIMALES")) &&
                        <OverlayTrigger
                            delay={{ show: 500, hide: 100 }}
                            overlay={<Tooltip>Eliminar imagen</Tooltip>}
                        >
                            <Button onClick={handleDelete} variant='light' className='delete'>
                                <Trash2 strokeWidth={1.2} />
                            </Button>
                        </OverlayTrigger>
                    }
                    <Carousel
                        data-bs-theme="dark"
                        activeIndex={index}
                        onSelect={selectedIndex => setIndex(selectedIndex)}
                        interval={null}
                    >
                        {imagenes.map((imagen) => (
                            <Carousel.Item key={imagen}>
                                <div>
                                    <Image
                                        src={`/protectoras/${id}/animales/${idAnimal}/imagenes/${imagen}`}
                                        alt={imagen}
                                    />
                                </div>
                            </Carousel.Item>
                        ))}
                    </Carousel>
                    {errorModal &&
                        <Alert variant="danger" className="text-center mt-4">
                            {errorModal}
                        </Alert>
                    }
                </Modal.Body>
            </Modal>
        </div>
    );
};


export default ImagenesAnimal;