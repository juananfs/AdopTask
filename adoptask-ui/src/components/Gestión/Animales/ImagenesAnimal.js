import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';

const ImagenesAnimal = ({ idAnimal, imagenes, reload }) => {
    const { token, logout } = useAuth();
    const { id } = useParams();

    return (
        <div>
            Imágenes de {idAnimal}
        </div>
    );
};

export default ImagenesAnimal;