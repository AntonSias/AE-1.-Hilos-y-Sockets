package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketCliente {

    public static final int PUERTO = 7565;
    public static final String IP_SERVER = "localhost";

    public static void main(String[] args) {
        System.out.println("APLICACI�N CLIENTE");
        System.out.println("MEN� DE OPCIONES:");
        System.out.println("1. Consultar pel�cula por ID");
        System.out.println("2. Consultar pel�cula por t�tulo");
        System.out.println("3. Consultar pel�culas por director");
        System.out.println("4. A�adir pel�cula");
        System.out.println("5. Salir de la aplicaci�n");
        System.out.println("-----------------------");

        try (Scanner sc = new Scanner(System.in)) {
            boolean salir = false;

            while (!salir) {
                try (Socket socketAlServidor = new Socket(IP_SERVER, PUERTO);
                     PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
                     InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
                     BufferedReader bf = new BufferedReader(entrada)) {
                    System.out.print("Elija una opci�n: ");
                    String option = sc.nextLine().trim();
                    salida.println(option);

                    if (option.equals("5")) {
                        String response = bf.readLine();
                        System.out.println(response);
                        salir = true;
                    } else if (option.equals("3")) {
                        System.out.print("Ingrese el nombre del director: ");
                        String director = sc.nextLine().trim();
                        salida.println(director);
                        String response = bf.readLine();
                        System.out.println(response);
                    } else if (option.equals("4")) {
                        System.out.print("Ingrese el ID de la pel�cula: ");
                        String idPelicula = sc.nextLine().trim();
                        System.out.print("Ingrese el t�tulo de la pel�cula: ");
                        String titulo = sc.nextLine().trim();
                        System.out.print("Ingrese el nombre del director: ");
                        String director = sc.nextLine().trim();
                        
                        int precio;
                        while (true) {
                            System.out.print("Ingrese el precio de la pel�cula: ");
                            String precioStr = sc.nextLine().trim();
                            try {
                                precio = Integer.parseInt(precioStr);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("El precio proporcionado no es un n�mero v�lido.");
                            }
                        }

                        salida.println(idPelicula);
                        salida.println(titulo);
                        salida.println(director);
                        salida.println(precio);

                        String response = bf.readLine();
                        if (response.equals("Pel�cula agregada exitosamente.")) {
                            System.out.println(response);
                        } else {
                            System.out.println("Error al ingresar la pel�cula.");
                        }
                    } else if (option.equals("1")) {
                        System.out.print("Ingrese el ID de la pel�cula: ");
                        String idPelicula = sc.nextLine().trim();
                        salida.println(idPelicula);
                        String response = bf.readLine();
                        System.out.println(response);
                    } else if (option.equals("2")) {
                        System.out.print("Ingrese el t�tulo de la pel�cula: ");
                        String titulo = sc.nextLine().trim();
                        salida.println(titulo);
                        String response = bf.readLine();
                        System.out.println(response);
                    } else {
                        String response = bf.readLine();
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("CLIENTE: Error de entrada/salida");
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("CLIENTE: Error -> " + e);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("CLIENTE: Error -> " + e);
            e.printStackTrace();
        }

        System.out.println("CLIENTE: ---FIN DEL PROGRAMA---");
    }
}