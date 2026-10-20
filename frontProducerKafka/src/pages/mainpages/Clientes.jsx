import { useState } from "react";
import ConsultaClientes from "../../components/ConsultaClientes";
import Arquitectura from "../../components/Arquitectura";

const tabStyle = (active) => ({
    padding: "10px 22px",
    fontSize: "16px",
    cursor: "pointer",
    border: "none",
    borderBottom: active ? "3px solid #007bff" : "3px solid transparent",
    background: "transparent",
    color: active ? "#007bff" : "inherit",
    opacity: active ? 1 : 0.75,
    fontWeight: active ? 700 : 400,
});

function Clientes() {
    const [tab, setTab] = useState("consulta");

    return (
        <div style={{ padding: 20 }}>
            <div style={{ display: "flex", borderBottom: "1px solid #ddd", marginBottom: 20 }}>
                <button style={tabStyle(tab === "consulta")} onClick={() => setTab("consulta")}>
                    Consulta
                </button>
                <button style={tabStyle(tab === "arquitectura")} onClick={() => setTab("arquitectura")}>
                    Arquitectura
                </button>
            </div>

            {tab === "consulta" ? <ConsultaClientes /> : <Arquitectura />}
        </div>
    );
}

export default Clientes
