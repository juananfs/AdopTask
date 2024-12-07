import './Tareas.css';
import Layout from '../Layout';
import { TareasProvider } from './TareasContext';
import TareasPendientes from '../../../components/Gestión/Tareas/TareasPendientes';
import TareasEnCurso from '../../../components/Gestión/Tareas/TareasEnCurso';
import TareasCompletadas from '../../../components/Gestión/Tareas/TareasCompletadas';
import { Carousel } from 'react-bootstrap';
import Footer from '../../../components/Footer/Footer';

const Tareas = () => {

    const Tareas = () => (
        <TareasProvider>
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
        </TareasProvider>
    );

    return (
        <Layout contenido={Tareas} />
    );
};

export default Tareas;