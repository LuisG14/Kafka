import { useState } from "react";

export default function ConsultaClientes() {
    const [query, setQuery] = useState("");
    const [clientes, setClientes] = useState(null);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const consultar = async () => {
        setLoading(true);
        setError("");
        try {
            const res = await fetch(
                `http://localhost:8000/clientes?query=${encodeURIComponent(query)}`
            );
            const data = await res.json();
            if (!res.ok) {
                setClientes(null);
                setError(data.error || `Error ${res.status}`);
            } else {
                setClientes(data);
            }
        } catch {
            setClientes(null);
            setError("No se pudo conectar con el back-end (puerto 8000)");
        } finally {
            setLoading(false);
        }
    };

    const cell = { padding: "8px 14px", borderBottom: "1px solid #eee", textAlign: "left" };

    return (
        <div>
            <h2>Consulta de clientes (vía Kafka)</h2>

            <input
                value={query}
                onChange={(e) => setQuery(e.target.value)}
                onKeyDown={(e) => e.key === "Enter" && consultar()}
                placeholder="id, nombre, ciudad o email (vacío = todos)"
                style={{
                    width: "340px",
                    padding: "12px",
                    fontSize: "16px",
                    marginRight: "10px",
                    borderRadius: "5px",
                    border: "1px solid #ccc",
                }}
            />
            <button
                onClick={consultar}
                disabled={loading}
                style={{
                    padding: "12px 20px",
                    fontSize: "16px",
                    backgroundColor: "#007bff",
                    color: "white",
                    border: "none",
                    borderRadius: "5px",
                    cursor: "pointer",
                }}
            >
                {loading ? "Consultando..." : "Consultar"}
            </button>

            {error && <p style={{ color: "crimson", marginTop: 20 }}>{error}</p>}

            {clientes && clientes.length === 0 && (
                <p style={{ marginTop: 20 }}>Sin resultados.</p>
            )}

            {clientes && clientes.length > 0 && (
                <table style={{ marginTop: 20, borderCollapse: "collapse" }}>
                    <thead>
                        <tr>
                            <th style={cell}>ID</th>
                            <th style={cell}>Nombre</th>
                            <th style={cell}>Ciudad</th>
                            <th style={cell}>Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        {clientes.map((c) => (
                            <tr key={c.id}>
                                <td style={cell}>{c.id}</td>
                                <td style={cell}>{c.nombre}</td>
                                <td style={cell}>{c.ciudad}</td>
                                <td style={cell}>{c.email}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}
