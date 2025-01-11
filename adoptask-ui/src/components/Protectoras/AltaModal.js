import { useAuth } from '../../AuthContext';
import { useState } from 'react';
import { Modal, Form, Alert, Button } from 'react-bootstrap';
import { HousePlus } from 'lucide-react';

const AltaModal = ({ onAlta, ...props }) => {
    const { token, logout } = useAuth();

    const [name, setName] = useState('');
    const [nif, setNif] = useState('');
    const [email, setEmail] = useState('');
    const [location, setLocation] = useState('');
    const [description, setDescription] = useState('');
    const [number, setNumber] = useState('');
    const [image, setImage] = useState(null);
    const [web, setWeb] = useState('');
    const [error, setError] = useState('');

    const handleCloseModal = () => {
        setName('');
        setNif('');
        setEmail('');
        setLocation('');
        setDescription('');
        setNumber('');
        setImage(null);
        setWeb('');
        setError('');

        props.onHide();
    };

    const handleAlta = (event) => {
        event.preventDefault();

        setError('');

        if (!name.trim()) {
            setName('');
        }
        if (!nif.trim()) {
            setNif('');
        }
        if (!email.trim()) {
            setEmail('');
        }
        if (!location.trim()) {
            setLocation('');
        }
        if (!name || !nif || !email || !location) {
            setError('Por favor, ingresa todos los campos obligatorios. (*)');
            return;
        }

        const formData = new FormData();
        formData.append("nombre", name);
        formData.append("nif", nif);
        formData.append("email", email);
        formData.append("ubicacion", location);
        if (number) {
            formData.append("telefono", number);
        }
        if (web) {
            formData.append("web", web);
        }
        if (description) {
            formData.append("descripcion", description);
        }
        if (image) {
            formData.append("imagen", image);
        }

        fetch('/api/protectoras', {
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
                    <HousePlus />
                    Añade tu protectora
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form id="altaForm" onSubmit={handleAlta}>
                    <Form.Label>Nombre de la organización*</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Nombre"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        isInvalid={error && !name}
                        autoFocus
                    />
                    <Form.Label>NIF*</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="G12345678"
                        value={nif}
                        onChange={(e) => setNif(e.target.value)}
                        isInvalid={error && !nif}
                    />
                    <Form.Label>Correo electrónico de contacto*</Form.Label>
                    <Form.Control
                        type="email"
                        placeholder="nombre@ejemplo.com"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        isInvalid={error && !email}
                    />
                    <Form.Label>Ubicación*</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Calle, Ciudad, Provincia"
                        value={location}
                        onChange={(e) => setLocation(e.target.value)}
                        isInvalid={error && !location}
                    />
                    <Form.Label>Descripción</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={2}
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                    <Form.Label>Teléfono de contacto</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="+34 912 345 678"
                        value={number}
                        onChange={(e) => setNumber(e.target.value)}
                    />
                    <Form.Label>Logotipo</Form.Label>
                    <Form.Control
                        type="file"
                        accept="image/*"
                        onChange={(e) => setImage(e.target.files[0])}
                    />
                    <Form.Label>Página web</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="www.ejemplo.org"
                        value={web}
                        onChange={(e) => setWeb(e.target.value)}
                    />
                </Form>
                {error && <Alert variant="danger" className="text-center">
                    {error}
                </Alert>}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleCloseModal}>
                    Cerrar
                </Button>
                <Button type='submit' form="altaForm">
                    Añadir
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default AltaModal;