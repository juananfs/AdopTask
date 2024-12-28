import './Home.css'
import Header from '../../components/Header/Header';
import { Button, Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { PawPrint, Users, Clipboard, Search } from 'lucide-react';
import Footer from '../../components/Footer/Footer';

const Home = () => {
  const FeatureCard = ({ icon, title, description }) => (
    <div className="caracteristica">
      <Card className="h-100 text-center">
        <Card.Body>
          <div className="mb-3">{icon}</div>
          <Card.Title>{title}</Card.Title>
          <Card.Text>{description}</Card.Text>
        </Card.Body>
      </Card>
    </div>
  );

  return (
    <div id='home' className='contenido'>
      <Header />
      <h2 className='text-center'>Inicio</h2>
      <section id='bienvenida'>
        <h1 className="display-4 fw-bold mb-4">Bienvenido a AdopTask</h1>
        <p className="lead mb-4">Gestiona tu protectora o encuentra animales en busca de un hogar</p>
        <div>
          <Button as={Link} to="/protectoras" variant="light" size="lg">
            Explorar Protectoras
          </Button>
          <Button as={Link} to="/animales" variant="light" size="lg">
            Explorar Animales
          </Button>
        </div>
      </section>
      <section id='caracteristicas'>
        <h2 className="mb-5">Nuestras Características</h2>
        <div>
          <FeatureCard
            icon={<PawPrint size={48} color='#629677' />}
            title="Gestión de Animales"
            description="Administra fácilmente los datos de tus animales."
          />
          <FeatureCard
            icon={<Users size={48} color='#629677' />}
            title="Coordinación de Voluntarios"
            description="Organiza y gestiona a los voluntarios de tu protectora."
          />
          <FeatureCard
            icon={<Clipboard size={48} color='#629677' />}
            title="Seguimiento de Tareas"
            description="Mantén un registro de todas las tareas y actividades pendientes."
          />
          <FeatureCard
            icon={<Search size={48} color='#629677' />}
            title="Búsqueda de Adopciones"
            description="Facilita el proceso de búsqueda para potenciales adoptantes."
          />
        </div>
      </section >
      <Footer />
    </div >
  );
};

export default Home;

