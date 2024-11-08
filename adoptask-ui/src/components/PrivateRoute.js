import { Route } from 'react-router-dom';
import { useAuth } from '../AuthContext';
import Forbidden from '../pages/Error/Forbidden';

function PrivateRoute({ component: Component, ...rest }) {
    const { isLoggedIn } = useAuth();

    return (
        <Route
            {...rest}
            render={(props) =>
                isLoggedIn ? <Component {...props} /> : <Forbidden />
            }
        />
    );
}

export default PrivateRoute;