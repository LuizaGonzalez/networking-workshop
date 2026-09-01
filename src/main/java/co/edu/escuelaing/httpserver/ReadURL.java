/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.httpserver;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;


/**
 *
 * @author luiza.gonzalez-v
 */
public class ReadURL {
    public static void main(String[] args) throws URISyntaxException, MalformedURLException{
        URL personalSite = new URI("http://ldbn.escuelaing.edu.co:8080/respuestasexamen.txt?year=2026&semestre=7#projects").toURL();
      
        
        System.out.println("Protocol:" + personalSite.getProtocol());
        System.out.println("Authority :" + personalSite.getAuthority());
        System.out.println("Host :" + personalSite.getHost());
        System.out.println("Port :" + personalSite.getPort());
        System.out.println("Path:" + personalSite.getPath());
        System.out.println("Query:" + personalSite.getQuery());
        System.out.println("File:" + personalSite.getFile());
        System.out.println("Ref :" + personalSite.getRef());
    }
}
