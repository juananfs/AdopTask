import Layout from './Layout';
import { Spinner } from 'react-bootstrap';
import Footer from '../../components/Footer/Footer';

const Loading = () => {
    const Loading = () => (
        <div className='contenido' id='loading'>
            <Spinner animation="grow" variant="dark" />
            <Footer />
        </div>
    );

    return (
        <Layout contenido={Loading} />
    );
};

export default Loading;