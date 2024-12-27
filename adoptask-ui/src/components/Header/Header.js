import './Header.css';
import { useState } from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';
import MenuUsuario from './MenuUsuario';

const Header = () => {
    const [expanded, setExpanded] = useState(false);

    return (
        <Navbar bg="custom" variant="dark" expand="lg" expanded={expanded}>
            <Navbar.Brand as={NavLink} to="/">AdopTask</Navbar.Brand>
            <Navbar.Toggle
                aria-controls="nav"
                onClick={() => setExpanded(expanded ? false : "expanded")}
            />
            <Navbar.Collapse id="nav">
                <Nav>
                    <Nav.Link as={NavLink} to="/" end>Inicio</Nav.Link>
                    <Nav.Link as={NavLink} to="/protectoras" end>Protectoras</Nav.Link>
                    <Nav.Link as={NavLink} to="/animales" end>Animales</Nav.Link>
                </Nav>
                <MenuUsuario />
            </Navbar.Collapse>
        </Navbar>
    );
};

export default Header;