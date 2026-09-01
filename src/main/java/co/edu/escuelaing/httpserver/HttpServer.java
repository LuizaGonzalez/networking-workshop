/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package co.edu.escuelaing.httpserver;

import java.net.*;
import java.io.*;

/**
 *
 * @author luiza.gonzalez-v
 */
public class HttpServer {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = new ServerSocket(35000);//creo un socket de servidor
        System.out.println("Ready to receive...");
        boolean running = true;
        while (running) {
            Socket clientSocket = serverSocket.accept();//espero recibir alguna conexion

            PrintWriter out = new PrintWriter(//salida
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(//entrada
                    new InputStreamReader(clientSocket.getInputStream()));
            boolean isFirstLine = true;
            String reqURIStr = "";
            String inputLine; //se pone a leer
            while ((inputLine = in.readLine()) != null) {
                if (isFirstLine) {
                    reqURIStr = inputLine.split(" ")[1];
                    System.out.println("Respuesta Path: " + reqURIStr);
                    isFirstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            String output = "";
            URI reqURI = new URI(reqURIStr);
            if (reqURI.getPath().startsWith("/hello")) {

                String queryStr = reqURI.getQuery();
                System.out.println("Query str: " + queryStr);

                output = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n\r\n"
                        + "{\"Response\":\"Hello word." + queryStr + "\"}";
            } else {
                //Encabezados 1y2, cuerpo lo otro
                output = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n\r\n" //dejo linea intermedia
                        + "<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "    <head>\n"
                        + "        <title>Form Example</title>\n"
                        + "        <meta charset=\"UTF-8\">\n"
                        + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <h1>Form with GET</h1>\n"
                        + "        <form action=\"/hello\">\n"
                        + "            <label for=\"name\">Name:</label><br>\n"
                        + "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n"
                        + "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n"
                        + "        </form>\n"
                        + "        <div id=\"getrespmsg\"></div>\n"
                        + "\n"
                        + "\n"
                        + "        <script>\n"
                        + "            function loadGetMsg() {\n"
                        + "                let nameVar = document.getElementById(\"name\").value;\n"
                        + "                const xhttp = new XMLHttpRequest();\n"
                        + "                xhttp.onload = function() {\n"
                        + "                    document.getElementById(\"getrespmsg\").innerHTML =\n"
                        + "                    this.responseText;\n"
                        + "                }\n"
                        + "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n"
                        + "                xhttp.send();\n"
                        + "            }\n"
                        + "        </script>\n"
                        + "\n"
                        + "\n"
                        + "        <h1>Form with POST</h1>\n"
                        + "        <form action=\"/hellopost\">\n"
                        + "            <label for=\"postname\">Name:</label><br>\n"
                        + "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\n"
                        + "            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n"
                        + "        </form>\n"
                        + "        \n"
                        + "        <div id=\"postrespmsg\"></div>\n"
                        + "        \n"
                        + "        <script>\n"
                        + "            function loadPostMsg(name){\n"
                        + "                let url = \"/hellopost?name=\" + name.value;\n"
                        + "\n"
                        + "\n"
                        + "                fetch (url, {method: 'POST'})\n"
                        + "                    .then(x => x.text())\n"
                        + "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n"
                        + "            }\n"
                        + "        </script>\n"
                        + "    </body>\n"
                        + "</html>";
            }
            out.println(output); //devuelvo por el out

            //cierro puertos
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close ();
    }
}

