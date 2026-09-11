# Networking 

Convierte un servidor Java basado en sockets en una pequeña aplicación web secuencial 
que sirve HTML, JavaScript e imágenes, expone algunos servicios codificados y se ejecuta 
en una instancia EC2.

## Parte 1 - Introducción a la nomenclaura, redes, clientes y servicios con Java

| Ejercicio | Clases | Descripción | Cómo correrlo |
|---|---|---|---|
| 1 | ReadURL.java | Imprime los 8 componentes de un objeto URL | Run File |
| 3.2 | URLReader.java | Lee una página web línea por línea | Run File |
| 3.3 | *(incluido en URLReader/HeaderReader)* | Lee encabezados de respuesta HTTP | Run File |
| 2 | SavePageToFile.java | Pide una URL, descarga la página y la guarda en *result.html* | Run File, ingresa la URL cuando te la pida |
| 4.1/4.2 | EchoServer.java, EchoClient.java | Servidor/cliente de eco por sockets TCP | Server primero (Run File), luego Client |
| 4.3.1 | SquareServer.java, SquareClient.java | Servidor TCP en el puerto 36000: recibe un número y devuelve su cuadrado | Server primero, luego Client |
| 4.3.2 | TrigServer.java, TrigClient.java | Servidor TCP en el puerto 37000: aplica seno/coseno/tangente sobre un número, con estado (fun:sin, fun:cos, fun:tan) | Server primero, luego Client |
| 4.4/4.5.1 | HttpServer.java | Servidor web en el puerto 35000, acepta múltiples solicitudes consecutivas | Run File (Main Class del proyecto) |
| 5.2.1 | DatagramTimeServer.java, DatagramTimeClient.java | Servidor/cliente UDP en el puerto 4445: el cliente pide la hora cada 5 segundos y sigue funcionando aunque el servidor se caiga y reinicie | Server primero, luego Client |
| 6.4.1 | ChatApp.java, ChatService.java, ChatServiceImpl.java | Chat bidireccional usando RMI cada instancia publica un objeto remoto y se conecta al de la otra persona | Corre `ChatApp` dos veces (dos instancias), cada una pide IP remota, puerto remoto, puerto local y nombre |

### Notas de ejecución
- Cada servidor de sockets usa un puerto distinto (35000 *HttpServer*, 36000 *SquareServer*, 37000 *TrigServer*, 4445 *DatagramTimeServer*) para poder correr varios a la vez sin choques.
- En NetBeans: para correr una clase que **no** sea la "Main Class" configurada del proyecto, usa siempre clic derecho sobre el archivo, **Run File** (no el botón verde de la barra de herramientas, que solo corre la Main Class).

## Parte 2: Desde un servidor HTTP mínimo hasta una aplicación web en AWS

Construye sobre `HttpServer.java` para agregar recursos estáticos, servicios codificados, un cliente JavaScript asíncrono, y despliegue en una instancia AWS EC2.

---

## Requisitos previos
- Java 21 (o compatible)
- Maven
- NetBeans (opcional, usado para el desarrollo)

## Cómo compilar
```bash
mvn compile
```