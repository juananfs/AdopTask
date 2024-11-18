import { useAuth } from '../AuthContext';
import { Route } from 'react-router-dom';
import Forbidden from '../pages/Error/Forbidden';

const PrivateRoute = ({ component: Component, ...rest }) => {
    const { isLoggedIn } = useAuth();

    return (
        <Route
            {...rest}
            render={(props) =>
                isLoggedIn ? <Component {...props} /> : <Forbidden />
            }
        />
    );
};

export default PrivateRoute;