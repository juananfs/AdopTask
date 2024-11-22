import { useAuth } from '../../AuthContext';
import { Route } from 'react-router-dom';
import Forbidden from '../../pages/Error/Forbidden';

const RutaUsuario = ({ component: Component, ...rest }) => {
    const { id } = useAuth();

    return (
        <Route
            {...rest}
            render={(props) =>
                id ? <Component {...props} /> : <Forbidden />
            }
        />
    );
};

export default RutaUsuario;