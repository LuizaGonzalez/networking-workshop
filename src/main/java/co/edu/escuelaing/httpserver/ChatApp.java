/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.httpserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Administrador
 */
public class ChatApp {
    public static void main(String[] args) throws Exception {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("Direccion IP remota: ");
        String remoteHost = teclado.readLine().trim();
        
        System.out.print("Puerto remoto: ");
        int remotePort = Integer.parseInt(teclado.readLine().trim());
        
        System.out.print("Puerto local donde publicaras tu servicio: ");
        int localPort = Integer.parseInt(teclado.readLine().trim());
        
        System.out.print("Tu nombre: ");
        String myName = teclado.readLine().trim();
        
        //publicamos el objeto local que recibira los mensajes remotos
        ChatServiceImpl myService = new ChatServiceImpl();
        Registry localRegistry = LocateRegistry.createRegistry(localPort);
        localRegistry.rebind("chat", myService);
        System.out.println("Tu servicio de chat esta publicado en el puerto " + localPort);
        
        // Busca el objeto remoto de la otra persona
        Registry remoteRegistry = LocateRegistry.getRegistry(remoteHost, remotePort);
        ChatService remoteService = (ChatService) remoteRegistry.lookup("chat");
        System.out.println("Conectado al chat remoto. Escribe mensajes (o 'salir' para terminar):");

        // Bucle de envío: lo que escribas se envía invocando el método remoto
        String mensaje;
        System.out.print("Tu: ");
        while ((mensaje = teclado.readLine()) != null) {
            if (mensaje.equalsIgnoreCase("salir")) break;
            remoteService.receiveMessage(myName, mensaje);
            System.out.print("Tu: ");
        }
        
        System.exit(0);
    }
}
