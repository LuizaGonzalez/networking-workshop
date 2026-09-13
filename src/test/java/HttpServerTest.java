package co.edu.escuelaing.httpserver;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Luiza Gonzalez
 */
public class HttpServerTest {
   @Test
    void shouldParseSingleQueryParameter() {
        Map<String, String> resultado = HttpServer.parseQuery("name=Luiza");
        assertEquals("Luiza", resultado.get("name"));
    }

    @Test
    void shouldParseMultipleQueryParameters() {
        Map<String, String> resultado = HttpServer.parseQuery("value=5&other=abc");
        assertEquals("5", resultado.get("value"));
        assertEquals("abc", resultado.get("other"));
    }

    @Test
    void shouldReturnEmptyMapWhenQueryIsEmpty() {
        Map<String, String> resultado = HttpServer.parseQuery("");
        assertTrue(resultado.isEmpty());
    }

    @Test
    void shouldReturnEmptyMapWhenQueryIsNull() {
        Map<String, String> resultado = HttpServer.parseQuery(null);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void shouldDecodeUrlEncodedSpaces() {
        Map<String, String> resultado = HttpServer.parseQuery("name=Luiza%20Gonzalez");
        assertEquals("Luiza Gonzalez", resultado.get("name"));
    }

    // ---------- escapeJson ----------

    @Test
    void shouldEscapeDoubleQuotes() {
        String resultado = HttpServer.escapeJson("dijo \"hola\"");
        assertEquals("dijo \\\"hola\\\"", resultado);
    }

    @Test
    void shouldEscapeBackslashes() {
        String resultado = HttpServer.escapeJson("ruta\\archivo");
        assertEquals("ruta\\\\archivo", resultado);
    }

    @Test
    void shouldNotModifyPlainText() {
        String resultado = HttpServer.escapeJson("Luiza");
        assertEquals("Luiza", resultado);
    }

    //contentTypeFor 

    @Test
    void shouldReturnHtmlContentTypeForHtmlFiles() {
        assertEquals("text/html; charset=UTF-8", HttpServer.contentTypeFor("/index.html"));
    }

    @Test
    void shouldReturnJavascriptContentTypeForJsFiles() {
        assertEquals("application/javascript; charset=UTF-8", HttpServer.contentTypeFor("/app.js"));
    }

    @Test
    void shouldReturnPngContentTypeForPngFiles() {
        assertEquals("image/png", HttpServer.contentTypeFor("/logo.png"));
    }

    @Test
    void shouldReturnJpegContentTypeForJpgAndJpegFiles() {
        assertEquals("image/jpeg", HttpServer.contentTypeFor("/foto.jpg"));
        assertEquals("image/jpeg", HttpServer.contentTypeFor("/foto.jpeg"));
    }

    @Test
    void shouldReturnOctetStreamForUnknownExtensions() {
        assertEquals("application/octet-stream", HttpServer.contentTypeFor("/archivo.xyz"));
    } 
}