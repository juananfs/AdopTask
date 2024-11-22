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
                    response.json().then(data => {
                        access(data.admin, data.permisos);
                        setHasAccess(true);
                    });
                else
                    setHasAccess(false);
            });
    }, [token]);

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