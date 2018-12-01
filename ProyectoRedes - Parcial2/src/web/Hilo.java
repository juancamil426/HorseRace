package web;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Hilo extends Thread {
	
	private ServidorBaseDatos server;
	
	public Hilo(ServidorBaseDatos server) {
		
		this.server = server;
	}
	
	public void run() {
		
		while(server.isAlive()) {
			try {
				Socket socket;
				socket = server.getSocketServer().accept();
				System.out.println("CHEQUEO : Se ha establecido una conexion con el Servidor de Horse_Race");
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				String mensaje = in.readUTF();
				String[] componentes = mensaje.split(";");
				
				String seVaGuardar = "";
				
				for(int i = 1; i < componentes.length; i++ ) 
				{
					if(i < componentes.length-1)
					{
						seVaGuardar += componentes[i] + ";";
					}
					else
					{
						seVaGuardar += componentes[i];
					}
					
				}
				System.out.println("/////////////////////////////////////////");
				System.out.println("Primer COmponente:   " + componentes[0]);
				System.out.println("Segundo COmponente:   "  + seVaGuardar);
				if (componentes[0].compareToIgnoreCase("GUARDAR")==0) {
					server.guardarEnBaseDeDatos(seVaGuardar);
					out.writeUTF("GUARDADO");
				}
				else {
					server.retornarInfoBD(out);
				}
				
				socket.close();
			} catch (IOException e) {
				System.out.println("Exception in Thread HiloAtencionServidores");

			}
			
		}
		
	}

}
