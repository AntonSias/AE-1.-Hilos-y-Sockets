package sevidor.hilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;





public class HiloPelicula implements Runnable {
	
	
	private static Object scanner;
	private int numCliente = 0;
	private Thread hilo;
	private Socket socketAlCliente;
	private List<PeliculaClass> listaPeliculas = new ArrayList<>();
	private PrintStream out;

	
	
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
			
			Object in;
			switch (numTexto) {
				case 1: {
					// Consultar película por ID
                        int id = ((Object) in).readInt();
                        Pelicula pelicula = getPeliculaPorID(id);
                        out.writeObject(pelicula);
					
				}
				case 2: {
					
					String titulo = in.readUTF();
                    List<Pelicula> peliculasPorTitulo = getPeliculasPorTitulo(titulo);
                    out.writeObject(peliculasPorTitulo);
                    break;
				}
				case 3: {
					//METODO 3
					 String director = in.readUTF();
                     List<Pelicula> peliculasPorDirector = getPeliculasPorDirector(director);
                     out.writeObject(peliculasPorDirector);
                     break;
					        }
				
				case 4: {
					
					//METODO 4
					System.out.print("ID: ");
					
					int id = scanner.nextInt();
					System.out.println("Titulo: ");
					String titulo = scanner.nextInt();
					System.out.println("Director: ");
					String director = scanner.nextInt();
					System.out.println("Precio: ");
					double precio = scanner.nextInt();
					
					HiloPelicula newPelicula = new Pelicula();
					out.writeobjec(new Pelicula);
					out.flush();
					System.out.println("Pelicula agregada exitosamente: ");
					
					
					
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





}
