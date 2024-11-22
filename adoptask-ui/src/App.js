import { AuthProvider } from './AuthContext';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import RutaUsuario from './components/Rutas/RutaUsuario';
import RutaProtectora from './components/Rutas/RutaProtectora';
import NotFound from './pages/Error/NotFound';
import Home from './pages/Home/Home';
import Login from './pages/Login/Login';
import Register from './pages/Login/Register';
import Protectoras from './pages/Protectoras/Protectoras';
import TareasPage from './pages/Tareas/TareasPage';

const App = () => (
    <AuthProvider>
        <BrowserRouter>
            <div className="App">
                <Switch>
                    <Route exact path='/' component={Home} />
                    <Route path='/login' component={Login} />
                    <Route path='/register' component={Register} />
                    <Route exact path='/protectoras' component={Protectoras} />
                    <RutaProtectora path='/protectoras/:id/tareas' component={TareasPage} />
                    <Route component={NotFound} />
                </Switch>
            </div>
        </BrowserRouter>
    </AuthProvider>
);

export default App;
