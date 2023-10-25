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
 
 try (Scanner sc = new Scanner(System.in);
			Socket socketAlServidor = new Socket()){
//-----------------------------------------------------------------------------
	
	 
	 
	 //POSIBLE LUGAR DEL MENU
	 
	 
	 
 
//---------------------------------------------------------------------------
		
		
		
 // ESTABLECEMOS LA CONEXION:
		System.out.println("CLIENTE: Esperando a que el servidor acepte la conexión");
		socketAlServidor.connect(direccionServidor);			
		System.out.println("CLIENTE: Conexion establecida... a " + IP_SERVER 
				+ " por el puerto " + PUERTO);	
		
//CREAMOS EL OBJETO QUE NOS PERMITE MANDAR INFORMACION AL SERVIDOR:
		PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
		
		
//CREAMOS EL OBJETO QUE NOS VA A PRMITIR LLER LA SALIDA DEL SERVIDOR:
		InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
				
//ESTA CLASE  NOS VA A AYUDAR A LEER DATOS DEL SERVIDOR LINEA A LINEA EN VEZ DE CARCATER A CARACTER
//COMO LA CLASE  InputStreamReader:
		BufferedReader bf = new BufferedReader(entrada);
		
		System.out.println("CLIENTE: ESPERANDO AL RESULTADO DEL SERVIDOR...");
		
//AQUI EL HILO PRINCIPAL VA A QUEDAR PARADO HASTA QUE EL SERVIDOR RESPONDA :
		String resultado = bf.readLine();
		
 } catch (UnknownHostException e) {
		System.err.println("CLIENTE:NO ENCUENTRO EL SERVIDO E LA DIRECCIÓN" + IP_SERVER);
		e.printStackTrace();
	} catch (IOException e) {
		System.err.println("CLIENTE:ERROR ENTRADA/SALIDA");
		e.printStackTrace();
	} catch (Exception e) {
		System.err.println("CLIENTE:ERROR-> " + e);
		e.printStackTrace();
	}
	
	System.out.println("CLIENTE: ---FIN DEL PROGRAMA---");
}
		
		
		
		

	}


 
