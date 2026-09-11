/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package co.edu.escuelaing.httpserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author luiza.gonzalez-v
 */
public class HttpServer {

    private static final int PORT = 35000;
    private static final String RESOURCE_ROOT = "/public";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Ready to receive on port " + PORT + "...");

        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                handleRequest(clientSocket);
            } catch (IOException e) {
                System.out.println("Error atendiendo una solicitud: " + e.getMessage());
            }
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        OutputStream rawOut = new BufferedOutputStream(clientSocket.getOutputStream());

        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            return;
        }
        System.out.println("Request: " + requestLine);

        // Consume el resto de encabezados hasta la línea en blanco (no nos interesa su contenido aquí)
        String headerLine;
        while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
            // ignorado intencionalmente
        }

        String[] parts = requestLine.split(" ");
        if (parts.length < 2) {
            sendError(rawOut, 400, "Bad Request");
            return;
        }
        String method = parts[0];
        String rawUri = parts[1];

        if (!method.equals("GET")) {
            sendError(rawOut, 405, "Method Not Allowed");
            return;
        }

        URI reqURI;
        try {
            reqURI = new URI(rawUri);
        } catch (URISyntaxException e) {
            sendError(rawOut, 400, "Bad Request");
            return;
        }

        String path = reqURI.getPath();
        String query = reqURI.getQuery();

        switch (path) {
            case "/hello" -> handleHello(rawOut, query);
            case "/square" -> handleSquare(rawOut, query);
            case "/time" -> handleTime(rawOut);
            case "/health" -> handleHealth(rawOut);
            default -> handleStaticResource(rawOut, path);
        }
    }

    // ---------- Servicios dinámicos ----------

    private static void handleHello(OutputStream out, String query) throws IOException {
        Map<String, String> params = parseQuery(query);
        String name = params.getOrDefault("name", "mundo");
        String json = "{\"greeting\":\"Hola, " + escapeJson(name) + "!\"}";
        sendJson(out, json);
    }

    private static void handleSquare(OutputStream out, String query) throws IOException {
        Map<String, String> params = parseQuery(query);
        String value = params.get("value");
        if (value == null) {
            sendError(out, 400, "Falta el parámetro 'value'");
            return;
        }
        try {
            double numero = Double.parseDouble(value);
            double cuadrado = numero * numero;
            String json = "{\"input\":" + numero + ",\"result\":" + cuadrado + "}";
            sendJson(out, json);
        } catch (NumberFormatException e) {
            sendError(out, 400, "'value' no es un número válido");
        }
    }

    private static void handleTime(OutputStream out) throws IOException {
        String json = "{\"time\":\"" + LocalDateTime.now() + "\"}";
        sendJson(out, json);
    }

    private static void handleHealth(OutputStream out) throws IOException {
        sendJson(out, "{\"status\":\"ok\"}");
    }

    // ---------- Recursos estáticos ----------

    private static void handleStaticResource(OutputStream out, String path) throws IOException {
        if (path == null || path.equals("/")) {
            path = "/index.html";
        }

        // Normaliza y rechaza cualquier intento de salir del área pública (path traversal)
        String normalized = java.nio.file.Paths.get(path).normalize().toString().replace("\\", "/");
        if (normalized.contains("..") || !normalized.startsWith("/")) {
            sendError(out, 400, "Ruta inválida");
            return;
        }

        String resourcePath = RESOURCE_ROOT + normalized;
        try (InputStream resourceStream = HttpServer.class.getResourceAsStream(resourcePath)) {
            if (resourceStream == null) {
                sendError(out, 404, "No encontrado: " + normalized);
                return;
            }
            byte[] content = resourceStream.readAllBytes();
            String contentType = contentTypeFor(normalized);
            sendBytes(out, 200, "OK", contentType, content);
        }
    }

    private static String contentTypeFor(String path) {
        String lower = path.toLowerCase();
        if (lower.endsWith(".html")) return "text/html; charset=UTF-8";
        if (lower.endsWith(".js")) return "application/javascript; charset=UTF-8";
        if (lower.endsWith(".png")) return "image/png";
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";
        return "application/octet-stream";
    }

    // ---------- Utilidades de respuesta ----------

    private static void sendJson(OutputStream out, String json) throws IOException {
        sendBytes(out, 200, "OK", "application/json; charset=UTF-8",
                json.getBytes(StandardCharsets.UTF_8));
    }

    private static void sendError(OutputStream out, int code, String message) throws IOException {
        String body = "{\"error\":\"" + escapeJson(message) + "\"}";
        sendBytes(out, code, message, "application/json; charset=UTF-8",
                body.getBytes(StandardCharsets.UTF_8));
    }

    private static void sendBytes(OutputStream out, int statusCode, String statusText,
                                   String contentType, byte[] body) throws IOException {
        String header = "HTTP/1.1 " + statusCode + " " + statusText + "\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + body.length + "\r\n"
                + "\r\n";
        out.write(header.getBytes(StandardCharsets.UTF_8));
        out.write(body);
        out.flush();
    }

    private static Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return params;
        }
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            String key = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
            String value = kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "";
            params.put(key, value);
        }
        return params;
    }

    private static String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

