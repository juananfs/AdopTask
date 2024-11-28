import './Tareas.css';
import Layout from '../../../components/Gestión/Layout';
import TareasPendientes from './TareasPendientes';
import TareasEnCurso from './TareasEnCurso';
import TareasCompletadas from './TareasCompletadas';
import { Carousel } from 'react-bootstrap';
import Footer from '../../../components/Footer/Footer';

const Tareas = () => {

    const Tareas = () => (
        <div className='contenido' id="tareas">
            <h3>Tareas</h3>
            <div id="contenedor" className="d-none d-lg-flex">
                <TareasPendientes />
                <TareasEnCurso />
                <TareasCompletadas />
            </div>
            <Carousel data-bs-theme="dark" indicators={false} interval={null} className="d-lg-none">
                <Carousel.Item>
                    <TareasPendientes />
                </Carousel.Item>
                <Carousel.Item>
                    <TareasEnCurso />
                </Carousel.Item>
                <Carousel.Item>
                    <TareasCompletadas />
                </Carousel.Item>
            </Carousel>
            <Footer />
        </div>
    );

    return (
        <Layout contenido={Tareas} />
    );
};

export default Tareas;