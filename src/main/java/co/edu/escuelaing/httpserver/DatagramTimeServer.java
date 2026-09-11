/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.httpserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;
/**
 *
 * @author Luiza Gonzalez
 */
public class DatagramTimeServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(4445); // Puerto donde se puede escuchar al cliente 
        System.out.println("Servidor de hora (UDP) listo en el puerto 4445...");

        byte[] buffer = new byte[256];

        while (true) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            byte[] response = new Date().toString().getBytes();
            DatagramPacket packet = new DatagramPacket(
                response, response.length, request.getAddress(), request.getPort());
            socket.send(packet);

            System.out.println("Solicitud atendida desde " + request.getAddress());
        }
    }
}
