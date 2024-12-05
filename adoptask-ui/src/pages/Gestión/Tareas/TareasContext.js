import React, { createContext, useState } from 'react';

const TareasContext = createContext();

const TareasProvider = ({ children }) => {
    const [reloadPendientes, setReloadPendientes] = useState(0);
    const [reloadEnCurso, setReloadEnCurso] = useState(0);
    const [reloadCompletadas, setReloadCompletadas] = useState(0);

    return (
        <TareasContext.Provider
            value={{
                reloadPendientes,
                setReloadPendientes,
                reloadEnCurso,
                setReloadEnCurso,
                reloadCompletadas,
                setReloadCompletadas,
            }}
        >
            {children}
        </TareasContext.Provider>
    );
};

const useTareas = () => React.useContext(TareasContext);

export { TareasProvider, useTareas }