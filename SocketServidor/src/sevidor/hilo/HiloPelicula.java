package sevidor.hilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import scanner;



public class HiloPelicula implements Runnable {
	
	
	private int numCliente = 0;
	private Thread hilo;
	private Socket socketAlCliente;
	private List<PeliculaClass> listaPeliculas = new ArrayList<>();

	
	
	//CONSTRUCTOR SOCKER
	public HiloPelicula(Socket socketAlCliente) {
		numCliente++;
		hilo = new Thread(this, "Cliente_"+numCliente);
		this.socketAlCliente = socketAlCliente;
		
		//dejamos inicializadas la lista de peliculas cada vez
		//que arranquemos un hilo
		 listaPeliculas.add(new PeliculaClass("1", "Pelicula 1", "Director 1", 10));
	     listaPeliculas.add(new PeliculaClass("2", "Pelicula 2", "Director 2", 15));
	     listaPeliculas.add(new PeliculaClass("3", "Pelicula 3", "Director 3", 20));
	     listaPeliculas.add(new PeliculaClass("4", "Pelicula 4", "Director 4", 12));
	     listaPeliculas.add(new PeliculaClass("5", "Pelicula 5", "Director 5", 18));
	     
		hilo.start();
	}
	
	
	
	@Override
	public void run(Object scanner) {
		System.out.println("Estableciendo comunicacion");
		PrintStream salida = null;
		InputStreamReader entrada = null;
		BufferedReader entradaBuffer = null;
		
		try {
		//Salida del servidor al cliente
		salida = new PrintStream(socketAlCliente.getOutputStream());
		//Entrada del servidor al cliente
		entrada = new InputStreamReader(socketAlCliente.getInputStream());
		entradaBuffer = new BufferedReader(entrada);
		
		String texto = "";
		boolean continuar = true;
		int numTexto = 0;
		
		
		//Procesaremos entradas hasta que el texto del cliente sea FIN
		while (continuar) {
			texto = entradaBuffer.readLine();
			numTexto = Integer.valueOf(texto.trim());
			
			switch (numTexto) {
				case 1: {
					//METODO 1
					
				}
				case 2: {
					
					//METODO 2
				}
				case 3: {
					//METODO 3
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
					
				
				case 4: {
					
					//METODO 4
					
				}
				case 5: {
					
					//METODO SALIR
					salida.println("salir");
					System.out.println("Saliendo del programa");
					continuar = false;
					
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + numTexto);
				}
			
			}
		
			//Cerramos el socket
			socketAlCliente.close();
			//Notese que si no cerramos el socket ni en el servidor ni en el cliente, mantendremos
			//la comunicacion abierta
		} catch (IOException e) {
			System.err.println("HiloContadorLetras: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("HiloContadorLetras: Error");
			e.printStackTrace();
		}
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}