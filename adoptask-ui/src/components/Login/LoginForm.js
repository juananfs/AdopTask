import { useAuth } from '../../AuthContext';
import { useState } from 'react';
import { Form, Alert, Button } from 'react-bootstrap';

const LoginForm = ({ redirectFunction: redirect }) => {

    const { login } = useAuth();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = (event) => {
        event.preventDefault();

        setError('');

        if (!username.trim()) {
            setUsername('');
        }
        if (!password.trim()) {
            setPassword('');
        }
        if (!username || !password) {
            setError('Por favor, ingresa todos los campos.');
            return;
        }

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
                if (response.status === 500) {
                    throw new Error("Ocurrió un error en el servidor. Intentalo más tarde.");
                }
                if (response.status === 404) {
                    setUsername('');
                }
                if (response.status === 401) {
                    setPassword('');
                }
                return response.json();
            })
            .then(data => {
                if (data.estado && data.mensaje) {
                    throw new Error(data.mensaje + '.');
                } else {
                    login(data.token, data.id, data.nick, data.foto);
                    window.location.href = '/';
                }
            })
            .catch(error => {
                setError(error.message);
            });
    };

    return (
        <Form onSubmit={handleLogin}>
            <h3 className='text-center'>Inicia sesión</h3>
            <p className='text-center'>Inicia sesión para acceder a todos nuestros servicios</p>
            <Form.Label>Nombre de usuario</Form.Label>
            <Form.Control
                type="text"
                placeholder="Nombre de usuario"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                isInvalid={error && !username}
                autoFocus
            />
            <Form.Label>Contraseña</Form.Label>
            <Form.Control
                type="password"
                placeholder="Contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                isInvalid={error && !password}
            />
            {error && <Alert variant="danger" className="text-center">
                {error}
            </Alert>}
            <Button type='submit'>
                INICIAR SESIÓN
            </Button>
            <Button variant="secondary" onClick={() => redirect('/register')}>
                ¿NO TIENES CUENTA? REGÍSTRATE
            </Button>
            <a className='text-center' href='/'>
                Volver a inicio
            </a>
        </Form>
    );
};

export default LoginForm;