package web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.util.Scanner;


public class ServidorBaseDatos {
	
	private ServerSocket socketServer;
	private boolean isAlive;
	public static final int PORT = 6500;
	
	public ServidorBaseDatos() {
	
		try {
			System.out.println("STATE SERVER BD :: ON ::");
			inicializarSocketServidor();
			isAlive = true;
			Hilo hiloAtencion = new Hilo(this);
			hiloAtencion.start();
		} catch (IOException e) {
			System.out.println("Exception in ServerBD Builder");
		}
		
	}
	
	
	
	public void guardarEnBaseDeDatos(String informacion)  {
		
		
		try {
			System.out.println("////////////////////////////////////////////////////////");
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				File texto = new File("datos/datosClientes.txt");
				if(!texto.exists()) {
					if(texto.createNewFile()) {
						System.out.println("El fichero se ha creado correctamente"); 
					}
					else {
						System.out.println("No ha podido ser creado el fichero"); 					
				}
			}
				else {
					BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(texto, true), "UTF8")); 
					out.write(informacion); 
					out.write("\n"); 
					out.close(); 
				}
				
				
		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	public void retornarInfoBD(DataOutputStream out) throws IOException {
		String mensajeDevolver = "";
		try {
			File texto = new File("datos/datosClientes.txt");
			Scanner s = new Scanner(texto);
			while (s.hasNextLine()) {
				mensajeDevolver += s.nextLine()+ "\n";
			}
			System.out.println("///////Mensaje devolver de a la web: " + mensajeDevolver);
			out.writeUTF(mensajeDevolver);
			
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error en guardado de TXT BD");
		}
		
		
	}
	
	public void inicializarSocketServidor() throws IOException {
		socketServer = new ServerSocket(PORT);
	}

	public ServerSocket getSocketServer() {
		return socketServer;
	}

	public void setSocketServer(ServerSocket socketServer) {
		this.socketServer = socketServer;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public static void main(String[] args) {
		ServidorBaseDatos serverBD = new ServidorBaseDatos();
	}
	
	
}