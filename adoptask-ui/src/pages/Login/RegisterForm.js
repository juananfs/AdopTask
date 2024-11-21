import { useAuth } from '../../AuthContext';
import { useState } from 'react';
import { Form, Alert, Button } from 'react-bootstrap';

const RegisterForm = ({ redirectFunction: redirect }) => {

    const { login } = useAuth();

    const [username, setUsername] = useState('');
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [image, setImage] = useState(null);
    const [error, setError] = useState('');

    const handleRegister = (event) => {
        event.preventDefault();

        setError('');

        if (!username.trim()) {
            setUsername('');
        }
        if (!name.trim()) {
            setName('');
        }
        if (!email.trim()) {
            setEmail('');
        }
        if (!password.trim()) {
            setPassword('');
        }
        if (!username || !name || !email || !password) {
            setError('Por favor, ingresa todos los campos.');
            return;
        }

        const formData = new FormData();
        formData.append("nick", username);
        formData.append("nombre", name);
        formData.append("email", email);
        formData.append("password", password);
        if (image) {
            formData.append("imagen", image);
        }

        fetch('/usuarios', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.status === 500) {
                    throw new Error("Ocurrió un error en el servidor. Intentalo más tarde.");
                }
                if (response.status === 201) {
                    const loginData = {
                        nick: username,
                        password: password
                    };
                    fetch('/auth/login', {
                        method: 'POST',
                        headers: {
                            "Content-Type": "application/json",
                        },
                        body: JSON.stringify(loginData)
                    })
                        .then(response => {
                            if (!response.ok)
                                throw new Error(`Error en la petición: ${response.statusText}`);
                            return response.json();
                        })
                        .then(data => {
                            login(data.token, data.id, data.foto);
                            window.location.href = '/';
                        })
                        .catch(error => {
                            console.error(error);
                            window.location.href = '/';
                        });
                    return;
                }
                return response.json().then(data => {
                    if (data.estado === 'Bad Request') {
                        if (data.mensaje.includes('nick'))
                            setUsername('');
                        if (data.mensaje.includes('email'))
                            setEmail('');
                    }
                    throw new Error(data.mensaje + '.');
                })
                    .catch(error => {
                        setError(error.message);
                    });
            });
    };

    return (
        <Form onSubmit={handleRegister}>
            <h3 className='text-center'>Crea una cuenta</h3>
            <Form.Label>Nombre de usuario</Form.Label>
            <Form.Control
                type="text"
                placeholder="Nombre de usuario"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                isInvalid={error && !username}
                autoFocus
            />
            <Form.Label>Nombre</Form.Label>
            <Form.Control
                type="text"
                placeholder="Nombre"
                value={name}
                onChange={(e) => setName(e.target.value)}
                isInvalid={error && !name}
            />
            <Form.Label>Correo electrónico</Form.Label>
            <Form.Control
                type="email"
                placeholder="nombre@ejemplo.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                isInvalid={error && !email}
            />
            <Form.Label>Contraseña</Form.Label>
            <Form.Control
                type="password"
                placeholder="Contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                isInvalid={error && !password}
            />
            <Form.Label>Foto de perfil (Opcional)</Form.Label>
            <Form.Control
                type="file"
                accept="image/*"
                onChange={(e) => setImage(e.target.files[0])}
            />
            {error && <Alert variant="danger" className="text-center">
                {error}
            </Alert>}
            <Button type='submit'>
                REGISTRARSE
            </Button>
            <Button variant="secondary" onClick={() => redirect('/login')}>
                ¿YA TIENES CUENTA? INICIA SESIÓN
            </Button >
            <a className='text-center' href='/'>
                Volver a inicio
            </a>
        </Form>
    );
};

export default RegisterForm;