package Modelo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;

import javax.net.ssl.SSLServerSocketFactory;

import Audios.CancionServer;
import Audios.clienteAudioFinal;
import Audios.servidorAudioFinal;
import Interfaz.InterfazServidor;
import Modelo.Caballo;


public class Servidor 
{
	public static String KEYSTORE_LOCATION = "C:/Program Files (x86)/Java/jre1.8.0_151/bin/keystore.jks";
	public static String KEYSTORE_PASSWORD = "123456";
	public static final String LOCAL_HOST = "localhost";
	public static final int PORT_BD = 6500;
	private static Caballo[] caballos;
	public String[] clientesNombres;
	private ArrayList<Apostador> clientesApostadores;
	private ArrayList<String> mensajesClientesApostadores;
	private static int posicionXmeta;
	private InterfazServidor server;
	private boolean apuestasCerrada;
	private boolean conexionRealizadaBD;
	
	private int caballoGanador;
	
	private Calendar calendario2;
	private int minutosInicio;
	
	servidorAudioFinal audioServer;
	public Servidor(InterfazServidor interfazServidor)
	{
		conexionRealizadaBD = false;
		clientesApostadores = new ArrayList();
		mensajesClientesApostadores = new ArrayList<String>();
		server = interfazServidor;
		caballos = new Caballo[6];
		clientesNombres = new String[6];
		caballos[0] = new Caballo(0, "./imagenes/Caballo1.gif", 50, 0);
		caballos[1] = new Caballo(1, "./imagenes/Caballo1.gif", 50, 75);
		caballos[2] = new Caballo(2, "./imagenes/Caballo1.gif", 50, 145);
		caballos[3] = new Caballo(3, "./imagenes/Caballo1.gif", 50, 215);
		caballos[4] = new Caballo(4, "./imagenes/Caballo1.gif", 50, 285);
		caballos[5] = new Caballo(5, "./imagenes/Caballo1.gif", 50, 355);
		clientesNombres[0] = "";
		clientesNombres[1] = "";
		clientesNombres[2] = "";
		clientesNombres[3] = "";
		clientesNombres[4] = "";
		clientesNombres[5] = "";
		posicionXmeta = 900;
		apuestasCerrada = false;
		caballoGanador = 0;
		calendario2 = Calendar.getInstance();
		minutosInicio = calendario2.get(Calendar.MINUTE);
	
		
	}
	
	
	public void establecerConexion()
	{
		//ServerSocket server = null;
		ServerSocket server = null;
		System.setProperty("javax.net.ssl.keyStore", KEYSTORE_LOCATION);
		System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);

