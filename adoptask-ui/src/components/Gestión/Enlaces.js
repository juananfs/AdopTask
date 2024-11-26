import { useParams } from 'react-router-dom';
import { NavLink } from 'react-router-dom';
import { Nav } from 'react-bootstrap';
import { Clipboard, PawPrint, Users, Folder, House, Clock, DoorOpen } from 'lucide-react';

const Enlaces = () => {
    const { id } = useParams();

    return (
        <Nav>
            <Nav.Item className="mt-4">
                <h5>Gestión</h5>
                <Nav.Link as={NavLink} exact to={`/protectoras/${id}/tareas`}>
                    <Clipboard size={15} strokeWidth={2} />
                    Tareas
                </Nav.Link>
                <Nav.Link as={NavLink} exact to={`/protectoras/${id}/animales`}>
                    <PawPrint size={15} strokeWidth={2} fill='#f2f4f3' />
                    Animales
                </Nav.Link>
                <Nav.Link as={NavLink} exact to={`/protectoras/${id}/voluntarios`}>
                    <Users size={15} strokeWidth={2} fill='#f2f4f3' />
                    Voluntarios
                </Nav.Link>
                <Nav.Link as={NavLink} exact to={`/protectoras/${id}/archivos`}>
                    <Folder size={15} strokeWidth={1} fill='#f2f4f3' />
                    Archivos
                </Nav.Link>
            </Nav.Item>
            <Nav.Item className="mt-4">
                <h5>Datos</h5>
                <Nav.Link as={NavLink} exact to={`/protectoras/${id}/datos`}>
                    <House size={15} strokeWidth={1} fill='#f2f4f3' color='#357266' />
                    Protectora
                </Nav.Link>
                <Nav.Link as={NavLink} exact to={`/protectoras/${id}/historial`}>
                    <Clock size={15} strokeWidth={2} fill='#f2f4f3' color='#357266' />
                    Historial
                </Nav.Link>
            </Nav.Item>
            <Nav.Item className="mt-auto">
                <Nav.Link as={NavLink} exact to='/'>
                    <DoorOpen size={15} strokeWidth={2} fill='#f2f4f3' />
                    Salir
                </Nav.Link>
            </Nav.Item>
        </Nav>
    );
};

export default Enlaces;