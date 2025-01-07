import { useAuth } from '../../AuthContext';
import { useParams, useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';
import Loading from '../../pages/Gestión/Loading';
import Forbidden from '../../pages/Error/Forbidden';
import ForbiddenProtectora from '../../pages/Gestión/Forbidden';

const RutaProtectora = ({ element: Component, ...rest }) => {
    const { token, access } = useAuth();
    const { id } = useParams();
    const location = useLocation();
    const [isLoading, setIsLoading] = useState(true);
    const [hasAccess, setHasAccess] = useState(false);
    const [hasPermission, setHasPermission] = useState(false);

    const checkPermission = (subruta, permisos) => {
        const permissionMap = {
            "tareas": "READ_TAREAS",
            "animales": "READ_ANIMALES",
            "documentos": "READ_DOCUMENTOS",
            "historial": "READ_HISTORIAL",
        };
        return permisos.includes(permissionMap[subruta]);
    };

    useEffect(() => {
        setIsLoading(true);
        fetch(`/api/protectoras/${id}/acceso`, {
            method: 'POST',
            headers: { 'Authorization': 'Bearer ' + token }
        })
            .then(response => {
                if (response.ok) return response.json();
                throw new Error('Acceso denegado');
            })
            .then(data => {
                access(data.nombreProtectora, data.admin, data.permisos);
                setHasAccess(true);
                if (data.admin) {
                    setHasPermission(true);
                } else {
                    const match = location.pathname.match(/\/protectoras\/[^/]+\/(.*)/);
                    if (match) {
                        const subruta = match[1];
                        setHasPermission(checkPermission(subruta, data.permisos));
                    } else {
                        setHasPermission(false);
                    }
                }
            })
            .catch(() => setHasAccess(false))
            .finally(() => setIsLoading(false));
    }, [id, token, access, location]);

    return (
        <>
            {isLoading ? (
                <Loading />
            ) : !hasAccess ? (
                <Forbidden />
            ) : hasPermission ? (
                <Component {...rest} />
            ) : (
                <ForbiddenProtectora />
            )}
        </>
    );
};

export default RutaProtectora;
