package sevidor.hilo;

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

<<<<<<< HEAD:SocketServidor/src/sevidor/hilo/SocketServidor.java


public class SocketServidor{

	public static final int PUERTO = 2017;
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("      APLICACiÓN DE SERVIDOR CON HILOS     ");
		System.out.println("-------------------------------------------");		
		
		int peticion = 0;
		
		try (ServerSocket servidor = new ServerSocket()){			
			InetSocketAddress direccion = new InetSocketAddress(PUERTO);
			servidor.bind(direccion);

			System.out.println("SERVIDOR: Esperando peticion por el puerto " + PUERTO);
			
			while (true) {
				//Por cada peticion de cliente aceptada se me crea un objeto socket diferente
				Socket socketAlCliente = servidor.accept();
				System.out.println("SERVIDOR: peticion numero " + ++peticion + " recibida");
				//Abrimos un hilo nuevo y liberamos el hilo principal para que pueda
				//recibir peticiones de otros clientes
				new HiloPelicula(socketAlCliente);
			}			
		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error");
			e.printStackTrace();
		}
				
				
	}
		
	
	

}
=======
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
>>>>>>> 3ca4b2c54b26ccc06ecc865dad3e69f5f9883057:SocketServidor/src/servidor/SocketServidor.java

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
