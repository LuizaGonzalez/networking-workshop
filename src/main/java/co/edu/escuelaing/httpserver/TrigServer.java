/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Luiza Gonzalez
 */
public class TrigServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(37000);
        System.out.println("Servidor trigonometrico listo, esperando en el puerto 37000...");

        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));

        String funcionActual = "cos";

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibido: " + inputLine);

            if (inputLine.startsWith("fun:")) {
                String nuevaFuncion = inputLine.substring(4).trim();
                if (nuevaFuncion.equals("sin") || nuevaFuncion.equals("cos") || nuevaFuncion.equals("tan")) {
                    funcionActual = nuevaFuncion;
                    out.println("Funcion cambiada a: " + funcionActual);
                } else {
                    out.println("Error: funcion no reconocida");
                }
            } else {
                try {
                    double x = Double.parseDouble(inputLine);
                    double resultado = switch (funcionActual) {
                        case "sin" -> Math.sin(x);
                        case "tan" -> Math.tan(x);
                        default -> Math.cos(x);
                    };
                    out.println(resultado);
                } catch (NumberFormatException e) {
                    out.println("Error: entrada no válida");
                }
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}