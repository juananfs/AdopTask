import './Layout.css';
import { useAuth } from '../../AuthContext';
import { useState } from 'react';
import { Navbar, Button, Offcanvas } from 'react-bootstrap';
import { Menu } from 'lucide-react';
import Enlaces from './Enlaces';
import MenuUsuario from '../Header/MenuUsuario';

const Layout = ({ contenido: Componente }) => {
    const { nombreProtectora } = useAuth();
    const [expanded, setExpanded] = useState(false);

    return (
        <div id='gestion'>
            <Navbar bg="custom" variant="dark" expand="lg">
                <Button
                    className='d-lg-none'
                    onClick={() => setExpanded(expanded ? false : true)}
                    aria-controls="nav"
                >
                    <Menu size={45} strokeWidth={1.5} color='#f2f4f3' />
                </Button>
                <Navbar.Brand>AdopTask</Navbar.Brand>
                <Navbar.Collapse>
                    <Enlaces />
                </Navbar.Collapse>
            </Navbar>
            <Offcanvas
                id="nav"
                show={expanded}
                onHide={() => setExpanded(false)}
                scroll={true}
            >
                <Offcanvas.Header closeButton />
                <Offcanvas.Body>
                    <Enlaces />
                </Offcanvas.Body>
            </Offcanvas>
            <div id='header'>
                <h2>{nombreProtectora}</h2>
                <MenuUsuario />
            </div>
            <Componente />
        </div>
    );
};

export default Layout;