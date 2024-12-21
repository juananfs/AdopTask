import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import { useRef } from 'react';
import { Button } from 'react-bootstrap';
import { Plus } from 'lucide-react';

const DocumentoAdd = ({ idAnimal, size, reload, onError }) => {
    const { token, logout } = useAuth();
    const { id } = useParams();

    const fileInputRef = useRef(null);

    const addDocumento = (event) => {
        const documento = event.target.files[0];
        onError('');

        const formData = new FormData();
        formData.append("documento", documento);

        fetch(`/protectoras/${id}/animales/${idAnimal}/documentos`, {
            method: 'POST',
            headers: { 'Authorization': 'Bearer ' + token },
            body: formData
        })
            .then(response => {
                if (response.status === 201) {
                    reload();
                    return;
                }
                if (response.status === 401) {
                    logout();
                }
                throw new Error(response.statusText);
            })
            .catch(error => {
                onError(error.message);
            });
    }

    return (
        <div>
            <Button onClick={() => fileInputRef.current.click()} variant='light'>
                <Plus size={size} />
            </Button>
            <input
                ref={fileInputRef}
                type="file"
                style={{ display: 'none' }}
                onChange={addDocumento}
            />
        </div>
    );
};

export default DocumentoAdd;