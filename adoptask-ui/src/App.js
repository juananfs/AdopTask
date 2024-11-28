import { AuthProvider } from './AuthContext';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import RutaUsuario from './components/Rutas/RutaUsuario';
import RutaProtectora from './components/Rutas/RutaProtectora';
import NotFound from './pages/Error/NotFound';
import Home from './pages/Home/Home';
import Login from './pages/Login/Login';
import Register from './pages/Login/Register';
import Protectoras from './pages/Protectoras/Protectoras';
import Datos from './pages/Protectoras/Datos/Datos';
import Tareas from './pages/Protectoras/Tareas/Tareas';
import Animales from './pages/Protectoras/Animales/Animales';
import Voluntarios from './pages/Protectoras/Voluntarios/Voluntarios';
import Documentos from './pages/Protectoras/Documentos/Documentos';
import Historial from './pages/Protectoras/Historial/Historial';

const App = () => (
    <AuthProvider>
        <BrowserRouter>
            <div className="App">
                <Switch>
                    <Route exact path='/' component={Home} />
                    <Route path='/login' component={Login} />
                    <Route path='/register' component={Register} />
                    <Route exact path='/protectoras' component={Protectoras} />
                    <RutaProtectora exact path='/protectoras/:id/' component={Datos} />
                    <RutaProtectora path='/protectoras/:id/tareas' component={Tareas} />
                    <RutaProtectora path='/protectoras/:id/animales' component={Animales} />
                    <RutaProtectora path='/protectoras/:id/voluntarios' component={Voluntarios} />
                    <RutaProtectora path='/protectoras/:id/documentos' component={Documentos} />
                    <RutaProtectora path='/protectoras/:id/historial' component={Historial} />
                    <Route component={NotFound} />
                </Switch>
            </div>
        </BrowserRouter>
    </AuthProvider>
);

export default App;
