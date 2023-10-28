package cliente.hilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketCliente {

<<<<<<< HEAD:SocketCliente/src/cliente/hilo/SocketCliente.java
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



 
=======
    public static final int PUERTO = 2020;
    public static final String IP_SERVER = "localhost";

    public static void main(String[] args) {
        System.out.println("APLICACIÓN CLIENTE");
        System.out.println("MENÚ DE OPCIONES:");
        System.out.println("1. Consultar película por ID");
        System.out.println("2. Consultar película por título");
        System.out.println("3. Consultar películas por director");
        System.out.println("4. Salir de la aplicación");
        System.out.println("5. Añadir película");
        System.out.println("-----------------------");

        try (Scanner userInput = new Scanner(System.in);
             Socket socketAlServidor = new Socket(IP_SERVER, PUERTO);
             PrintStream salida = new PrintStream(socketAlServidor.getOutputStream());
             InputStreamReader entrada = new InputStreamReader(socketAlServidor.getInputStream());
             BufferedReader bf = new BufferedReader(entrada)) {

            while (true) {
                System.out.print("Elija una opción: ");
                String option = userInput.nextLine();

                salida.println(option);

                if (option.equals("4")) {
                    String response = bf.readLine();
                    System.out.println(response);
                    break;
                } else if (option.equals("3")) {
                    System.out.print("Ingrese el nombre del director: ");
                    String director = userInput.nextLine();
                    salida.println(director);
                    String response = bf.readLine();
                    System.out.println(response);
                } else if (option.equals("5")) {
                    // Añadir película
                    System.out.print("Ingrese el ID de la película: ");
                    String idPelicula = userInput.nextLine();
                    System.out.print("Ingrese el título de la película: ");
                    String titulo = userInput.nextLine();
                    System.out.print("Ingrese el nombre del director: ");
                    String director = userInput.nextLine();
                    System.out.print("Ingrese el precio de la película: ");
                    int precio = userInput.nextInt();

                    userInput.nextLine(); // Limpiar el salto de línea

                    salida.println(option);
                    salida.println(idPelicula);
                    salida.println(titulo);
                    salida.println(director);
                    salida.println(precio);

                    String response = bf.readLine();
                    System.out.println(response);
                } else {
                    String response = bf.readLine();
                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            System.err.println("CLIENTE: Error de entrada/salida");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("CLIENTE: Error -> " + e);
            e.printStackTrace();
        }

        System.out.println("CLIENTE: ---FIN DEL PROGRAMA---");
    }
}
>>>>>>> 3ca4b2c54b26ccc06ecc865dad3e69f5f9883057:SocketCliente/src/cliente/SocketCliente.java
