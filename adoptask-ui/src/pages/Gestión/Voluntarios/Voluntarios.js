import './Voluntarios.css';
import Layout from '../Layout';
import Footer from '../../../components/Footer/Footer';

const Voluntarios = () => {

    const Voluntarios = () => (
        <div className='contenido' id="voluntarios">
            <h3>Voluntarios</h3>
            <Footer />
        </div>
    );

    return (
        <Layout contenido={Voluntarios} />
    );
};

export default Voluntarios;