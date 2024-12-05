import './Tareas.css';
import { useState } from 'react';
import Layout from '../Layout';
import TareasPendientes from '../../../components/Gestión/Tareas/TareasPendientes';
import TareasEnCurso from '../../../components/Gestión/Tareas/TareasEnCurso';
import TareasCompletadas from '../../../components/Gestión/Tareas/TareasCompletadas';
import { Carousel } from 'react-bootstrap';
import Footer from '../../../components/Footer/Footer';

const Tareas = () => {
    const [reloadKey, setReloadKey] = useState(0);

    const handleReload = () => {
        setReloadKey((prev) => prev + 1);
    }

    const Tareas = () => (
        <div key={reloadKey} className='contenido' id="tareas">
            <h3>Tareas</h3>
            <div id="contenedor" className="d-none d-lg-flex">
                <TareasPendientes reload={handleReload} />
                <TareasEnCurso reload={handleReload} />
                <TareasCompletadas />
            </div>
            <Carousel data-bs-theme="dark" indicators={false} interval={null} className="d-lg-none">
                <Carousel.Item>
                    <TareasPendientes reload={handleReload} />
                </Carousel.Item>
                <Carousel.Item>
                    <TareasEnCurso reload={handleReload} />
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