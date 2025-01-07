import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { Modal, Image, Button, Alert } from 'react-bootstrap';
import { Image as ImageIcon } from 'lucide-react';
import ImagenAdd from './ImagenAdd';

const PortadaModal = ({ idAnimal, imagenes, portada, setPortada, reload, ...props }) => {
    const { id } = useParams();

    const [selectedImagen, setSelectedImagen] = useState(portada);
    const [error, setError] = useState('');

    const handleCloseModal = () => {
        setError('');
        setSelectedImagen(portada);
        props.onHide();
    };

    const handleUpdate = () => {
        setError('');
        setPortada(selectedImagen);
        props.onHide();
    };

    return (
        <Modal {...props} onHide={handleCloseModal}>
            <Modal.Header>
                <ImageIcon size={20}/>
               <h5> Selecciona una portada</h5>
            </Modal.Header>
            <Modal.Body>
                <div id='imagenes'>
                    {imagenes.map((imagen) => (
                        <Image
                            key={imagen}
                            src={`/api/protectoras/${id}/animales/${idAnimal}/imagenes/${imagen}`}
                            alt={imagen}
                            onClick={() => setSelectedImagen(imagen)}
                            rounded
                            thumbnail={imagen === selectedImagen}
                        />
                    ))}
                    {imagenes.length < 5 && <ImagenAdd idAnimal={idAnimal} size={60} reload={reload} onError={setError} />}
                </div>
                {error &&
                    <Alert variant="danger" className="text-center mt-4">
                        {error}
                    </Alert>
                }
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleCloseModal}>
                    Cancelar
                </Button>
                <Button onClick={handleUpdate}>
                    Guardar
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default PortadaModal;