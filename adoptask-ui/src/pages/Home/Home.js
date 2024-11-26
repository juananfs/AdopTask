import './Home.css'
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

const Home = () => {
    return (
        <div id='home'>
            <Header />
            <h2 className='text-center'>Inicio</h2>
            <Footer />
        </div>
    );
};

export default Home;