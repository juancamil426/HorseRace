package Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Modelo.Caballo;

public class PanelCarrera extends JPanel
{
	private boolean carrreraEmpezo;
	private Caballo[] caballos;
	private String[] clientes;
	
	public PanelCarrera(Caballo[] caballitos)
	{
		carrreraEmpezo = false;
		setPreferredSize(new Dimension(1020, 600));
		setLayout(new BorderLayout());
		caballos = caballitos;
	}
	
	public void paintComponent(Graphics graficos) 
	{
			Graphics2D dibujo = (Graphics2D) graficos;
			super.paintComponent(graficos);
			
			ImageIcon iconoMeta = new ImageIcon("./imagenes/meta.png");
			Image imagenMeta = iconoMeta.getImage();
			dibujo.drawImage(imagenMeta, 910, 20, null);
			dibujo.setColor(Color.BLACK);
			
			for(int i = 0; i < caballos.length; i++)
			{
				int x = caballos[i].darPosicionx();
				int y = caballos[i].darPosiciony();					
				ImageIcon icono = new ImageIcon(caballos[i].darRuta());
				Image imagen = icono.getImage();
				dibujo.drawImage(imagen, x, y, null);
				dibujo.setColor(Color.BLACK);
				repaint();
			}
			
			if(carrreraEmpezo == true)
			{
				dibujo.setColor(Color.BLACK);
				String apostador1 = clientes[0];
				String apostador2 = clientes[1];
				String apostador3 = clientes[2];
				String apostador4 = clientes[3];
				String apostador5 = clientes[4];
				String apostador6 = clientes[5];
				
				dibujo.drawString("APOSTADORES", 25, 45);
				dibujo.drawString(apostador1, 40, 125);
				dibujo.drawString(apostador2, 40, 200);
				dibujo.drawString(apostador3, 40, 275);
				dibujo.drawString(apostador4, 40, 350);
				dibujo.drawString(apostador5, 40, 425);
				dibujo.drawString(apostador6, 40, 500);
			}
				
			
		
		
	}

	public void activarCarrera(String[] clientesNombres) 
	{
		carrreraEmpezo = true;
		clientes = clientesNombres;
		
	}
}
