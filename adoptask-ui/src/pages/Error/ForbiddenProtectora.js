import './Error.css'
import Layout from '../../components/Gestión/Layout';
import Footer from '../../components/Footer/Footer';

const ForbiddenProtectora = () => {
    const Forbidden = () => (
        <div className='contenido'>
            <h2 className='mt-5 text-center'>No tienes permiso</h2>
            <p className='text-center'>Contacta con el administrador de la protectora para obtener acceso a esta página.</p>
            <Footer />
        </div>
    );

    return (
        <Layout contenido={Forbidden} />
    );
};

export default ForbiddenProtectora;