import './Animales.css';
import Layout from '../../../components/Gestión/Layout';
import Footer from '../../../components/Footer/Footer';

const Animales = () => {

    const Animales = () => (
        <div className='contenido' id="animales">
            <h3>Animales</h3>
            <Footer />
        </div>
    );

    return (
        <Layout contenido={Animales} />
    );
};

export default Animales;