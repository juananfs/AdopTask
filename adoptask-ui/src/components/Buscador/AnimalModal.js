import { useAuth } from '../../AuthContext';
import { useState, useRef, useCallback, useEffect } from 'react';
import { Modal, Carousel, Image, Spinner, Alert, Button } from 'react-bootstrap';
import { Heart, CircleAlert } from 'lucide-react';

const AnimalModal = ({ idAnimal, ...props }) => {
    const { token, id, logout } = useAuth();

    const [publicacion, setPublicacion] = useState(undefined);
    const [likes, setLikes] = useState(0);
    const [liked, setLiked] = useState(false);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');
    const [modalShow, setModalShow] = useState(false);

    const initialFetch = useRef(true);

    const fetchPublicacion = useCallback(() => {
        setError('');
        setIsLoading(true);

        fetch(`/api/publicaciones/${idAnimal}`, token && {
            headers: { 'Authorization': 'Bearer ' + token }
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error("Ocurrió un error en el servidor. Intentalo más tarde.");
            })
            .then(data => {
                setPublicacion(data);
                setLikes(data.likes);
                setLiked(data.liked);
            })
            .catch(error => {
                setError(error.message);
            })
            .finally(() => setIsLoading(false));
    }, [idAnimal, token]);

    useEffect(() => {
        if (initialFetch.current) {
            initialFetch.current = false;
            return;
        }
        if (!idAnimal) {
            setPublicacion(undefined);
            return;
        }
        fetchPublicacion();
    }, [idAnimal, fetchPublicacion]);

    const handleLike = () => {
        if (!token) {
            setModalShow(true);
            return;
        }

        if (liked) {
            fetch(`/api/usuarios/${id}/favoritos/${publicacion.id}`, {
                method: 'DELETE',
                headers: { 'Authorization': 'Bearer ' + token }
            })
                .then(response => {
                    if (response.ok) {
                        setLikes(prev => prev - 1);
                        setLiked(false);
                        return;
                    }
                    if (response.status === 401) {
                        logout();
                    }
                    throw new Error("Ocurrió un error en el servidor. Intentalo más tarde.");
                })
                .catch(error => {
                    setError(error.message);
                });
        } else {
            fetch(`/api/usuarios/${id}/favoritos?idPublicacion=${publicacion.id}`, {
                method: 'POST',
                headers: { 'Authorization': 'Bearer ' + token }
            })
                .then(response => {
                    if (response.ok) {
                        setLikes(prev => prev + 1);
                        setLiked(true);
                        return;
                    }
                    if (response.status === 401) {
                        logout();
                    }
                    throw new Error("Ocurrió un error en el servidor. Intentalo más tarde.");
                })
                .catch(error => {
                    setError(error.message);
                });
        }
    };

    const categoriaOptions = [
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

    const sexoOptions = [
        { value: null, label: 'n/a' },
        { value: 'MACHO', label: 'Macho' },
        { value: 'HEMBRA', label: 'Hembra' }
    ];

    return (
        <Modal
            {...props}
            centered
        >
            <Modal.Header closeButton>
                {publicacion &&
                    <Modal.Title>
                        {publicacion.nombre}
                    </Modal.Title>
                }
            </Modal.Header>
            {publicacion &&
                <Modal.Body>
                    <Carousel
                        data-bs-theme="dark"
                        interval={null}
                        slide={false}
                    >
                        {publicacion.imagenes.map((imagen) => (
                            <Carousel.Item key={imagen}>
                                <div>
                                    <Image
                                        src={`/api/protectoras/${publicacion.idProtectora}/animales/${idAnimal}/imagenes/${imagen}`}
                                        alt={imagen}
                                    />
                                </div>
                            </Carousel.Item>
                        ))}
                    </Carousel>
                    <div id='likes'>
                        {likes}
                        <button onClick={handleLike}>
                            <Heart fill={liked ? 'red' : 'transparent'} />
                        </button>
                    </div>
                    {publicacion.descripcion &&
                        <section id='descripcion'>
                            <h4 className='text-center'>Descripción</h4>
                            <label>{publicacion.descripcion}</label>
                        </section>
                    }
                    <section id='datos'>
                        <h4 className='text-center'>Datos</h4>
                        <div className='dato'>
                            <strong>Categoría:</strong>
                            <label>{categoriaOptions.find(option => option.value === publicacion.categoria).label}</label>
                        </div>
                        {publicacion.raza &&
                            <div className='dato'>
                                <strong>Raza:</strong>
                                <label>{publicacion.raza}</label>
                            </div>
                        }
                        {publicacion.sexo &&
                            <div className='dato'>
                                <strong>Sexo:</strong>
                                <label>{sexoOptions.find(option => option.value === publicacion.sexo).label}</label>
                            </div>
                        }
                        {publicacion.edad &&
                            <div className='dato'>
                                <strong>Edad:</strong>
                                <label>{publicacion.edad} años</label>
                            </div>
                        }
                        {publicacion.peso &&
                            <div className='dato'>
                                <strong>Peso:</strong>
                                <label>{publicacion.peso} kg</label>
                            </div>
                        }
                        {publicacion.camposAdicionales.map((campo, index) => (
                            campo.clave && campo.valor &&
                            <div className='dato' key={`campo-${index}`}>
                                <strong>{campo.clave}:</strong>
                                <label>{campo.valor}</label>
                            </div>
                        ))}

                    </section>
                    <section id='contacto'>
                        <h4 className='text-center'>Contacto</h4>
                        <div className='dato'>
                            <strong>Correo electrónico:</strong>
                            <label>{publicacion.email}</label>
                        </div>
                        <div className='dato'>
                            <strong>Ubicación:</strong>
                            <label>{publicacion.ubicacion}</label>
                        </div>
                        {publicacion.telefono &&
                            <div className='dato'>
                                <strong>Teléfono:</strong>
                                <label>{publicacion.telefono}</label>
                            </div>
                        }
                        {publicacion.web &&
                            <div className='dato'>
                                <strong>Página web:</strong>
                                <label>{publicacion.web}</label>
                            </div>
                        }
                    </section>
                </Modal.Body>
            }
            {isLoading && <Spinner animation="grow" variant="dark" />}
            {error &&
                <Alert variant="danger" className="text-center">
                    {error}
                </Alert>
            }
            <Modal
                show={modalShow}
                onHide={() => setModalShow(false)}
                aria-labelledby="modal-title"
                backdropClassName="nested-backdrop"
                style={{ zIndex: 1056 }}
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title id="modal-title">
                        <CircleAlert />
                        No has iniciado sesión
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <p>Debes iniciar sesión para poder añadir a favoritos una publicación.</p>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => setModalShow(false)}>Cerrar</Button>
                </Modal.Footer>
            </Modal>
        </Modal>
    );
};

export default AnimalModal;
