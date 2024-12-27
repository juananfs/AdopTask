import './Layout.css';
import { useNavigate } from 'react-router-dom';
import Footer from '../../components/Footer/Footer'

const Layout = ({ contenido: Componente }) => {
    const navigate = useNavigate();

    const fadeOut = (href) => {
        setTimeout(() => {
            navigate(href);
        }, 500);

        const h1 = document.querySelector('#contenedor-1 h1');
        const form = document.querySelector('#contenedor-2 form');
        const footer = document.querySelector('#contenedor-2 footer');
        const contenedor = document.querySelector('#contenedor-2');

        h1.classList.add('fadeOut');
        form.classList.add('fadeOut');
        footer.classList.add('fadeOut');
        contenedor.classList.add('scaleOut');
    };

    return (
        <div id='contenedor-1'>
            <div id='contenedor-2'>
                <h1>AdopTask</h1>
                <Componente redirectFunction={fadeOut} />
                <Footer />
            </div>
        </div>
    );
};

export default Layout;