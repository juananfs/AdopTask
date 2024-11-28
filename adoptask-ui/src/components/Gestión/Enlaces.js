import { useAuth } from '../../AuthContext';
import { useParams } from 'react-router-dom';
import { NavLink } from 'react-router-dom';
import { Nav } from 'react-bootstrap';
import { Clipboard, PawPrint, Users, Folder, House, History, DoorOpen } from 'lucide-react';

const Enlaces = () => {
    const { permisos, isAdmin } = useAuth();
    const { id } = useParams();

    return (
        <Nav>
            <Nav.Item className="mt-4">
                <h5>Gestión</h5>
                {(isAdmin || permisos.includes("READ_TAREAS")) && <Nav.Link as={NavLink} exact to={`/protectoras/${id}/tareas`}>
                    <Clipboard size={15} strokeWidth={2} />
                    Tareas
                </Nav.Link>}
                {(isAdmin || permisos.includes("READ_ANIMALES")) && <Nav.Link as={NavLink} exact to={`/protectoras/${id}/animales`}>
                    <PawPrint size={15} strokeWidth={2} fill='#f2f4f3' />
                    Animales
                </Nav.Link>}
                {isAdmin && <Nav.Link as={NavLink} exact to={`/protectoras/${id}/voluntarios`}>
                    <Users size={15} strokeWidth={2} fill='#f2f4f3' />
                    Voluntarios
                </Nav.Link>}
                {(isAdmin || permisos.includes("READ_DOCUMENTOS")) && <Nav.Link as={NavLink} exact to={`/protectoras/${id}/documentos`}>
                    <Folder size={15} strokeWidth={1} fill='#f2f4f3' />
                    Documentos
                </Nav.Link>}
            </Nav.Item>
            <Nav.Item className="mt-4">
                <h5>Datos</h5>
                {isAdmin && <Nav.Link as={NavLink} exact to={`/protectoras/${id}/`}>
                    <House size={15} strokeWidth={1} fill='#f2f4f3' color='#357266' />
                    Protectora
                </Nav.Link>}
                {(isAdmin || permisos.includes("READ_HISTORIAL")) && <Nav.Link as={NavLink} exact to={`/protectoras/${id}/historial`}>
                    <History size={15} strokeWidth={2} />
                    Historial
                </Nav.Link>}
            </Nav.Item>
            <Nav.Item className="mt-auto">
                <Nav.Link as={NavLink} exact to='/protectoras'>
                    <DoorOpen size={15} strokeWidth={2} fill='#f2f4f3' />
                    Salir
                </Nav.Link>
            </Nav.Item>
        </Nav>
    );
};

export default Enlaces;