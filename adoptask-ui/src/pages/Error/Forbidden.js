import './Error.css'
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

const Forbidden = () => (
    <div className='error-page text-center'>
        <Header />
        <h2 className='mt-5'>403 - Prohibido</h2>
        <p>Lo sentimos, no tiene permiso para acceder a esta página.</p>
        <Footer />
    </div>
);

export default Forbidden;