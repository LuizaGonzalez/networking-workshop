/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.httpserver;

/**
 *
 * @author Luiza Gonzalez
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SquareServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(36000);
        System.out.println("Servidor de cuadrado listo, esperando en el puerto 36000...");

        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibido: " + inputLine);
            try {
                double numero = Double.parseDouble(inputLine);
                double cuadrado = numero * numero;
                out.println(cuadrado);
            } catch (NumberFormatException e) {
                out.println("Error: entrada no numerica");
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}