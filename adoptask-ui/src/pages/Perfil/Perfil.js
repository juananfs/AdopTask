import './Perfil.css'
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

const Perfil = () => {
    return (
        <div id='perfil' className='contenido'>
            <Header />
            <h2 className='text-center'>Perfil</h2>
            <Footer />
        </div>
    );
};

export default Perfil;