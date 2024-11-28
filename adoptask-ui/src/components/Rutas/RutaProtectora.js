import { useAuth } from '../../AuthContext';
import { useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { Route } from 'react-router-dom';
import ForbiddenProtectora from '../../pages/Error/ForbiddenProtectora';
import Forbidden from '../../pages/Error/Forbidden';


const RutaProtectora = ({ component: Component, ...rest }) => {
    const { token, access } = useAuth();
    const location = useLocation();
    const { id } = rest.computedMatch.params;
    const [hasAccess, setHasAccess] = useState(false);
    const [hasPermission, setHasPermission] = useState(false);

    useEffect(() => {
        fetch(`/protectoras/${id}/acceso`, {
            method: 'POST',
            headers: { 'Authorization': 'Bearer ' + token }
        })
            .then(response => {
                if (response.ok)
                    return response.json();
                else
                    throw new Error('Acceso denegado');
            })
            .then(data => {
                access(data.nombreProtectora, data.admin, data.permisos);
                setHasAccess(true);
                if (data.admin)
                    setHasPermission(true)
                else {
                    const match = location.pathname.match(/\/protectoras\/[^/]+\/(.*)/);
                    if (match) {
                        const subruta = match[1];
                        switch (subruta) {
                            case "tareas":
                                if (data.permisos.includes("READ_TAREAS"))
                                    setHasPermission(true);
                                else
                                    setHasPermission(false);
                                break;
                            case "animales":
                                if (data.permisos.includes("READ_ANIMALES"))
                                    setHasPermission(true);
                                else
                                    setHasPermission(false);
                                break;
                            case "documentos":
                                if (data.permisos.includes("READ_DOCUMENTOS"))
                                    setHasPermission(true);
                                else
                                    setHasPermission(false);
                                break;
                            case "historial":
                                if (data.permisos.includes("READ_HISTORIAL"))
                                    setHasPermission(true);
                                else
                                    setHasPermission(false);
                                break;
                            default:
                                setHasPermission(false);
                        }
                    } else
                        setHasPermission(false);
                }
            })
            .catch(() => setHasAccess(false));
    }, [id, token, access, location]);

    return (
        <Route
            {...rest}
            render={(props) =>
                hasAccess ? (hasPermission ? <Component {...props} /> : <ForbiddenProtectora />) : <Forbidden />
            }
        />
    );
};

export default RutaProtectora;