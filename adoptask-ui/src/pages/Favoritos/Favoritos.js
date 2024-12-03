import './Favoritos.css'
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

const Favoritos = () => {
    return (
        <div id='favoritos' className='contenido'>
            <Header />
            <h2 className='text-center'>Favoritos</h2>
            <Footer />
        </div>
    );
};

export default Favoritos;