import './Error.css'
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

const NotFound = () => (
    <div className='error-page'>
        <Header />
        <h2 className='mt-5 text-center'>404 - Página no encontrada</h2>
        <p className='text-center'>Lo sentimos, la página que estás buscando no existe.</p>
        <Footer />
    </div>
);

export default NotFound;