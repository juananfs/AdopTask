import React, { createContext, useState, useEffect, useCallback } from 'react';

const AuthContext = createContext();

const AuthProvider = ({ children }) => {
    // Variables de estado para la autenticación
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [id, setId] = useState(localStorage.getItem('id'));
    const [nick, setNick] = useState(localStorage.getItem('nick'));
    const [foto, setFoto] = useState(localStorage.getItem('foto'));
    const [nombreProtectora, setNombreProtectora] = useState(localStorage.getItem('nombreProtectora'));
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
        if (nick !== null) {
            localStorage.setItem('nick', nick);
        } else {
            localStorage.removeItem('nick');
        }
    }, [nick]);

    useEffect(() => {
        if (foto !== null) {
            localStorage.setItem('foto', foto);
        } else {
            localStorage.removeItem('foto');
        }
    }, [foto]);

    useEffect(() => {
        if (nombreProtectora !== null) {
            localStorage.setItem('nombreProtectora', nombreProtectora);
        } else {
            localStorage.removeItem('nombreProtectora');
        }
    }, [nombreProtectora]);

    useEffect(() => {
        if (isAdmin) {
            localStorage.setItem('isAdmin', 'true');
        } else {
            localStorage.removeItem('isAdmin');
        }
    }, [isAdmin]);

    useEffect(() => {
        if (permisos !== null && permisos.length > 0) {
            localStorage.setItem('permisos', JSON.stringify(permisos));
        } else {
            localStorage.removeItem('permisos');
        }
    }, [permisos]);

    // Función para iniciar sesión
    const login = useCallback((token, id, nick, foto) => {
        setToken(token);
        setId(id);
        setNick(nick);
        setFoto(foto);
    }, []);

    // Función para acceder a una protectora
    const access = useCallback((nombre, isAdmin, permisos) => {
        setNombreProtectora(nombre);
        setIsAdmin(isAdmin);
        setPermisos(permisos);
    }, []);

    // Función para cerrar sesión
    const logout = useCallback(() => {
        setNombreProtectora(null);
        setIsAdmin(false);
        setPermisos(null);
        setToken(null);
        setId(null);
        setFoto(null);
    }, []);

    return (
        <AuthContext.Provider value={{ token, id, nick, foto, nombreProtectora, isAdmin, permisos, login, access, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

const useAuth = () => React.useContext(AuthContext);

export { AuthProvider, useAuth }