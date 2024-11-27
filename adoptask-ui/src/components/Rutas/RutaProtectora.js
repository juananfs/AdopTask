import { useAuth } from '../../AuthContext';
import { Route } from 'react-router-dom';
import Forbidden from '../../pages/Error/Forbidden';
import { useEffect, useState } from 'react';

const RutaProtectora = ({ component: Component, ...rest }) => {
    const { token, access } = useAuth();
    const { id } = rest.computedMatch.params;
    const [hasAccess, setHasAccess] = useState(false);

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
            })
            .catch(() => setHasAccess(false));
    }, [id, token, access]);

    return (
        <Route
            {...rest}
            render={(props) =>
                hasAccess ? <Component {...props} /> : <Forbidden />
            }
        />
    );
};

export default RutaProtectora;