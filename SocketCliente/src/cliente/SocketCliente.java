package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketCliente {

    public static final int PUERTO = 2020;
    public static final String IP_SERVER = "localhost";

    public static void main(String[] args) {
        System.out.println("APLICACIÓN CLIENTE");
        System.out.println("MENÚ DE OPCIONES:");
        System.out.println("1. Consultar película por ID");
        System.out.println("2. Consultar película por título");
        System.out.println("3. Consultar películas por director");
        System.out.println("4. Salir de la aplicación");
        System.out.println("5. Añadir película");
        System.out.println("-----------------------");

        try (Scanner userInput = new Scanner(System.in);
             Socket socketAlServidor = new Socket(IP_SERVER, PUERTO);
             PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
             InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
             BufferedReader bf = new BufferedReader(entrada)) {

            while (true) {
                System.out.print("Elija una opción: ");
                String option = userInput.nextLine();

                salida.println(option);

                if (option.equals("4")) {
                    String response = bf.readLine();
                    System.out.println(response);
                    break;
                } else if (option.equals("2")) {
                    System.out.print("Ingrese el título de la película: ");
                    String title = userInput.nextLine();
                    salida.println(title); // Enviar el título al servidor
                    String response = bf.readLine();
                    System.out.println(response);
                } else if (option.equals("3")) {
                    System.out.print("Ingrese el nombre del director: ");
                    String director = userInput.nextLine();
                    salida.println(director); // Enviar el director al servidor
                    String response = bf.readLine();
                    System.out.println(response);
                } else if (option.equals("5")) {
                    // Añadir película
                    System.out.print("Ingrese el ID de la película: ");
                    String idPelicula = userInput.nextLine();
                    System.out.print("Ingrese el título de la película: ");
                    String titulo = userInput.nextLine().trim();
                    System.out.print("Ingrese el nombre del director: ");
                    String director = userInput.nextLine().trim();
                    System.out.print("Ingrese el precio de la película: ");
                    int precio = userInput.nextInt();

                    userInput.nextLine(); // Limpiar el salto de línea

                    salida.println(option);
                    salida.println(idPelicula);
                    salida.println(titulo);
                    salida.println(director);
                    salida.println(precio);

                    String response = bf.readLine();
                    System.out.println(response);
                } else {
                    String response = bf.readLine();
                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            System.out.println("CLIENTE: Error de entrada/salida");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("CLIENTE: Error -> " + e);
            e.printStackTrace();
        }

        System.out.println("CLIENTE: ---FIN DEL PROGRAMA---");
    }
}
