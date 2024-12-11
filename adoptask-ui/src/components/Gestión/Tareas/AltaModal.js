import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { Modal, Form, Alert, Button } from 'react-bootstrap';
import Select from 'react-select';
import { ClipboardPlus } from 'lucide-react';

const AltaModal = ({ onAlta, ...props }) => {
    const { token, logout } = useAuth();
    const { id } = useParams();

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [priority, setPriority] = useState('ALTA');
    const [error, setError] = useState('');

    const handleCloseModal = () => {
        setTitle('');
        setDescription('');
        setPriority('ALTA');
        setError('');
        props.onHide();
    };

    const handleAlta = (event) => {
        event.preventDefault();
        setError('');

        if (!title.trim()) {
            setTitle('');
        }
        if (!title) {
            setError('Por favor, ingresa el título de la tarea');
            return;
        }

        const tareaData = {
            titulo: title,
            prioridad: priority
        };
        if (description.trim()) {
            tareaData.descripcion = description;
        }

        fetch(`/protectoras/${id}/tareas`, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(tareaData)
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

    const priorityOptions = [
        { value: 'ALTA', label: 'Alta' },
        { value: 'MEDIA', label: 'Media' },
        { value: 'BAJA', label: 'Baja' }
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

    return (
        <Modal
            {...props}
            onHide={handleCloseModal}
            aria-labelledby="alta-title"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title>
                    <ClipboardPlus />
                    Añade una tarea
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form id="altaForm" onSubmit={handleAlta}>
                    <Form.Label>Título de la tarea*</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Título"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        isInvalid={error && !title}
                        autoFocus
                    />
                    <Form.Label>Prioridad</Form.Label>
                    <Select
                        className='select'
                        value={priorityOptions.find(option => option.value === priority)}
                        onChange={(selectedOption) => setPriority(selectedOption.value)}
                        options={priorityOptions}
                        styles={customStyles}
                        theme={(theme) => ({
                            ...theme,
                            borderRadius: 6,
                            colors: {
                                ...theme.colors,
                                primary25: '#f2f4f3',
                                primary50: '#357266',
                                primary: '#629677',
                            },
                        })}
                    />
                    <Form.Label>Descripción</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={2}
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
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
                <Button type="submit" form="altaForm">
                    Añadir
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default AltaModal;
