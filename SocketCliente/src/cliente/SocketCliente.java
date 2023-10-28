package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketCliente {

    public static final int PUERTO = 7565; // Puerto de comunicación con el servidor
    public static final String IP_SERVER = "localhost"; // IP del servidor

    public static void main(String[] args) {
        System.out.println("APLICACIÓN CLIENTE");
        System.out.println("MENÚ DE OPCIONES:");
        System.out.println("1. Consultar película por ID");
        System.out.println("2. Consultar película por título");
        System.out.println("3. Consultar películas por director");
        System.out.println("4. Añadir película");
        System.out.println("5. Salir de la aplicación");
        System.out.println("-----------------------");

        try (Scanner sc = new Scanner(System.in);
             Socket socketAlServidor = new Socket(IP_SERVER, PUERTO);
             PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
             InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
             BufferedReader bf = new BufferedReader(entrada)) {

            boolean salir = false; // Agregamos un flag para salir del bucle principal

            while (!salir) {
                System.out.print("Elija una opción: ");
                String option = sc.nextLine();

                salida.println(option);

                if (option.equals("5")) {
                    String response = bf.readLine();
                    System.out.println(response);
                    salir = true; // Salir del bucle principal
                } else if (option.equals("3")) {
                    System.out.print("Ingrese el nombre del director: ");
                    String director = sc.nextLine();
                    salida.println(director);
                    String response = bf.readLine();
                    System.out.println(response);
                } else if (option.equals("4")) {
                    // Añadir película
                    System.out.print("Ingrese el ID de la película: ");
                    String idPelicula = sc.nextLine();
                    System.out.print("Ingrese el título de la película: ");
                    String titulo = sc.nextLine().trim();
                    System.out.print("Ingrese el nombre del director: ");
                    String director = sc.nextLine().trim();
                    
                    int precio;
                    while (true) {
                        System.out.print("Ingrese el precio de la película: ");
                        String precioStr = sc.nextLine();
                        try {
                            precio = Integer.parseInt(precioStr);
                            break;  // Sal del bucle si el precio es válido
                        } catch (NumberFormatException e) {
                            System.out.println("El precio proporcionado no es un número válido.");
                        }
                    }

                    salida.println(idPelicula);
                    salida.println(titulo);
                    salida.println(director);
                    salida.println(precio);

                    String response = bf.readLine();
                    if (response.equals("Pelicula ingresada correctamente")) {
                        System.out.println(response);
                    } else {
                        System.out.println("Error al ingresar la película.");
                    }
                } else if (option.equals("1")) {
                    // Consulta por ID
                    System.out.print("Ingrese el ID de la película: ");
                    String idPelicula = sc.nextLine();
                    salida.println(idPelicula);
                    String response = bf.readLine();
                    System.out.println(response);
                } else if (option.equals("2")) {
                    // Consulta por título
                    System.out.print("Ingrese el título de la película: ");
                    String titulo = sc.nextLine();
                    salida.println(titulo);
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
