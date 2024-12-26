import { useState, useRef, useEffect } from "react";
import { Button, Accordion, FormCheck } from "react-bootstrap";

const Filtros = ({ categorias, setCategorias, sexos, setSexos, protectoras, setProtectoras }) => {
    const [listaProtectoras, setListaProtectoras] = useState([]);

    const initialLoadDone = useRef(false);

    const handleReset = () => {
        setCategorias([]);
        setSexos([]);
        setProtectoras([]);
    };

    const categoriasOpciones = [
        { value: 'PERROS', label: 'Perros' },
        { value: 'GATOS', label: 'Gatos' },
        { value: 'ROEDORES', label: 'Roedores' },
        { value: 'AVES', label: 'Aves' },
        { value: 'GRANJA', label: 'Granja' },
        { value: 'HURONES', label: 'Hurones' },
        { value: 'REPTILES', label: 'Reptiles' },
        { value: 'ACUATICOS', label: 'Acuáticos' },
        { value: 'SILVESTRES', label: 'Silvestres' },
        { value: 'OTROS', label: 'Otros' }
    ];

    const handleCategoriaChange = (categoria, isChecked) => {
        if (isChecked) {
            setCategorias((prev) => [...prev, categoria]);
        } else {
            setCategorias((prev) => prev.filter((c) => c !== categoria));
        }
    };

    const sexosOpciones = [
        { value: 'MACHO', label: 'Macho' },
        { value: 'HEMBRA', label: 'Hembra' }
    ];

    const handleSexoChange = (sexo, isChecked) => {
        if (isChecked) {
            setSexos((prev) => [...prev, sexo]);
        } else {
            setSexos((prev) => prev.filter((s) => s !== sexo));
        }
    };

    const fetchProtectoras = () => {
        fetch('/protectoras?page=0&size=100')
            .then((response) => response.json())
            .then((data) => {
                if (data._embedded) {
                    setListaProtectoras(data._embedded.resumenProtectoraDtoList);
                }
            });
    };

    useEffect(() => {
        if (!initialLoadDone.current) {
            initialLoadDone.current = true;
            return;
        }
        fetchProtectoras();
    }, []);

    const handleProtectoraChange = (protectora, isChecked) => {
        if (isChecked) {
            setProtectoras((prev) => [...prev, protectora]);
        } else {
            setProtectoras((prev) => prev.filter((p) => p !== protectora));
        }
    };

    return (
        <div id="filtros">
            <Button onClick={handleReset}>BORRAR FILTROS</Button>
            <Accordion defaultActiveKey={['0']} alwaysOpen>
                <Accordion.Item eventKey="0">
                    <Accordion.Header>Categoría</Accordion.Header>
                    <Accordion.Body>
                        {categoriasOpciones.map((categoria) => (
                            <FormCheck
                                key={categoria.value}
                                label={categoria.label}
                                onChange={(e) =>
                                    handleCategoriaChange(categoria.value, e.target.checked)
                                }
                                checked={categorias.includes(categoria.value)}
                            />
                        ))}
                    </Accordion.Body>
                </Accordion.Item>
                <Accordion.Item eventKey="1">
                    <Accordion.Header>Sexo</Accordion.Header>
                    <Accordion.Body>
                        {sexosOpciones.map((sexo) => (
                            <FormCheck
                                key={sexo.value}
                                label={sexo.label}
                                onChange={(e) =>
                                    handleSexoChange(sexo.value, e.target.checked)
                                }
                                checked={sexos.includes(sexo.value)}
                            />
                        ))}
                    </Accordion.Body>
                </Accordion.Item>
                <Accordion.Item eventKey="2">
                    <Accordion.Header>Protectora</Accordion.Header>
                    <Accordion.Body>
                        {listaProtectoras.map((protectora) => (
                            <FormCheck
                                key={protectora.id}
                                label={protectora.nombre}
                                onChange={(e) =>
                                    handleProtectoraChange(protectora.id, e.target.checked)
                                }
                                checked={protectoras.includes(protectora.id)}
                            />
                        ))}
                    </Accordion.Body>
                </Accordion.Item>
            </Accordion>
        </div>
    );
};

export default Filtros;
