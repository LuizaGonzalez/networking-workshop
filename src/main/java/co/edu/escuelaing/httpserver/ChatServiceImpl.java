/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.httpserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 *
 * @author Administrador
 */
public class ChatServiceImpl extends UnicastRemoteObject implements ChatService  {
    
    public ChatServiceImpl() throws RemoteException {
        super();
    }
    
    @Override
    public void receiveMessage(String from, String message) throws RemoteException{
        System.out.println("/n[" + from + "]:" + message);
        System.out.print("Tu: ");
    }
}
