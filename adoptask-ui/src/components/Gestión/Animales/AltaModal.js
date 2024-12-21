import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { Modal, Form, Alert, Button } from 'react-bootstrap';
import Select from 'react-select';
import { CirclePlus } from 'lucide-react';

const AltaModal = ({ onAlta, customStyles, customTheme, ...props }) => {
    const { token, logout } = useAuth();
    const { id } = useParams();

    const [nombre, setNombre] = useState('');
    const [imagen, setImagen] = useState(undefined);
    const [fechaEntrada, setFechaEntrada] = useState('');
    const [categoria, setCategoria] = useState('PERROS');
    const [estado, setEstado] = useState('PENDIENTE');
    const [raza, setRaza] = useState('');
    const [sexo, setSexo] = useState('');
    const [peso, setPeso] = useState(undefined);
    const [fechaNacimiento, setFechaNacimiento] = useState('');
    const [descripcion, setDescripcion] = useState('');
    const [error, setError] = useState('');

    const handleCloseModal = () => {
        setNombre('');
        setImagen(undefined);
        setFechaEntrada('');
        setCategoria('PERROS');
        setEstado('PENDIENTE');
        setRaza('');
        setSexo('');
        setPeso(undefined);
        setFechaNacimiento('');
        setDescripcion('');
        setError('');
        props.onHide();
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
        { value: '', label: 'Sexo', isDisabled: true },
        { value: 'MACHO', label: 'Macho' },
        { value: 'HEMBRA', label: 'Hembra' }
    ];

    const handleAlta = (event) => {
        event.preventDefault();
        setError('');

        if (!nombre.trim()) {
            setNombre('');
        }
        if (!nombre || !imagen || !fechaEntrada) {
            setError('Por favor, ingresa todos los campos obligatorios. (*)');
            return;
        }

        const formData = new FormData();
        formData.append("nombre", nombre);
        formData.append("imagen", imagen);
        formData.append("fechaEntrada", fechaEntrada);
        formData.append("categoria", categoria);
        formData.append("estado", estado);
        if (raza)
            formData.append("raza", raza);
        if (sexo)
            formData.append("sexo", sexo);
        if (peso)
            formData.append("peso", peso);
        if (fechaNacimiento)
            formData.append("fechaNacimiento", fechaNacimiento);
        if (descripcion)
            formData.append("descripcion", descripcion);

        fetch(`/protectoras/${id}/animales`, {
            method: 'POST',
            headers: { 'Authorization': 'Bearer ' + token },
            body: formData
        })
            .then(response => {
                if (response.status === 201) {
                    onAlta();
                    handleCloseModal();
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

    return (
        <Modal
            {...props}
            onHide={handleCloseModal}
            aria-labelledby="alta-title"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title>
                    <CirclePlus />
                    Añade un animal
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form id="altaForm" onSubmit={handleAlta}>
                    <Form.Label>Nombre*</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Nombre"
                        value={nombre}
                        onChange={(e) => setNombre(e.target.value)}
                        isInvalid={error && !nombre}
                        autoFocus
                    />
                    <Form.Label>Imagen*</Form.Label>
                    <Form.Control
                        type="file"
                        accept="image/*"
                        onChange={(e) => setImagen(e.target.files[0])}
                        isInvalid={error && !imagen}
                    />
                    <Form.Label>Fecha de entrada*</Form.Label>
                    <Form.Control
                        type="date"
                        value={fechaEntrada}
                        onChange={(e) => setFechaEntrada(e.target.value)}
                        isInvalid={error && !fechaEntrada}
                    />
                    <Form.Label>Categoría*</Form.Label>
                    <Select
                        className='select'
                        value={categoriaOptions.find(option => option.value === categoria)}
                        onChange={(selectedOption) => setCategoria(selectedOption.value)}
                        options={categoriaOptions}
                        styles={customStyles}
                        theme={customTheme}
                    />
                    <Form.Label>Estado*</Form.Label>
                    <Select
                        className='select'
                        value={estadoOptions.find(option => option.value === estado)}
                        onChange={(selectedOption) => setEstado(selectedOption.value)}
                        options={estadoOptions}
                        styles={customStyles}
                        theme={customTheme}
                    />
                    <Form.Label>Raza</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Raza"
                        value={raza}
                        onChange={(e) => setRaza(e.target.value)}
                    />
                    <Form.Label>Sexo</Form.Label>
                    <Select
                        className='select'
                        value={sexoOptions.find(option => option.value === sexo)}
                        onChange={(selectedOption) => setSexo(selectedOption.value)}
                        options={sexoOptions}
                        styles={customStyles}
                        theme={customTheme}
                    />
                    <Form.Label>Peso</Form.Label>
                    <Form.Control
                        type="number"
                        placeholder="Peso"
                        value={peso}
                        onChange={(e) => setPeso(e.target.value)}
                    />
                    <Form.Label>Fecha de nacimiento</Form.Label>
                    <Form.Control
                        type="date"
                        value={fechaNacimiento}
                        onChange={(e) => setFechaNacimiento(e.target.value)}
                    />
                    <Form.Label>Descripción</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={2}
                        value={descripcion}
                        onChange={(e) => setDescripcion(e.target.value)}
                    />
                </Form>
                {error &&
                    <Alert variant="danger" className="text-center">
                        {error}
                    </Alert>
                }
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleCloseModal}>
                    Cerrar
                </Button>
                <Button type="submit" form="altaForm">
                    Añadir
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default AltaModal;