		SSLServerSocketFactory sslServer = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		try
		{	
			//server = sslServer.createServerSocket(8029);
			server = new ServerSocket(8029);
			System.out.println("Servidor listo para recibir solicitudes");
			
			servidorAudioFinal audioServer = new servidorAudioFinal();
			audioServer.start();
			
			CancionServer cancionservidor = new CancionServer();
			cancionservidor.start();
			
			
			while (true) 
			{
				Socket client = server.accept();
				System.out.println("Solicitud recibida");
				
				CharThread hilo = new CharThread(client);
				hilo.start();
			}

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				server.close();
			} 
			catch (IOException e2) 
			{
				e2.printStackTrace();
			}
		}
		
		
		
		
	}
	static synchronized void print(String lin) 
	{
		System.out.println(lin);
	}
	
	public class CharThread extends Thread
	{
		Socket client;
		BufferedReader cIn;
		PrintWriter cOut;
		String user;
		
		public CharThread(Socket client)
		{
			this.client = client;
			try {
				//Lector de la conexcion
				cIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
				//Escritor de la conexion
				cOut = new PrintWriter(client.getOutputStream(), true);
				String[]  user = cIn.readLine().split("-");
				Apostador nuevoApos= new Apostador(user[2], user[1], Integer.parseInt(user[0]) + 1);
				clientesApostadores.add(nuevoApos);
				if(apuestasCerrada == false)
				{
					agregarApuesta(user[0], user[1]);
					int identificadorCaballo = Integer.parseInt(user[0]);
					if(clientesNombres[identificadorCaballo].length() == 0)
					{
						clientesNombres[identificadorCaballo] = user[2];
					}
					else
					{
						clientesNombres[identificadorCaballo] += "," + user[2];
					}
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() 
		{
			super.run();
			boolean aviso = true;
			while(aviso)
			{
				Calendar calendario1 = Calendar.getInstance();
		
				int minutosFinal = calendario1.get(Calendar.MINUTE);
				if(minutosFinal - minutosInicio == 2)
				{
					aviso = false;
					apuestasCerrada = true;
					mostrarTotalApuestas();
					empezarCarrera();
					empezarAudio();			
				}  
				try 
				{
		
				sleep(1);
		
				} 
				catch (InterruptedException e) 
				{
				e.printStackTrace();
				}
			}

			 

			
		}

		private void empezarAudio() {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	private void empezarCarrera()
	{
		server.activarCarrera(clientesNombres);
		HiloMoverCaballos mover = new HiloMoverCaballos();
		mover.start();
	}
	
	public class HiloMoverCaballos extends Thread
	{
		@Override
		public void run() 
		{
			super.run();
			boolean termino = false;
			while(!termino && (caballos[0].pasoMeta() == false ||  caballos[1].pasoMeta() == false  || caballos[2].pasoMeta() == false || caballos[3].pasoMeta() == false  || caballos[4].pasoMeta() == false  || caballos[5].pasoMeta() == false))
			{
				if(caballos[0].darPosicionx() >= posicionXmeta)
				{
					caballos[0].cambiarPasoMeta();
					
					if(caballoGanador == 0)
					{
						caballoGanador = 1;
					}
				}
				if(caballos[1].darPosicionx() >= posicionXmeta)
				{
					caballos[1].cambiarPasoMeta();
					
					if(caballoGanador == 0)
					{
						caballoGanador = 2;
					}
				}
				if(caballos[2].darPosicionx() >= posicionXmeta)
				{
					caballos[2].cambiarPasoMeta();
					
					if(caballoGanador == 0)
					{
						caballoGanador = 3;
					}
				}
				if(caballos[3].darPosicionx() >= posicionXmeta)
				{
					caballos[3].cambiarPasoMeta();
					
					if(caballoGanador == 0)
					{
						caballoGanador = 4;
					}
				}
				if(caballos[4].darPosicionx() >= posicionXmeta)
				{
					caballos[4].cambiarPasoMeta();
					
					if(caballoGanador == 0)
					{
						caballoGanador = 5;
					}
				}
				if(caballos[5].darPosicionx() >= posicionXmeta)
				{
					caballos[5].cambiarPasoMeta();
					
					if(caballoGanador == 0)
					{
						caballoGanador = 6;
					}
				}
				caballos[0].aumentarX((int)(Math.random()*5+1));
				caballos[1].aumentarX((int)(Math.random()*5+1));
				caballos[2].aumentarX((int)(Math.random()*5+1));
				caballos[3].aumentarX((int)(Math.random()*5+1));
				caballos[4].aumentarX((int)(Math.random()*5+1));
				caballos[5].aumentarX((int)(Math.random()*5+1));
				server.actualizarCarrera();
				try 
				{
					sleep(100);
				} 
				catch (InterruptedException e) 
				{
				e.printStackTrace();
				}
			}
			caballos[0].aumentarX(200);
			caballos[1].aumentarX(200);
			caballos[2].aumentarX(200);
			caballos[3].aumentarX(200);
			caballos[4].aumentarX(200);
			caballos[5].aumentarX(200);
			server.actualizarCarrera();
			server.mostrarResultados(caballoGanador, clientesNombres[caballoGanador-1]);
			modificarDatosApostadorVictorioso();
			establecerConexConServerBD();
			System.exit( 0 );
			
		}
	
	}

	private void mostrarTotalApuestas() 
	{
		server.mostrarTodasLasApuestas(caballos[0].darApuestaTotal(), caballos[1].darApuestaTotal(),caballos[2].darApuestaTotal(),caballos[3].darApuestaTotal(),caballos[4].darApuestaTotal(),caballos[5].darApuestaTotal());
	}
	public Caballo[] darCaballos()
	{
		return caballos;
	}
	
	public int darPosicionXmeta()
	{
		return posicionXmeta;
	}
	
	private static void agregarApuesta(String numeroCaballo, String valor)
	{
		int caballo = Integer.parseInt(numeroCaballo);
		double valorApuesta =  Double.parseDouble(valor);
		
		for(int i= 0; i < caballos.length; i++)
		{
			if(caballos[i].darNumero() == caballo)
			{
				caballos[i].sumarApuesta(valorApuesta);
			}
		}
	}
	
	private void establecerConexConServerBD() {
		
		try {
			Socket socket = new Socket(LOCAL_HOST, PORT_BD);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			String mensaje = "GUARDAR;";
			System.out.println("Chequeo Method Establecer Conex con Server BD");
			for (int i = 0; i < mensajesClientesApostadores.size(); i++) {
				mensaje += mensajesClientesApostadores.get(i) + "\n";
				
			}
			System.out.println("Esto mando: " + mensaje);
			out.writeUTF(mensaje);
			String mensajeObtenido = in.readUTF();
			System.out.println("Mensaje Obtenido por el Servidor BD al GUARDAR : " + mensajeObtenido);
			socket.close();
			
		} catch (IOException e) {
			System.out.println("Exception in ConexServerBD");
		}	
	}



	private void modificarDatosApostadorVictorioso() 
	{
		for (int i = 0; i < clientesApostadores.size(); i++) 
		{
			Apostador apos = clientesApostadores.get(i);
			if (apos.darcaballoApostado() == caballoGanador) 
			{
				apos.cambiarGano(true);
			}
			
			String mensaje = apos.darcedula() + ";" + apos.darcaballoApostado() + ";" + apos.darvalorApostado() + ";" + apos.darGano();
			mensajesClientesApostadores.add(mensaje);
			System.out.println("Recien modificado: " + mensaje);
		}
		
	}
	
	public String pedirDatosAlServerBD(String cedula) {
		String datosObtenidos = "";
		Socket socket = null;
		if(conexionRealizadaBD == false)
		{
			conexionRealizadaBD = true;
			try {
				socket = new Socket(LOCAL_HOST, PORT_BD);
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				String mensaje = "PEDIR_DATOS;" + cedula;
				System.out.println("Chequeo Method pedir Datos Al Server BD");	
				out.writeUTF(mensaje);
				String mensajeObtenido = in.readUTF();
				datosObtenidos = mensajeObtenido;
				System.out.println("Mensaje Obtenido por el Servidor BD al PEDIR DATOS : " + mensajeObtenido);
				socket.close();	
			} catch (IOException e) {
				System.out.println("Exception in ConexServerBD");
			}
		}
		else
		{
			try {
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				String mensaje = "PEDIR_DATOS;" + cedula;
				System.out.println("Chequeo Method pedir Datos Al Server BD");	
				out.writeUTF(mensaje);
				String mensajeObtenido = in.readUTF();
				datosObtenidos = mensajeObtenido;
				System.out.println("Mensaje Obtenido por el Servidor BD al PEDIR DATOS : " + mensajeObtenido);
				socket.close();	
			} catch (IOException e) {
				System.out.println("Exception in ConexServerBD");
			}
		}
			
		return datosObtenidos;
	}

	
}