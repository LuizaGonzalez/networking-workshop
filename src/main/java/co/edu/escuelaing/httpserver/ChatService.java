package co.edu.escuelaing.httpserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Luiza Gonzalez
 */
public interface ChatService extends Remote {
    void receiveMessage(String from, String message) throws RemoteException;
}
