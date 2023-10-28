package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServidor {

    public static final int PUERTO = 2020;

    public static void main(String[] args) {
        System.out.println("APLICACIÓN DE SERVIDOR");
        System.out.println("----------------------");

        List<Pelicula> peliculas = new ArrayList<>();

        peliculas.add(new Pelicula("A0215", "Encuentro en la tercera fase", "Steven Spillberg", 25));
        peliculas.add(new Pelicula("A025", "La lista de Schindler ", "Steven Spillberg", 25));
        peliculas.add(new Pelicula("A752", "Los idiotas", "Lars Von Trier", 18));
        peliculas.add(new Pelicula("A962", "Cache", "Michael Haneke", 12));
        peliculas.add(new Pelicula("A0256", "Mullholand Drive", "David Lynch", 30));

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            int peticion = 0;

            while (true) {
                System.out.println("SERVIDOR: Esperando peticiones en el puerto " + PUERTO);

                try (Socket socketAlCliente = serverSocket.accept()) {
                    System.out.println("SERVIDOR: Petición número " + ++peticion + " recibida");

                    InputStreamReader entrada = new InputStreamReader(socketAlCliente.getInputStream());
                    BufferedReader bf = new BufferedReader(entrada);

                    String stringRecibido = bf.readLine();
                    System.out.println("SERVIDOR: Me ha llegado del cliente: " + stringRecibido);

                    if (stringRecibido.equals("3")) {
                        String director = bf.readLine();
                        boolean encontrada = false;
                        for (Pelicula pelicula : peliculas) {
                            if (pelicula.getDirector().equalsIgnoreCase(director)) {
                                encontrada = true;
                                PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                                salida.println(pelicula.getIdPelicula() + " - " + pelicula.getTitulo());
                            }
                        }

                        if (!encontrada) {
                            PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                            salida.println("No se encontraron películas para ese director.");
                        }
                    } else if (stringRecibido.equals("5")) {
                        // Agregar una nueva película
                        String idPelicula = bf.readLine();
                        String titulo = bf.readLine();
                        String director = bf.readLine();
                        int precio = Integer.parseInt(bf.readLine());

                        synchronized (peliculas) {
                            peliculas.add(new Pelicula(idPelicula, titulo, director, precio));
                        }

                        PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                        salida.println("Película agregada exitosamente.");
                    } else {
                        // Simulación de espera
                        Thread.sleep(15000);

                        PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                        String resultado = "";
                        salida.println(resultado);
                    }
                } catch (IOException e) {
                    System.err.println("SERVIDOR: Error de entrada/salida");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    System.err.println("SERVIDOR: Error en la espera");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("SERVIDOR: Error de entrada/salida");
            e.printStackTrace();
        }
    }
}
