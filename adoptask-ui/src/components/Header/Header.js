import './Header.css';
import { useAuth } from '../../AuthContext';
import { useState } from 'react';
import { Navbar, Nav, Dropdown } from 'react-bootstrap';
import { NavLink } from 'react-router-dom';
import { UserRound, Pencil, Heart, LogOut } from 'lucide-react';

const Header = () => {
    const { id, foto, logout } = useAuth();

    const [expanded, setExpanded] = useState(false);

    const handleLogout = () => {
        logout();
        window.location.href = '/';
    };

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
                {id ?
                    <Dropdown>
                        <Dropdown.Toggle variant="link">
                            {foto ?
                                <img
                                    src={`/usuarios/${id}/${foto}`}
                                    alt="Foto de perfil"
                                    className="rounded-circle"
                                    width="40"
                                    height="40"
                                />
                                :
                                <UserRound size={40} strokeWidth={1.5} color='#f2f4f3' />}
                        </Dropdown.Toggle>
                        <Dropdown.Menu>
                            <Dropdown.Item href="/perfil"><Pencil size={15} strokeWidth={1.5} />Perfil</Dropdown.Item>
                            <Dropdown.Item href="/favoritos"><Heart size={15} strokeWidth={1.5} />Favoritos</Dropdown.Item>
                            <Dropdown.Divider />
                            <Dropdown.Item onClick={handleLogout}><LogOut size={15} strokeWidth={1.5} />Cerrar sesión</Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>
                    :
                    <a className='login' href='/login'>
                        <UserRound size={40} strokeWidth={1.5} color='#f2f4f3' />
                    </a>}
            </Navbar.Collapse>
        </Navbar>
    );
};

export default Header;