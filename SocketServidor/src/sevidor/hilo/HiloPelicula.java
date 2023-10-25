package sevidor.hilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class HiloPelicula implements Runnable {
	
	private String idPelicula;
	private String titulo;
	private String director;
	private int precio;
	private Thread hilo;
	private Socket socketAlCliente;
	
	//CONSTRUCTOR POR DEFECTO:
		public HiloPelicula() {
			super();
		}
	
	// CONSTRUCTOR CON TODOD:
	public HiloPelicula(String idPelicula, String titulo, String director, int precio) {
		super();
		this.idPelicula = idPelicula;
		this.titulo = titulo;
		this.director = director;
		this.precio = precio;
		this.socketAlCliente = socketAlCliente;
		
		hilo.start();
	}
	
	//GETTER AND SETTERS::
	
	public String getIdPelicula() {
		return idPelicula;
	}
	
	public void setIdPelicula(String idPelicula) {
		this.idPelicula = idPelicula;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDirector() {
		return director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public int getPrecio() {
		return precio;
	}
	
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	
	@Override
	public void run() {
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
				}
				case 4: {
					
					//METODO 4
					
				}
				case 5: {
					
					//METODO SALIR
					salida.println("Saliendoi del programa");
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
