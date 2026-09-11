/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Luiza Gonzalez
 */
public class TrigClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 37000);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
        BufferedReader keyboard = new BufferedReader(
            new InputStreamReader(System.in));

        System.out.println("Escribe un numero, 'fun:sin' / 'fun:cos' / 'fun:tan' para cambiar de funcion, o 'salir':");
        String userInput;
        while ((userInput = keyboard.readLine()) != null) {
            if (userInput.equalsIgnoreCase("salir")) break;
            out.println(userInput);
            System.out.println("Respuesta: " + in.readLine());
        }

        out.close();
        in.close();
        keyboard.close();
        socket.close();
    }
}
