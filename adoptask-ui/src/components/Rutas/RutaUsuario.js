import { useAuth } from '../../AuthContext';
import Forbidden from '../../pages/Error/Forbidden';

const RutaUsuario = ({ element: Component, ...rest }) => {
    const { id } = useAuth();

    return (
        <>
            {id ?
                <Component {...rest} />
                :
                <Forbidden />
            }
        </>
    );
};

export default RutaUsuario;