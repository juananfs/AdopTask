import { useAuth } from '../../AuthContext';
import { Dropdown } from 'react-bootstrap';
import { UserRound, Pencil, Heart, LogOut } from 'lucide-react';

const MenuUsuario = () => {
    const { id, foto, logout } = useAuth();

    const handleLogout = () => {
        logout();
        window.location.href = '/';
    };

    return (
        <div>
            {id ?
                <Dropdown>
                    <Dropdown.Toggle variant="link">
                        <img
                            src={foto ? `/usuarios/${id}/${foto}` : '/foto-perfil.webp'}
                            alt="Foto de perfil"
                            className="rounded-circle"
                            width="40"
                            height="40"
                        />
                    </Dropdown.Toggle>
                    <Dropdown.Menu>
                        <Dropdown.Item href="/perfil"><Pencil size={15} strokeWidth={1.5} />Perfil</Dropdown.Item>
                        <Dropdown.Item href="/favoritos"><Heart size={15} strokeWidth={1.5} />Favoritos</Dropdown.Item>
                        <Dropdown.Divider />
                        <Dropdown.Item onClick={handleLogout}><LogOut size={15} strokeWidth={1.5} />Cerrar sesión</Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
                :
                <a id='login' href='/login'>
                    <UserRound size={40} strokeWidth={1.5} color='#f2f4f3' />
                </a>}
        </div>
    );
};

export default MenuUsuario;