import { AuthProvider } from './AuthContext';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import RutaUsuario from './components/Rutas/RutaUsuario';
import RutaProtectora from './components/Rutas/RutaProtectora';
import NotFound from './pages/Error/NotFound';
import Home from './pages/Home/Home';
import Login from './pages/Login/Login';
import Register from './pages/Login/Register';
import Perfil from './pages/Perfil/Perfil';
import Favoritos from './pages/Favoritos/Favoritos';
import Buscador from './pages/Buscador/Buscador';
import Protectoras from './pages/Protectoras/Protectoras';
import Datos from './pages/Gestión/Datos/Datos';
import Tareas from './pages/Gestión/Tareas/Tareas';
import Animales from './pages/Gestión/Animales/Animales';
import Voluntarios from './pages/Gestión/Voluntarios/Voluntarios';
import Documentos from './pages/Gestión/Documentos/Documentos';
import Historial from './pages/Gestión/Historial/Historial';

const App = () => (
    <AuthProvider>
        <BrowserRouter>
            <div className="App">
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/perfil" element={<RutaUsuario element={Perfil} />} />
                    <Route path="/favoritos" element={<RutaUsuario element={Favoritos} />} />
                    <Route path="/animales" element={<Buscador />} />
                    <Route path="/protectoras" element={<Protectoras />} />
                    <Route path="/protectoras/:id" element={<RutaProtectora element={Datos} />} />
                    <Route path="/protectoras/:id/tareas" element={<RutaProtectora element={Tareas} />} />
                    <Route path="/protectoras/:id/animales" element={<RutaProtectora element={Animales} />} />
                    <Route path="/protectoras/:id/voluntarios" element={<RutaProtectora element={Voluntarios} />} />
                    <Route path="/protectoras/:id/documentos" element={<RutaProtectora element={Documentos} />} />
                    <Route path="/protectoras/:id/historial" element={<RutaProtectora element={Historial} />} />
                    <Route path="*" element={<NotFound />} />
                </Routes>
            </div>
        </BrowserRouter>
    </AuthProvider>
);

export default App;
