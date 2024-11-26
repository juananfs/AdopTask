import './Header.css';
import { useState } from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';
import MenuUsuario from './MenuUsuario';

const Header = () => {
    const [expanded, setExpanded] = useState(false);

    return (
        <Navbar bg="custom" variant="dark" expand="lg" expanded={expanded}>
            <Navbar.Brand href="/">AdopTask</Navbar.Brand>
            <Navbar.Toggle
                aria-controls="nav"
                onClick={() => setExpanded(expanded ? false : "expanded")}
            />
            <Navbar.Collapse id="nav">
                <Nav>
                    <Nav.Link as={NavLink} exact to="/">Inicio</Nav.Link>
                    <Nav.Link as={NavLink} exact to="/protectoras">Protectoras</Nav.Link>
                    <Nav.Link as={NavLink} exact to="/animales">Animales</Nav.Link>
                </Nav>
                <MenuUsuario />
            </Navbar.Collapse>
        </Navbar>
    );
};

export default Header;