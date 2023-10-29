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

    public static final int PUERTO = 7565;

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

                    // Mover la creación del socket para cada solicitud
                    InputStreamReader entrada = new InputStreamReader(socketAlCliente.getInputStream());
                    BufferedReader bf = new BufferedReader(entrada);

                    String stringRecibido = bf.readLine();
                    System.out.println("SERVIDOR: Me ha llegado del cliente: " + stringRecibido);

                    if (stringRecibido.equals("1")) { // Search by ID
                        String id = bf.readLine();
                        Pelicula pelicula = buscarPeliculaPorID(id, peliculas);

                        if (pelicula != null) {
                            PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                            // Envía todos los detalles de la película
                            salida.println("ID: " + pelicula.getIdPelicula() + " Título: " + pelicula.getTitulo() + " Director: " + pelicula.getDirector() + " Precio: " + pelicula.getPrecio());
                        } else {
                            PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                            salida.println("No se encontró ninguna película con el ID proporcionado.");
                        }
                    } else if (stringRecibido.equals("2")) { // Search by Title
                        String titulo = bf.readLine();
                        Pelicula pelicula = buscarPeliculaPorTitulo(titulo, peliculas);

                        if (pelicula != null) {
                            PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                            // Envía todos los detalles de la película
                            salida.println("ID: " + pelicula.getIdPelicula() + " Título: " + pelicula.getTitulo() + " Director: " + pelicula.getDirector() + " Precio: " + pelicula.getPrecio());
                        } else {
                            PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                            salida.println("No se encontró ninguna película con el título proporcionado.");
                        }
                    } else if (stringRecibido.equals("3")) { // Search by Director
                        String director = bf.readLine();
                        String directorLimpio = director.trim();
                        boolean encontrada = false;
                        for (Pelicula pelicula : peliculas) {
                            if (pelicula.getDirector().equalsIgnoreCase(directorLimpio)) {
                                encontrada = true;
                                PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                                salida.println("ID: " + pelicula.getIdPelicula() + " Título: " + pelicula.getTitulo() + " Director: " + pelicula.getDirector() + " Precio: " + pelicula.getPrecio());
                            }
                        }

                        if (!encontrada) {
                            PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                            salida.println("No se encontraron películas para ese director.");
                        }
                    } else if (stringRecibido.equals("4")) { // Exit
                        PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                        salida.println("Hasta luego.");
                    } else {
                        // Simulación de espera
                        Thread.sleep(15000);

                        PrintStream salida = new PrintStream(socketAlCliente.getOutputStream());
                        String resultado = "";
                        salida.println(resultado);
                    }
                } catch (IOException e) {
                    System.out.println("SERVIDOR: Error de entrada/salida");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    System.out.println("SERVIDOR: Error en la espera");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("SERVIDOR: Error de entrada/salida");
            e.printStackTrace();
        }
    }

    private static Pelicula buscarPeliculaPorID(String id, List<Pelicula> peliculas) {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getIdPelicula().equalsIgnoreCase(id)) {
                return pelicula;
            }
        }
        return null; // Película no encontrada
    }

    private static Pelicula buscarPeliculaPorTitulo(String titulo, List<Pelicula> peliculas) {
        for (Pelicula pelicula : peliculas) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                return pelicula;
            }
        }
        return null; // Película no encontrada
    }
    
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