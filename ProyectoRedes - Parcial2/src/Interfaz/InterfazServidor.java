package Interfaz;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Modelo.Servidor;
import Modelo.Caballo;

public class InterfazServidor extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Servidor servidorRele;
	private Caballo[] caballos;
	private PanelCarrera panelCarrera;
	private InterfazServidor me;
	public InterfazServidor() 
	{
		setLayout(new BorderLayout());
		setTitle("Carrera de caballos");
		
		me = this;
		servidorRele = new Servidor(me);
		
		
		caballos = servidorRele.darCaballos();
		panelCarrera = new PanelCarrera(caballos);
		add(BorderLayout.CENTER, panelCarrera);
		
		pack();
		
		HiloAuxilio hilito = new HiloAuxilio();
		hilito.start();
		
	}
	
	public class HiloAuxilio extends Thread
	{
		
		@Override
		public void run() 
		{
			
			servidorRele.establecerConexion();
		}
	
	}
	public static void main(String[] args)
	{
		InterfazServidor interfaz = new InterfazServidor();
		interfaz.setVisible(true);
	}
	
	public void mostrarTodasLasApuestas(double caballo1, double caballo2, double caballo3, double caballo4, double caballo5, double caballo6) 
	{
		JPanel datos = new JPanel();
		datos.setLayout(new GridLayout(1,6));
		
		//Caballo 1
		JPanel panelCaballo1 = new JPanel();
		panelCaballo1.setLayout(new GridLayout(2,1));
		JLabel nombreCaballo1 = new JLabel("Caballo # 1");
		JLabel apuestaCaballo1 = new JLabel(caballo1 + "");
		panelCaballo1.add(nombreCaballo1);
		panelCaballo1.add(apuestaCaballo1);
		
		//Caballo 2
		JPanel panelCaballo2 = new JPanel();
		panelCaballo2.setLayout(new GridLayout(2,1));
		JLabel nombreCaballo2 = new JLabel("Caballo # 2");
		JLabel apuestaCaballo2 = new JLabel(caballo2 + "");
		panelCaballo2.add(nombreCaballo2);
		panelCaballo2.add(apuestaCaballo2);
		
		//Caballo 3
		JPanel panelCaballo3 = new JPanel();
		panelCaballo3.setLayout(new GridLayout(2,1));
		JLabel nombreCaballo3 = new JLabel("Caballo # 3");
		JLabel apuestaCaballo3 = new JLabel(caballo3 + "");
		panelCaballo3.add(nombreCaballo3);
		panelCaballo3.add(apuestaCaballo3);
		
		//Caballo 4
		JPanel panelCaballo4 = new JPanel();
		panelCaballo4.setLayout(new GridLayout(2,1));
		JLabel nombreCaballo4 = new JLabel("Caballo # 4");
		JLabel apuestaCaballo4 = new JLabel(caballo4 + "");
		panelCaballo4.add(nombreCaballo4);
		panelCaballo4.add(apuestaCaballo4);
		
		//Caballo 5
		JPanel panelCaballo5 = new JPanel();
		panelCaballo5.setLayout(new GridLayout(2,1));
		JLabel nombreCaballo5 = new JLabel("Caballo # 5");
		JLabel apuestaCaballo5 = new JLabel(caballo5 + "");
		panelCaballo5.add(nombreCaballo5);
		panelCaballo5.add(apuestaCaballo5);
		
		//Caballo 6
		JPanel panelCaballo6 = new JPanel();
		panelCaballo6.setLayout(new GridLayout(2,1));
		JLabel nombreCaballo6 = new JLabel("Caballo # 6");
		JLabel apuestaCaballo6 = new JLabel(caballo6 + "");
		panelCaballo6.add(nombreCaballo6);
		panelCaballo6.add(apuestaCaballo6);
		
		datos.add(panelCaballo1);
		datos.add(panelCaballo2);
		datos.add(panelCaballo3);
		datos.add(panelCaballo4);
		datos.add(panelCaballo5);
		datos.add(panelCaballo6);
		
		add(BorderLayout.NORTH, datos);
		pack();
	}

	public void activarCarrera(String[] clientesNombres) 
	{
		panelCarrera.activarCarrera(clientesNombres);
		
	}

	public void actualizarCarrera() 
	{
		panelCarrera.repaint();
		
	}

	public void mostrarResultados(int caballoGanador, String clientesNombres) 
	{
		String mensaje = "El caballo ganador es: " + caballoGanador + "\n" +  "Cliente ganador: " + clientesNombres;
		JOptionPane.showMessageDialog(null, mensaje);
		
	}
	
	
	
}

