import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';

const DocumentosAnimal = ({ idAnimal, documentos, reload }) => {
    const { token, logout } = useAuth();
    const { id } = useParams();

    return (
        <div>
            Documentos de {idAnimal}
        </div>
    );
};

export default DocumentosAnimal;