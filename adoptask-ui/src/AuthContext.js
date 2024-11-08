import React, { createContext, useState, useEffect } from 'react';

const AuthContext = createContext();

function AuthProvider({ children }) {
    // Variables de estado para la autenticación
    const [isLoggedIn, setIsLoggedIn] = useState(localStorage.getItem('isLoggedIn') === 'true');
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [id, setId] = useState(localStorage.getItem('id'));
    const [foto, setFoto] = useState(localStorage.getItem('foto'));

    // Hooks de efectos para persistir el estado
    useEffect(() => {
        if (isLoggedIn) {
            localStorage.setItem('isLoggedIn', 'true');
        } else {
            localStorage.removeItem('isLoggedIn');
        }
    }, [isLoggedIn]);

    useEffect(() => {
        if (token !== null) {
            localStorage.setItem('token', token);
        } else {
            localStorage.removeItem('token');
        }
    }, [token]);

    useEffect(() => {
        if (id !== null) {
            localStorage.setItem('id', id);
        } else {
            localStorage.removeItem('id');
        }
    }, [id]);

    useEffect(() => {
        if (foto !== null) {
            localStorage.setItem('foto', foto);
        } else {
            localStorage.removeItem('foto');
        }
    }, [foto]);

    // Función para iniciar sesión
    const login = (token, id, foto) => {
        setToken(token);
        setId(id);
        setFoto(foto);
        setIsLoggedIn(true);
    }

    // Función para cerrar sesión
    const logout = () => {
        setToken(null);
        setId(null);
        setFoto(null);
        setIsLoggedIn(false);
    }

    return (
        <AuthContext.Provider value={{ isLoggedIn, token, id, foto, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

const useAuth = () => React.useContext(AuthContext);

export { AuthProvider, useAuth }