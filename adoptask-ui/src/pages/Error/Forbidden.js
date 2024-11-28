import './Error.css'
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

const Forbidden = () => (
    <div id='error' className='contenido'>
        <Header />
        <h2 className='mt-5 text-center'>403 - Prohibido</h2>
        <p className='text-center'>Lo sentimos, no tienes permiso para acceder a esta página.</p>
        <Footer />
    </div>
);

export default Forbidden;