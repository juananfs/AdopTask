import './Documentos.css';
import Layout from '../../../components/Gestión/Layout';
import Footer from '../../../components/Footer/Footer';

const Documentos = () => {

    const Documentos = () => (
        <div className='contenido' id="documentos">
            <h3>Documentos</h3>
            <Footer />
        </div>
    );

    return (
        <Layout contenido={Documentos} />
    );
};

export default Documentos;