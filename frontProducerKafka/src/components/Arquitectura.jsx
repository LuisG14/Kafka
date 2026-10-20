import diagrama from "../assets/arquitectura.png";

export default function Arquitectura() {
    return (
        <div>
            <h2>Arquitectura del sistema</h2>
            <img
                src={diagrama}
                alt="Diagrama de arquitectura: React, Spring Boot y Kafka"
                style={{ maxWidth: "100%", border: "1px solid #ddd", borderRadius: 6 }}
            />
        </div>
    );
}
