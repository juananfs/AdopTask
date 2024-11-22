import './Layout.css';
import Footer from '../Footer/Footer'

const Layout = ({ contenido: Componente }) => {

    return (
        <div className='contenedor-1'>
            <div className='contenedor-2'>
                <h1>AdopTask</h1>
                <Componente />
                <Footer />
            </div>
        </div>
    );
};

export default Layout;