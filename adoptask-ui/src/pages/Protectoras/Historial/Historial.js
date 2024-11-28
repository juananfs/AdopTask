import './Historial.css';
import Layout from '../../../components/Gestión/Layout';
import Footer from '../../../components/Footer/Footer';

const Historial = () => {

    const Historial = () => (
        <div className='contenido' id="historial">
            <h3>Historial</h3>
            <Footer />
        </div>
    );

    return (
        <Layout contenido={Historial} />
    );
};

export default Historial;