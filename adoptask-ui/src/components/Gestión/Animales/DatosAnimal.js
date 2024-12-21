import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import UpdateAnimal from './UpdateAnimal';
import { useState, } from 'react';
import { Image, OverlayTrigger, Tooltip, Button, Modal, Alert } from 'react-bootstrap';
import { Pencil, Trash2, TriangleAlert } from 'lucide-react';

const DatosAnimal = ({ animal, reload, onUpdate, onDelete }) => {
    const { token, logout, permisos, isAdmin } = useAuth();
    const { id } = useParams();

    const [edit, setEdit] = useState(false);
    const [show, setShow] = useState(false);
    const [error, setError] = useState('');

    const handleDelete = () => {
        setError('');

        fetch(`/protectoras/${id}/animales/${animal.id}`, {
            method: 'DELETE',
            headers: { 'Authorization': 'Bearer ' + token }
        })
            .then(response => {
                if (response.ok) {
                    onDelete();
                    setShow(false);
                    return;
                }
                if (response.status === 401) {
                    logout();
                    return;
                }
                throw new Error("Ocurrió un error en el servidor. Intentalo más tarde.");
            })
            .catch(error => {
                setError(error.message);
            })
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

    const estadoOptions = [
        { value: 'PENDIENTE', label: 'Pendiente' },
        { value: 'EN_ADOPCION', label: 'En adopción' },
        { value: 'CEDIDO', label: 'Cedido' },
        { value: 'ACOGIDO', label: 'Acogido' },
        { value: 'RESERVADO', label: 'Reservado' },
        { value: 'ADOPTADO', label: 'Adoptado' },
        { value: 'FALLECIDO', label: 'Fallecido' }
    ];

    const sexoOptions = [
        { value: null, label: 'n/a' },
        { value: 'MACHO', label: 'Macho' },
        { value: 'HEMBRA', label: 'Hembra' }
    ];

    const visibilidadOptions = [
        { value: false, label: 'Privado' },
        { value: true, label: 'Público' }
    ];

    return (
        edit ?
            <UpdateAnimal
                animal={animal}
                reload={reload}
                onUpdate={onUpdate}
                setEdit={setEdit}
                categoriaOptions={categoriaOptions}
                estadoOptions={estadoOptions}
                sexoOptions={sexoOptions}
                visibilidadOptions={visibilidadOptions}
            />
            :
            <div id="datos">
                <div id='header'>
                    <Image src={`/protectoras/${id}/animales/${animal.id}/imagenes/${animal.portada}`} rounded />
                    {permisos &&
                        <div>
                            {(isAdmin || permisos.includes("UPDATE_ANIMALES")) &&
                                <OverlayTrigger
                                    delay={{ show: 500, hide: 100 }}
                                    overlay={<Tooltip>Editar</Tooltip>}
                                >
                                    <Button onClick={() => setEdit(true)}>
                                        <Pencil strokeWidth={1.2} />
                                    </Button>
                                </OverlayTrigger>
                            }
                            {(isAdmin || permisos.includes("DELETE_ANIMALES")) &&
                                <OverlayTrigger
                                    delay={{ show: 500, hide: 100 }}
                                    overlay={<Tooltip>Eliminar</Tooltip>}
                                >
                                    <Button onClick={() => setShow(true)} className='delete'>
                                        <Trash2 strokeWidth={1.2} />
                                    </Button>
                                </OverlayTrigger>
                            }
                        </div>
                    }
                </div>
                <div className='dato'>
                    <strong>Nombre:</strong>
                    <label>{animal.nombre}</label>
                </div>
                <div className='dato'>
                    <strong>Fecha de entrada:</strong>
                    <label>{animal.fechaEntrada}</label>
                </div>
                <div className='dato'>
                    <strong>Categoría:</strong>
                    <label>{categoriaOptions.find(option => option.value === animal.categoria).label}</label>
                </div>
                <div className='dato'>
                    <strong>Estado:</strong>
                    <label>{estadoOptions.find(option => option.value === animal.estado).label}</label>
                </div>
                <div className='dato'>
                    <strong>Raza:</strong>
                    <label>{animal.raza || 'n/a'}</label>
                </div>
                <div className='dato'>
                    <strong>Sexo:</strong>
                    <label>{sexoOptions.find(option => option.value === animal.sexo).label}</label>
                </div>
                <div className='dato'>
                    <strong>Peso:</strong>
                    <label>{animal.peso || 'n/a'}</label>
                </div>
                <div className='dato'>
                    <strong>Fecha de nacimiento:</strong>
                    <label>{animal.fechaNacimiento || 'n/a'}</label>
                </div>
                <div className='dato'>
                    <strong>Descripción:</strong>
                    <label>{animal.descripcion || 'n/a'}</label>
                </div>
                <div id='campos-adicionales'>
                    {animal.camposAdicionales.map((campo, index) => (
                        campo.clave &&
                        <div className='campo' key={`campo-${index}`}>
                            <strong>{campo.clave}:</strong>
                            <label>{campo.valor || 'n/a'}</label>
                            <label>{visibilidadOptions.find(option => option.value === campo.publico).label}</label>
                        </div>
                    ))}
                </div>
                <Modal
                    id='delete-warning'
                    aria-labelledby="modal-title"
                    show={show}
                    onHide={() => setShow(false)}
                    backdropClassName="nested-backdrop"
                    style={{ zIndex: 1056 }}
                    centered
                >
                    <Modal.Header closeButton>
                        <Modal.Title id="modal-title">
                            <TriangleAlert />
                            ¿Seguro que deseas eliminar a {animal.nombre}?
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        Si lo eliminas perderás todas las imágenes y documentos asociados al animal.
                        {error &&
                            <Alert variant="danger" className="text-center">
                                {error}
                            </Alert>
                        }
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={() => setShow(false)} variant='secondary'>Cerrar</Button>
                        <Button onClick={handleDelete} variant='danger'>
                            <Trash2 />
                            Eliminar
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
    );
};

export default DatosAnimal;