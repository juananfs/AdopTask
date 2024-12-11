import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';

const DatosAnimal = ({ animal, reload }) => {
    const { token, logout } = useAuth();
    const { id } = useParams();

    return (
        <div>
            Datos de {animal.id}
        </div>
    );
};

export default DatosAnimal;