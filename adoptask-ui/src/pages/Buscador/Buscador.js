import './Buscador.css'
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

const Buscador = () => {
    return (
        <div id='buscador' className='contenido'>
            <Header />
            <h2 className='text-center'>Animales</h2>
            <Footer />
        </div>
    );
};

export default Buscador;