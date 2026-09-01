/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.httpserver;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 *
 * @author luiza.gonzalez-v
 */
public class URLReader {
    public static void main(String[] args) throws Exception {

        URL siteURL = new URI("http://www.google.com/").toURL();
        URLConnection connection = siteURL.openConnection();

        Map<String, List<String>> headers = connection.getHeaderFields();
        Set<Map.Entry<String, List<String>>> entries = headers.entrySet();

        for (Map.Entry<String, List<String>> entry : entries) {
            String headerName = entry.getKey();
            // A null name represents the HTTP status line.
            if (headerName != null) System.out.print(headerName + ":");
            for (String value : entry.getValue()) System.out.print(value);
            System.out.println();
        }
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}
