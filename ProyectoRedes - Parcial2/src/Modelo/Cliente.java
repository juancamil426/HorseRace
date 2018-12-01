package Modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

import Audios.clienteAudioFinal;
import Audios.servidorAudioFinal;


public class Cliente 
{
	public static final String TRUSTTORE_LOCATION = "C:/Program Files (x86)/Java/jre1.8.0_151/bin/keystore.jks";
	private int caballoSeleccionado;
	private String nombreUsuario;
	private int valorApuesta;
	
	BufferedReader cIn;
	PrintWriter cOut;
	Socket client;
	
	clienteAudioFinal audioCliente;
	public Cliente(String nombreUsu)
	{
		nombreUsuario = nombreUsu;
		System.setProperty("javax.net.ssl.trustStore", TRUSTTORE_LOCATION);
		SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		try 
		{
			//Conexion con el servidor
			//client = sf.createSocket("localHost", 8029);
			client = new Socket("localHost", 8029);		
			System.out.println("Conectado al servidor");
	
			//Lectura de datos desde la conexion con el servidor 
			cIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
					
			//Escritura de datos por la conexion con el servidor
			cOut = new PrintWriter(client.getOutputStream(), true);
			
		} 
		catch (IOException e) 
		{
				e.printStackTrace();
		}
		
		
		
	}
	
	public void realizarApuesta(int valorApostado, int caballoSel) 
	{
		caballoSeleccionado = caballoSel;
		valorApuesta = valorApostado;
		
		try 
		{
			String user = caballoSeleccionado + "-" + valorApuesta + "-" + nombreUsuario;
			cOut.println(user);
			cIn.close();
			cOut.close();
			client.close();
		}
		catch (IOException e) 
		{
				e.printStackTrace();
		}
		
		
		
	}

}
