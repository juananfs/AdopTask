import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { Modal, Form, Image, Alert, Button, OverlayTrigger, Tooltip } from 'react-bootstrap';
import { Pencil, Trash2, Plus } from 'lucide-react';
import Select from 'react-select';
import PortadaModal from './PortadaModal';

const UpdateAnimal = ({ animal, reload, onUpdate, setEdit, categoriaOptions, estadoOptions, sexoOptions, visibilidadOptions }) => {
    const { token, logout } = useAuth();
    const { id } = useParams();

    const [nombre, setNombre] = useState(animal.nombre);
    const [portada, setPortada] = useState(animal.portada);
    const [show, setShow] = useState(false);
    const [fechaEntrada, setFechaEntrada] = useState(animal.fechaEntrada);
    const [categoria, setCategoria] = useState(animal.categoria);
    const [estado, setEstado] = useState(animal.estado);
    const [raza, setRaza] = useState(animal.raza || undefined);
    const [sexo, setSexo] = useState(animal.sexo);
    const [peso, setPeso] = useState(animal.peso || undefined);
    const [fechaNacimiento, setFechaNacimiento] = useState(animal.fechaNacimiento || undefined);
    const [descripcion, setDescripcion] = useState(animal.descripcion || undefined);
    const [camposAdicionales, setCamposAdicionales] = useState(animal.camposAdicionales);
    const [error, setError] = useState('');

    const handleUpdate = (event) => {
        event.preventDefault();
        setError('');

        if (!nombre.trim()) {
            setNombre('');
        }
        if (!nombre || !fechaEntrada) {
            setError('Por favor, ingresa todos los campos obligatorios. (*)');
            return;
        }

        const animalData = {
            nombre: nombre,
            portada: portada,
            fechaEntrada: fechaEntrada,
            categoria: categoria,
            estado: estado,
            raza: raza,
            sexo: sexo,
            peso: peso,
            fechaNacimiento: fechaNacimiento,
            descripcion: descripcion,
            camposAdicionales: camposAdicionales
        };

        fetch(`/api/protectoras/${id}/animales/${animal.id}`, {
            method: 'PATCH',
            headers: {
                'Authorization': 'Bearer ' + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(animalData)
        })
            .then(response => {
                if (response.ok) {
                    reload();
                    onUpdate();
                    setEdit(false);
                    return;
                }
                if (response.status === 401) {
                    logout();
                }
                throw new Error(response.statusText);
            })
            .catch(error => {
                setError(error.message);
            });
    };

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

    const setCampo = (index, key, value) => {
        setCamposAdicionales(prevCampos => {
            const nuevosCampos = [...prevCampos];
            nuevosCampos[index] = {
                ...nuevosCampos[index],
                [key]: value
            };
            return nuevosCampos;
        });
    };

    const removeCampo = (index) => {
        setCamposAdicionales(prevCampos =>
            prevCampos.filter((_, i) => i !== index)
        );
    };

    const addCampo = () => {
        setCamposAdicionales(prevCampos => [
            ...prevCampos,
            { clave: '', valor: '', publico: false }
        ]);
    };

    return (
        <div id='update'>
            <Form id="update-form" onSubmit={handleUpdate}>
                <div id='header'>
                    <div id='portada' onClick={() => setShow(true)}>
                        <Image src={`/api/protectoras/${id}/animales/${animal.id}/imagenes/${portada}`} rounded />
                        <Pencil />
                    </div>
                    <div id='nombre-fecha'>
                        <Form.Group>
                            <Form.Label>Nombre*</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Nombre"
                                value={nombre}
                                onChange={(e) => setNombre(e.target.value)}
                                isInvalid={error && !nombre}
                            />
                        </Form.Group>
                        <Form.Group>
                            <Form.Label>Fecha de entrada*</Form.Label>
                            <Form.Control
                                type="date"
                                value={fechaEntrada}
                                onChange={(e) => setFechaEntrada(e.target.value)}
                                isInvalid={error && !fechaEntrada}
                            />
                        </Form.Group>
                    </div>
                </div>
                <Form.Group>
                    <Form.Label>Categoría</Form.Label>
                    <Select
                        className='select'
                        value={categoriaOptions.find(option => option.value === categoria)}
                        onChange={(selectedOption) => setCategoria(selectedOption.value)}
                        options={categoriaOptions}
                        styles={customStyles}
                        theme={customTheme}
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Estado</Form.Label>
                    <Select
                        className='select'
                        value={estadoOptions.find(option => option.value === estado)}
                        onChange={(selectedOption) => setEstado(selectedOption.value)}
                        options={estadoOptions}
                        styles={customStyles}
                        theme={customTheme}
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Raza</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Raza"
                        value={raza}
                        onChange={(e) => setRaza(e.target.value)}
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Sexo</Form.Label>
                    <Select
                        className='select'
                        value={sexoOptions.find(option => option.value === sexo)}
                        onChange={(selectedOption) => setSexo(selectedOption.value)}
                        options={sexoOptions}
                        styles={customStyles}
                        theme={customTheme}
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Peso</Form.Label>
                    <Form.Control
                        type="number"
                        placeholder="Peso"
                        value={peso}
                        onChange={(e) => setPeso(e.target.value)}
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Fecha de nacimiento</Form.Label>
                    <Form.Control
                        type="date"
                        value={fechaNacimiento}
                        onChange={(e) => setFechaNacimiento(e.target.value)}
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Descripción</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={2}
                        value={descripcion}
                        onChange={(e) => setDescripcion(e.target.value)}
                    />
                </Form.Group>
                <div id='campos-adicionales'>
                    <Form.Label>Campos adicionales</Form.Label>
                    {camposAdicionales.map((campo, index) => (
                        <Form.Group className='campo' key={`campo-${index}`}>
                            <Form.Control
                                type="text"
                                value={campo.clave}
                                onChange={(e) => setCampo(index, 'clave', e.target.value)}
                            />
                            <Form.Label className='text-center'>:</Form.Label>
                            <Form.Control
                                type="text"
                                value={campo.valor}
                                onChange={(e) => setCampo(index, 'valor', e.target.value)}
                            />
                            <div />
                            <Select
                                value={visibilidadOptions.find(option => option.value === campo.publico)}
                                onChange={(selectedOption) => setCampo(index, 'publico', selectedOption.value)}
                                options={visibilidadOptions}
                                styles={customStyles}
                                theme={customTheme}
                            />
                            <div />
                            <OverlayTrigger
                                delay={{ show: 500, hide: 100 }}
                                overlay={<Tooltip>Eliminar</Tooltip>}
                            >
                                <Button onClick={() => removeCampo(index)} variant='light' className='delete'>
                                    <Trash2 strokeWidth={1.2} />
                                </Button>
                            </OverlayTrigger>
                        </Form.Group>
                    ))}
                    <Button variant='light' onClick={addCampo}>
                        <Plus />
                    </Button>
                </div>
            </Form>
            {error &&
                <Alert variant="danger" className="text-center">
                    {error}
                </Alert>
            }
            <Modal.Footer>
                <Button variant="secondary" onClick={() => setEdit(false)}>
                    Cancelar
                </Button>
                <Button type="submit" form="update-form">
                    Guardar
                </Button>
            </Modal.Footer>
            <PortadaModal
                id="portada-select"
                show={show}
                idAnimal={animal.id}
                imagenes={animal.imagenes}
                portada={portada}
                setPortada={setPortada}
                reload={reload}
                onHide={() => setShow(false)}
                backdropClassName="nested-backdrop"
                style={{ zIndex: 1057 }}
            />
        </div>
    );
};

export default UpdateAnimal;