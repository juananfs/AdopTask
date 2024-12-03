import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import { useState, useRef, useEffect, useCallback } from 'react';
import { Clipboard, Info, ClipboardPlus } from 'lucide-react';
import { OverlayTrigger, Tooltip, Button, Spinner } from 'react-bootstrap';
import AltaModal from './AltaModal';

const TareasPendientes = () => {
    const { token, logout } = useAuth();
    const { id } = useParams();

    const [tareas, setTareas] = useState([]);
    const [page, setPage] = useState(0);
    const [isLoading, setIsLoading] = useState(false);
    const [hasMore, setHasMore] = useState(true);
    const [loadError, setLoadError] = useState('');
    const [altaShow, setAltaShow] = useState(false);

    const initialLoadDone = useRef(false);
    const containerRef = useRef(null);

    const fetchTareas = useCallback((pageNumber) => {
        setLoadError('');
        setIsLoading(true);

        fetch(`/protectoras/${id}/tareas?estado=PENDIENTE&page=${pageNumber}&size=10`, {
            headers: { 'Authorization': 'Bearer ' + token }
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                if (response.status === 401) {
                    logout();
                    return;
                }
                throw new Error("Ocurrió un error en el servidor. Intentalo más tarde.");
            })
            .then(data => {
                if (data._embedded)
                    setTareas((prev) => [...prev, ...data._embedded.tareaDtoList]);
                setHasMore(data.page.number < data.page.totalPages - 1);
            })
            .catch(error => {
                setLoadError(error.message);
            })
            .finally(() => setIsLoading(false));
    }, [id, logout, token]);

    const handleScroll = useCallback(() => {
        if (isLoading || !hasMore) return;

        const container = containerRef.current;
        if (container) {
            const scrollTop = container.scrollTop;
            const scrollHeight = container.scrollHeight;
            const clientHeight = container.clientHeight;

            if (scrollTop + clientHeight >= scrollHeight - 100) {
                setPage((prev) => prev + 1);
            }
        }
    }, [isLoading, hasMore]);

    useEffect(() => {
        if (page === 0) {
            if (initialLoadDone.current)
                return;
            initialLoadDone.current = true;
        }
        fetchTareas(page);
    }, [page, fetchTareas]);

    useEffect(() => {
        const container = containerRef.current;
        if (container) {
            container.addEventListener('scroll', handleScroll);
        }
        return () => {
            if (container) {
                container.removeEventListener('scroll', handleScroll);
            }
        };
    }, [handleScroll]);

    return (
        <div id="pendientes" className="item">
            <h4><Clipboard size={20} strokeWidth={2.5} />Pendientes</h4>
            {loadError ?
                <p className='text-center'>{loadError}</p>
                :
                <div ref={containerRef} id='lista'>
                    {tareas.map((tarea) => (
                        <div key={tarea.id}>
                            {tarea.titulo}
                            <OverlayTrigger
                                placement='right'
                                delay={{ show: 250, hide: 300 }}
                                overlay={
                                    <Tooltip>
                                        {tarea.descripcion}
                                    </Tooltip>
                                }
                            >
                                <Info size={20} strokeWidth={2.5} />
                            </OverlayTrigger>
                        </div>
                    ))}
                    {isLoading && <Spinner animation="grow" variant="dark" />}
                </div>
            }
            <Button onClick={() => setAltaShow(true)}>
                <ClipboardPlus size={15} strokeWidth={2.5} color='#f2f4f3' />
                AÑADIR TAREA
            </Button>
            <AltaModal
                show={altaShow}
                onHide={() => setAltaShow(false)}
            />
        </div>
    );
};

export default TareasPendientes;