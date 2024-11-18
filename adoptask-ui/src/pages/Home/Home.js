import './Home.css'
import Header from '../../components/Header/Header';
import Footer from '../../components/Footer/Footer';

const Home = () => {
    return (
        <div className='home'>
            <Header />
            <h2 className='mt-5 text-center'>Inicio</h2>
            <Footer />
        </div>
    );
};

export default Home;