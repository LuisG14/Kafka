Antes de comenzar, asegúrate de tener instalado:

Docker
Docker Compose

Verifica con:

docker --version
docker-compose --version
▶ Ejecutar el entorno

Desde la carpeta donde está tu docker-compose.yml, ejecuta:

docker-compose up -d

Esto levantará los servicios en segundo plano.

▶ Verificar que todo esté corriendo
1. Ver contenedores activos
docker ps

Deberías ver:

zookeeper
kafka
kafdrop
2. Ver logs (opcional)

Para revisar que Kafka inició correctamente:

docker logs kafka

O todos los servicios:

docker-compose logs -f

▶ Acceder a Kafdrop (UI de Kafka)

Abre en tu navegador:

👉 http://localhost:19000

Desde aquí puedes:

Ver topics
Crear topics
Ver mensajes
Monitorear brokers

▶ Crear un topic (desde consola)

Entra al contenedor de Kafka:

docker exec -it kafka bash

Crear un topic:

kafka-topics --create \
  --topic test-topic \
  --bootstrap-server kafka:29092 \
  --partitions 1 \
  --replication-factor 1

Listar topics:

kafka-topics --list --bootstrap-server kafka:29092


▶ Enviar mensajes (Producer)
kafka-console-producer \
  --broker-list kafka:29092 \
  --topic test-topic

Escribe mensajes y presiona Enter.


▶ Consumir mensajes (Consumer)

En otra terminal:

docker exec -it kafka bash
kafka-console-consumer \
  --bootstrap-server kafka:29092 \
  --topic test-topic \
  --from-beginning
 

▶ Detener el entorno

docker-compose down
docker-compose down -v
docker system prune -f


__consumer_offsets

| Tipo            | Qué significa              |
| --------------- | -------------------------- |
| join group      | se conectó un consumer     |
| assignment      | Kafka repartió particiones |
| commit          | guardó progreso            |
| commit otra vez | volvió a guardar progreso  |
