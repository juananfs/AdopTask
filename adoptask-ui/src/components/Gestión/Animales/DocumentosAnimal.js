import "file-icons-js/css/style.css";
import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { File, Trash2 } from 'lucide-react';
import { getClass } from "file-icons-js";
import { Alert, OverlayTrigger, Tooltip, Button } from 'react-bootstrap';
import DocumentoAdd from './DocumentoAdd';

const DocumentosAnimal = ({ idAnimal, documentos, reload }) => {
    const { token, logout, permisos, isAdmin } = useAuth();
    const { id } = useParams();

    const [error, setError] = useState('');

    const handleDelete = (nombreDocumento) => {
        setError('');

        fetch(`/api/protectoras/${id}/animales/${idAnimal}/documentos/${nombreDocumento}`, {
            method: 'DELETE',
            headers: { 'Authorization': 'Bearer ' + token }
        })
            .then(response => {
                if (response.ok) {
                    reload();
                    return;
                }
                if (response.status === 401) {
                    logout();
                }
                throw new Error(response.statusText);
            })
            .catch(error => {
                setError(error.message);
            });
    }

    return (
        <div>
            <h5><File size={20} />({documentos.length}/5)</h5>
            <div id='documentos'>
                {documentos.map((documento) => (
                    <div key={documento.nombre} className="documento">
                        <i className={`icon ${getClass(documento.nombre)}`} />
                        <a
                            href={`/api/protectoras/${id}/animales/${idAnimal}/documentos/${documento.nombre}`}
                            target="_blank"
                            rel="noreferrer"
                        >
                            {documento.nombre}
                        </a>
                        {documento.fecha}
                        {permisos && (isAdmin || permisos.includes("UPDATE_ANIMALES")) &&
                            <OverlayTrigger
                                delay={{ show: 500, hide: 100 }}
                                overlay={<Tooltip>Eliminar</Tooltip>}
                            >
                                <Button onClick={() => handleDelete(documento.nombre)} className='delete'>
                                    <Trash2 size={15} />
                                </Button>
                            </OverlayTrigger>
                        }
                    </div>
                ))}
                {documentos.length < 5 &&
                    <DocumentoAdd idAnimal={idAnimal} size={20} reload={reload} onError={setError} />
                }
            </div>
            {error &&
                <Alert variant="danger" className="text-center mt-4">
                    {error}
                </Alert>
            }
        </div>
    );
};

export default DocumentosAnimal;