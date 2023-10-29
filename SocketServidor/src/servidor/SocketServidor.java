package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
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

        peliculas.add(new Pelicula("A0215", "Encuentro en la tercera fase", "Steven Spielberg", 25));
        peliculas.add(new Pelicula("A025", "La lista de Schindler", "Steven Spielberg", 25));
        peliculas.add(new Pelicula("A752", "Los idiotas", "Lars Von Trier", 18));
        peliculas.add(new Pelicula("A962", "Cache", "Michael Haneke", 12));
        peliculas.add(new Pelicula("A0256", "Mulholland Drive", "David Lynch", 30));

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
                        String directorLimpio = director.trim();
                        boolean encontrada = false;
                        for (Pelicula pelicula : peliculas) {
                            if (pelicula.getDirector().equalsIgnoreCase(directorLimpio)) {
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
	                        agregarPelicula(bf, peliculas, socketAlCliente);
	                    }else if (stringRecibido.equals("4")) {
	                    	PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
	                    	salida.println("Saliendo del programa.");
	                    	socketAlCliente.close();
	                    	
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

    // Metodo añadir pelicula
    private static void agregarPelicula(BufferedReader bf, List<Pelicula> peliculas, Socket socketAlCliente) {
        try {
            String idPelicula = bf.readLine();
            String titulo = bf.readLine();
            String director = bf.readLine();
            int precio;
            try {
            	String precioStr = bf.readLine();
                precio = Integer.parseInt(precioStr);
            } catch (NumberFormatException e) {
                System.err.println("SERVIDOR: Error al convertir precio a entero");
                precio = 0; // Asigna un valor por defecto si el precio no es válido
            }

            synchronized (peliculas) {
                peliculas.add(new Pelicula(idPelicula, titulo, director, precio));
            }

            PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
            salida.println("Película agregada exitosamente.");
        } catch (IOException e) {
            System.err.println("SERVIDOR: Error de entrada/salida al agregar película");
            e.printStackTrace();
        }
    }
}
