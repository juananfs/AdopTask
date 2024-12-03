import './Datos.css';
import Layout from '../Layout';
import Footer from '../../../components/Footer/Footer';

const Datos = () => {

    const Datos = () => (
        <div className='contenido' id="datos">
            <h3>Datos</h3>
            <Footer />
        </div>
    );

    return (
        <Layout contenido={Datos} />
    );
};

export default Datos;