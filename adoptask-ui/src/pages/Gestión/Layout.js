import './Layout.css';
import { useAuth } from '../../AuthContext';
import { useState } from 'react';
import { Navbar, Button, Offcanvas } from 'react-bootstrap';
import { Menu } from 'lucide-react';
import Enlaces from '../../components/Gestión/Enlaces';
import MenuUsuario from '../../components/Header/MenuUsuario';

const Layout = ({ contenido: Componente }) => {
    const { nombreProtectora } = useAuth();
    const [expanded, setExpanded] = useState(false);

    const toggleExpanded = () => setExpanded(prev => !prev);

    return (
        <div id='gestion'>
            <Navbar bg="custom" variant="dark" expand="xl">
                <Button
                    onClick={toggleExpanded}
                    aria-label="Toggle navigation menu"
                    aria-controls="nav"
                    aria-expanded={expanded}
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
                className='d-xl-none'
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