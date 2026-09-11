/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.httpserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 *
 * @author Luiza Gonzalez
 */
public class DatagramTimeClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        DatagramSocket socket = new DatagramSocket(); // Creo un socket UDP, se asigna uno automaticamente
        socket.setSoTimeout(2000); // espera máximo 2 segundos por respuesta
        InetAddress address = InetAddress.getByName("127.0.0.1"); // guardo la direccion de mi pc 

        String ultimaHoraConocida = "(sin datos todavía)";

        while (true) {
            try {
                byte[] request = new byte[1]; // Paquete vacio
                socket.send(new DatagramPacket(request, request.length, address, 4445));//le pregunto la hora al servidor

                byte[] response = new byte[256];//espacio para guardar lo que llegue
                DatagramPacket packet = new DatagramPacket(response, response.length);
                socket.receive(packet); // se queda esperando hasta que llegue algo

                ultimaHoraConocida = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Hora del servidor: " + ultimaHoraConocida);

            } catch (SocketTimeoutException e) {
                System.out.println("Sin respuesta del servidor. Ultima hora conocida: " + ultimaHoraConocida);
            }

            Thread.sleep(5000);
        }
    }
}