import React, { createContext, useState, useEffect } from 'react';

const AuthContext = createContext();

const AuthProvider = ({ children }) => {
    // Variables de estado para la autenticación
    const [id, setId] = useState(localStorage.getItem('id'));
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [foto, setFoto] = useState(localStorage.getItem('foto'));
    const [idProtectora, setIdProtectora] = useState(localStorage.getItem('idProtectora'));
    const [isAdmin, setIsAdmin] = useState(localStorage.getItem('isAdmin') === 'true');
    const [permisos, setPermisos] = useState(() => {
        try {
            return JSON.parse(localStorage.getItem('permisos')) || [];
        } catch (error) {
            return [];
        }
    });

    // Hooks de efectos para persistir el estado
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

    useEffect(() => {
        if (isAdmin) {
            localStorage.setItem('isAdmin', 'true');
        } else {
            localStorage.removeItem('isAdmin');
        }
    }, [isAdmin]);

    useEffect(() => {
        if (idProtectora !== null) {
            localStorage.setItem('idProtectora', idProtectora);
        } else {
            localStorage.removeItem('idProtectora');
        }
    }, [idProtectora]);

    useEffect(() => {
        if (permisos !== null && permisos.length > 0) {
            localStorage.setItem('permisos', JSON.stringify(permisos));
        } else {
            localStorage.removeItem('permisos');
        }
    }, [permisos]);

    // Función para iniciar sesión
    const login = (token, id, foto) => {
        setToken(token);
        setId(id);
        setFoto(foto);
    }

    // Función para acceder a una protectora
    const access = (idProtectora, isAdmin, permisos) => {
        setIdProtectora(idProtectora);
        setIsAdmin(isAdmin);
        setPermisos(permisos);
    }

    // Función para cerrar sesión
    const logout = () => {
        setIdProtectora(null);
        setIsAdmin(false);
        setPermisos(null);
        setToken(null);
        setId(null);
        setFoto(null);
    }

    return (
        <AuthContext.Provider value={{ token, id, foto, isAdmin, idProtectora, permisos, login, access, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

const useAuth = () => React.useContext(AuthContext);

export { AuthProvider, useAuth }