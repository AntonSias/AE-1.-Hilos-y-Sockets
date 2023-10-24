package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServidor{

	public static final int PUERTO = 2017;
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("      APLICACIÓN DE SERVIDOR      ");
		System.out.println("----------------------------------");
		
//EL CANAL DE ENTRADA DEL SERVIDOR POR CUAL EL CLIENTE NOS VA A MANDAR INFORMACION:
		InputStreamReader entrada = null;
		
//EL CANAL DE SALIDA DEL SERVIDOR POR CUAL VAMOS A ENVIAR INFORMACION AL CLIENTE
		PrintStream salida = null;
		
//SOCKET - ES LA CLASE QUE NOS VA A PERMITIR COMUNICARNOS CON EL CLIENTE
		Socket socketAlCliente = null;
//
		InetSocketAddress direccion = new InetSocketAddress(PUERTO);
		
//
		try (ServerSocket serverSocket = new ServerSocket()){
		
//AVISAMOS AL SERER SOCKET QUE ESCUCHE PETICIONES DESDE EL PUERTO STABLECIDO:
			serverSocket.bind(direccion);
//VAMOS A LLEVAR LA CUNETA DE LAS PETCICONES QUE SE VAN A EJECUTAR:
			int peticion = 0;
			
//ESTAMOS CONTINUAMENTE ESCUCHANDO EL COMPORTAMIENTO DE UN SERVIDOR , UN PROGRAMA QUE NO PARA NUNCA:
			while(true){
				System.out.println("SERVIDOR:ESPERANDO PETICIONES POR EL PUERTO " + PUERTO);
				
//EL PROGRAMA SE PARA HASTA QUE ENTRE LA PETICION DE UN CLIENTE ,
//SERA EN ESTE MOMENTO CUENDO SE CREA UN OBEJTO SOCKET
				socketAlCliente = serverSocket.accept();
				System.out.println("SERVIDOR:PETICION NUMERO " + ++peticion + " RECIBIDA");
				entrada = new InputStreamReader(socketAlCliente.getInputStream());
				BufferedReader bf = new BufferedReader(entrada);

//EL SERVIDOR SE QUEDARA AQUI PARADO HASTA QUE EL CLIENTE NOS MADE INFORMACION
				String stringRecibido = bf.readLine();
				
				System.out.println("SERVIDOR: ME HA LLEGADO DEL CLIENTE : " + stringRecibido);
				
//SE SUPONE UNA SIMULACION DE ESPERA , HAY SITUACIONES CUANDO EL SERVIDOR TARDA EN RESPONDER:
				Thread.sleep(15000);
				
//MANDMAOS EL RESULTADO AL CLIENTE:
				salida = new PrintStream(socketAlCliente.getOutputStream());
				String resultado = "";
				salida.println(resultado);
				
				
//CERAMOS LAS CONEXIONES :
				socketAlCliente.close();
			} 
		} catch (IOException e) {
			System.err.println("SERVIDOR: Error de entrada/salida");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("SERVIDOR: Error -> " + e);
			e.printStackTrace();
		}
				
				
			}
		
	
	

	}

