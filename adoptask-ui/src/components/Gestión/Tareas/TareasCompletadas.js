import { useTareas } from '../../../pages/Gestión/Tareas/TareasContext';
import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import { useState, useRef, useEffect, useCallback } from 'react';
import { ClipboardCheck, Info, Trash2 } from 'lucide-react';
import { OverlayTrigger, Tooltip, Button, Spinner } from 'react-bootstrap';

const TareasCompletadas = () => {
    const { reloadCompletadas } = useTareas();
    const { token, logout, isAdmin, permisos } = useAuth();
    const { id } = useParams();

    const [tareas, setTareas] = useState([]);
    const [page, setPage] = useState(0);
    const [isLoading, setIsLoading] = useState(true);
    const [hasMore, setHasMore] = useState(true);
    const [loadError, setLoadError] = useState('');

    const initialFetch = useRef(true);
    const containerRef = useRef(null);

    const fetchTareas = useCallback((pageNumber) => {
        if (pageNumber < 0)
            return;

        setLoadError('');
        setIsLoading(true);

        fetch(`/protectoras/${id}/tareas?estado=COMPLETADA&page=${pageNumber}&size=10`, {
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

            if (scrollTop + clientHeight >= scrollHeight - 10) {
                setPage((prev) => prev + 1);
            }
        }
    }, [isLoading, hasMore]);

    const reload = useCallback(() => {
        setTareas([]);
        setPage(-1);
        setHasMore(true);
    }, []);

    const handleDelete = (idTarea) => {
        fetch(`/protectoras/${id}/tareas/${idTarea}`, {
            method: 'DELETE',
            headers: { 'Authorization': 'Bearer ' + token }
        })
            .then(response => {
                if (response.ok) {
                    reload();
                }
                if (response.status === 401) {
                    logout();
                }
            });
    };

    useEffect(() => {
        if (initialFetch.current) {
            initialFetch.current = false;
            return;
        }
        fetchTareas(page);
    }, [page, fetchTareas]);

    useEffect(() => {
        const container = containerRef.current;

        if (container && !isLoading && hasMore) {
            const scrollHeight = container.scrollHeight;
            const clientHeight = container.clientHeight;

            if (scrollHeight <= clientHeight) {
                setPage((prev) => prev + 1);
            }
        }
    }, [isLoading, hasMore]);

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

    useEffect(() => {
        if (reloadCompletadas === 0)
            return;
        reload();
    }, [reload, reloadCompletadas]);

    return (
        <div id="completadas" className="item">
            <h4><ClipboardCheck size={20} strokeWidth={2.5} />Completadas</h4>
            {loadError ?
                <p className='text-center'>{loadError}</p>
                :
                <div ref={containerRef} id='lista'>
                    {tareas.map((tarea) => (
                        <div key={tarea.id} className='tarea completada'>
                            <div id='titulo' >
                                {tarea.titulo}
                                <OverlayTrigger
                                    delay={250}
                                    overlay={
                                        <Tooltip className={tarea.prioridad.toLowerCase()}>
                                            <p className='fw-bold'>{tarea.titulo}</p>
                                            <p>{tarea.descripcion}</p>
                                            <p>PRIORIDAD: <span>{tarea.prioridad}</span></p>
                                            <p>ENCARGADO: {tarea.encargado}</p>
                                        </Tooltip>
                                    }
                                >
                                    <Info size={20} strokeWidth={2.5} className={tarea.prioridad.toLowerCase()} />
                                </OverlayTrigger>
                            </div>
                            {permisos && (isAdmin || permisos.includes("DELETE_TAREAS")) &&
                                <OverlayTrigger
                                    delay={100}
                                    overlay={<Tooltip>Eliminar</Tooltip>}
                                >
                                    <Button onClick={() => handleDelete(tarea.id)} className='delete'><Trash2 size={15} strokeWidth={2.5} /></Button>
                                </OverlayTrigger>
                            }
                        </div>
                    ))}
                    {isLoading && <Spinner animation="grow" variant="dark" />}
                </div>
            }
        </div>
    );
};

export default TareasCompletadas;