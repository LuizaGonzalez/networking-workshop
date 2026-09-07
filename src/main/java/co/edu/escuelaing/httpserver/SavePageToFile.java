/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.escuelaing.httpserver;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
/**
 *
 * @author Administrador
 */
public class SavePageToFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa la URL a descargar: ");
        String inputUrl = scanner.nextLine();

        try {
            URL url = new URI(inputUrl).toURL();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                 FileWriter writer = new FileWriter("result.html", StandardCharsets.UTF_8)) {

                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.write(System.lineSeparator());
                }
            }

            System.out.println("Página guardada en result.html");

        } catch (Exception e) {
            System.err.println("Error al descargar la página: " + e.getMessage());
        }
    }
}
