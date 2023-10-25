package cliente.hilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class SocketCliente {
	
	             //IP Y PUERTO 
	public static final int PUERTO = 2017;
	public static final String IP_SERVER = "localhost";

	public static void main(String[] args) {
		
		System.out.println("            APLICACIÓN CLIENTE              ");
		System.out.println("SE ESTA PROCESANDO EL MENU CON LAS OPCIONES ");
		System.out.println("--------------------------------------------");
		
    //EN ESTE PASO VAMOS A ENCAPSULAR EL IP Y EL PUERTO AL QUE NOS VAMOS A CONECTAR 
  
		InetSocketAddress direccionServidor = new InetSocketAddress(IP_SERVER, PUERTO);
	 
		try (Scanner sc = new Scanner(System.in)){
			
			System.out.println("CLIENTE: Esperando a que el servidor acepte la conexi�n");
			Socket socketAlServidor = new Socket();
			socketAlServidor.connect(direccionServidor);
			System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER + 
					" por el puerto " + PUERTO);
			
			InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
			BufferedReader entradaBuffer = new BufferedReader(entrada);
			
			PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
			
			String texto = "";
			boolean continuar = true;
			do {
				System.out.println("Escriba el número de la opción deseada");
				//AQUI VAMOS A METER EL MENU PARA QUE LE SALGA AL CLIENTE
				System.out.println("               ******MENU******             ");
				System.out.println("               ----------------             ");
				System.out.println("  1.        CONSULTAR PELICULA POR ID       ");
				System.out.println("  2.      CONSULTAR PELICULA POR TITULO     ");
				System.out.println("  3.     CONSULTAR PELICULA POR DIRECTOR    ");
				System.out.println("  4.            AÑADIR PELICULA             ");
				System.out.println("  5                 SALIR                   ");
				
				texto = sc.nextLine();//frase que vamos a mandar para contar	
				
				
				
				salida.println(texto);
				System.out.println("CLIENTE: Esperando respuesta ...... ");				
				String respuesta = entradaBuffer.readLine();
								
				if("salir".equalsIgnoreCase(respuesta)) {
					continuar = false;
				}else {
					System.out.println("CLIENTE: Servidor responde : " + respuesta);
				}				
			}while(continuar);
			//Cerramos la conexion
			socketAlServidor.close();
			
		} catch (UnknownHostException e) {
			System.err.println("CLIENTE: No encuentro el servidor en la direcci�n" + IP_SERVER);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("CLIENTE: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("CLIENTE: Error -> " + e);
			e.printStackTrace();
		}
		
		System.out.println("CLIENTE: Fin del programa");
	}
 
}	 



 
