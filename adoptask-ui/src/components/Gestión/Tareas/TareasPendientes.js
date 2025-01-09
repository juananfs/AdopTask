import { useTareas } from '../../../pages/Gestión/Tareas/TareasContext';
import { useAuth } from '../../../AuthContext';
import { useParams } from 'react-router-dom';
import { useState, useRef, useEffect, useCallback } from 'react';
import { Clipboard, Info, Play, Trash2, ClipboardPlus } from 'lucide-react';
import { OverlayTrigger, Tooltip, Button, Spinner } from 'react-bootstrap';
import AltaModal from './AltaModal';

const TareasPendientes = () => {
    const { reloadPendientes, setReloadPendientes, setReloadEnCurso } = useTareas();
    const { token, logout, isAdmin, permisos } = useAuth();
    const { id } = useParams();

    const [tareas, setTareas] = useState([]);
    const [page, setPage] = useState(0);
    const [isLoading, setIsLoading] = useState(true);
    const [hasMore, setHasMore] = useState(true);
    const [loadError, setLoadError] = useState('');
    const [altaShow, setAltaShow] = useState(false);

    const containerRef = useRef(null);

    const fetchTareas = useCallback((pageNumber) => {
        if (pageNumber < 0)
            return;

        setLoadError('');
        setIsLoading(true);

        fetch(`/api/protectoras/${id}/tareas?estado=PENDIENTE&page=${pageNumber}&size=10`, {
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

    const handleStart = (idTarea) => {
        const tareaData = {
            estado: 'EN_CURSO'
        }

        fetch(`/api/protectoras/${id}/tareas/${idTarea}`, {
            method: 'PATCH',
            headers: {
                'Authorization': 'Bearer ' + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(tareaData)
        })
            .then(response => {
                if (response.ok) {
                    setReloadPendientes((prev) => prev + 1);
                    setReloadEnCurso((prev) => prev + 1);
                }
                if (response.status === 401) {
                    logout();
                }
            });
    };

    const handleDelete = (idTarea) => {
        fetch(`/api/protectoras/${id}/tareas/${idTarea}`, {
            method: 'DELETE',
            headers: { 'Authorization': 'Bearer ' + token }
        })
            .then(response => {
                if (response.ok) {
                    setReloadPendientes((prev) => prev + 1);
                }
                if (response.status === 401) {
                    logout();
                }
            });
    };

    useEffect(() => {
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
        if (reloadPendientes === 0)
            return;
        setTareas([]);
        setPage(-1);
        setHasMore(true);
    }, [reloadPendientes]);

    return (
        <div id="pendientes" className="item">
            <h4><Clipboard size={20} strokeWidth={2.5} />Pendientes</h4>
            {loadError ?
                <p className='text-center'>{loadError}</p>
                :
                <div ref={containerRef} id='lista'>
                    {tareas.map((tarea) => (
                        <div key={tarea.id} className='tarea'>
                            <div id='titulo'>
                                {tarea.titulo}
                                <OverlayTrigger
                                    delay={250}
                                    overlay={
                                        <Tooltip className={tarea.prioridad.toLowerCase()}>
                                            <p className='fw-bold'>{tarea.titulo}</p>
                                            <p>{tarea.descripcion}</p>
                                            <p>PRIORIDAD: <span>{tarea.prioridad}</span></p>
                                        </Tooltip>
                                    }
                                >
                                    <Info size={20} strokeWidth={2.5} className={tarea.prioridad.toLowerCase()} />
                                </OverlayTrigger>
                            </div>
                            <div id='botones'>
                                {permisos && (isAdmin || permisos.includes("UPDATE_TAREAS")) &&
                                    <OverlayTrigger
                                        delay={{ show: 500, hide: 100 }}
                                        overlay={<Tooltip>Empezar</Tooltip>}
                                    >
                                        <Button onClick={() => handleStart(tarea.id)}><Play size={15} strokeWidth={2.5} /></Button>
                                    </OverlayTrigger>
                                }
                                {permisos && (isAdmin || permisos.includes("DELETE_TAREAS")) &&
                                    <OverlayTrigger
                                        delay={{ show: 500, hide: 100 }}
                                        overlay={<Tooltip>Eliminar</Tooltip>}
                                    >
                                        <Button onClick={() => handleDelete(tarea.id)} className='delete'><Trash2 size={15} strokeWidth={2.5} /></Button>
                                    </OverlayTrigger>
                                }
                            </div>
                        </div>
                    ))}
                    {isLoading && <Spinner animation="grow" variant="dark" />}
                </div>
            }
            {permisos && (isAdmin || permisos.includes("CREATE_TAREAS")) &&
                <Button onClick={() => setAltaShow(true)}>
                    <ClipboardPlus size={15} strokeWidth={2.5} color='#f2f4f3' />
                    AÑADIR TAREA
                </Button>
            }
            <AltaModal
                show={altaShow}
                onHide={() => setAltaShow(false)}
                onAlta={() => setReloadPendientes((prev) => prev + 1)}
            />
        </div>
    );
};

export default TareasPendientes;