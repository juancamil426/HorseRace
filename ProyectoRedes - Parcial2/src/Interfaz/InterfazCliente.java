package Interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Audios.CancionCliente;
import Audios.clienteAudioFinal;
import Modelo.Cliente;




public class InterfazCliente extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String AVANZAR = "avanzar";
	public static String APOSTAR = "apostar";
	
	private int caballoSeleccionado;
	private double valorApuestaClienteTotal;
	private String nombreUsuario;
	
	private static Cliente cliente;

	private JPanel izquierda;
	
	private JLabel nada;
	private JLabel labelSeleccione;
	private JComboBox<String> comboBoxCaballos;
	private JLabel labelNombre;
	private JTextField textNombreUsuario;
	private JButton botonAvanzar;
	
	private JLabel labelValorApuesta;
	private JTextField valorApuesta;
	private JButton botonApuesta;
	
	private JLabel LabelEtiquetaNumeroCaballo;
	private JLabel numeCaba;
	private JLabel LabelEtiquetaValorApostado;
	private JLabel totalApostado;
	
	public InterfazCliente()
	{
		setTitle("Carrera de caballos");
		setLayout(new BorderLayout());
		nada = new JLabel();
		

		//Panel de la izquierda
		
		izquierda = new JPanel();
		izquierda.setLayout(new GridLayout(1,3));
		
		
		// Panel Izquierda Arriba
		JPanel izquiArriba = new JPanel();
		izquiArriba.setLayout(new GridLayout(5,1));
		izquiArriba.setBorder(new TitledBorder("Centro de caballos"));
		
		labelSeleccione = new JLabel("  Seleccione un caballo:  ");
		comboBoxCaballos = new JComboBox<String>();
		comboBoxCaballos.addItem("Caballo 1");
		comboBoxCaballos.addItem("Caballo 2");
		comboBoxCaballos.addItem("Caballo 3");
		comboBoxCaballos.addItem("Caballo 4");
		comboBoxCaballos.addItem("Caballo 5");
		comboBoxCaballos.addItem("Caballo 6");
		labelNombre = new JLabel("  Ingrese un nombre de usuario:  ");
		textNombreUsuario = new JTextField();
		
		
		JPanel aux1 = new JPanel();
		aux1.setLayout(new GridLayout(1,3));
		
		botonAvanzar = new JButton(">>");
		botonAvanzar.addActionListener(this);
		botonAvanzar.setActionCommand(AVANZAR);
		
		
		aux1.add(nada);
		aux1.add(nada);
		aux1.add(botonAvanzar);
		
		izquiArriba.add(labelSeleccione);
		izquiArriba.add(comboBoxCaballos);
		izquiArriba.add(labelNombre);
		izquiArriba.add(textNombreUsuario);
		izquiArriba.add(aux1);
		
		
		izquierda.add(izquiArriba);
		
		add(BorderLayout.NORTH, izquierda);
		
		
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals(AVANZAR))
		{
			if(comboBoxCaballos.getSelectedItem().equals("Caballo 1"))
			{
				caballoSeleccionado = 0;
			}
			else if(comboBoxCaballos.getSelectedItem().equals("Caballo 2"))
			{
				caballoSeleccionado = 1;
			}
			else if(comboBoxCaballos.getSelectedItem().equals("Caballo 3"))
			{
				caballoSeleccionado = 2;
			}
			else if(comboBoxCaballos.getSelectedItem().equals("Caballo 4"))
			{
				caballoSeleccionado = 3;
			}
			else if(comboBoxCaballos.getSelectedItem().equals("Caballo 5"))
			{
				caballoSeleccionado = 4;
			}
			else if(comboBoxCaballos.getSelectedItem().equals("Caballo 6"))
			{
				caballoSeleccionado = 5;
			}
			
			nombreUsuario = textNombreUsuario.getText();
			
			cliente = new Cliente(nombreUsuario);
			clienteAudioFinal c = new clienteAudioFinal();
			c.start();
			
			CancionCliente cancioncliente = new CancionCliente();
			cancioncliente.start();
			
			
			JPanel izquierdaCentro = new JPanel();
			izquierdaCentro.setLayout(new BorderLayout());
			izquierdaCentro.setBorder(new TitledBorder("Centro de apuestas"));
			
			labelValorApuesta = new JLabel("Ingrese el valor de la apuesta");
			
			
			JPanel aux121 = new JPanel();
			aux121.setLayout(new GridLayout(1,2));
			
			valorApuesta = new JTextField();
			botonApuesta = new JButton("Apostar");
			botonApuesta.addActionListener(this);
			botonApuesta.setActionCommand(APOSTAR);
			
			aux121.add(valorApuesta);
			aux121.add(botonApuesta);
			
			izquierdaCentro.add(BorderLayout.NORTH, labelValorApuesta);
			izquierdaCentro.add(BorderLayout.CENTER, aux121);
			
			
			JPanel auti = new JPanel();
			auti.setBorder(new TitledBorder("Datos carrera"));
			auti.setLayout(new GridLayout(2,2));
			
			LabelEtiquetaNumeroCaballo = new JLabel("Numero de caballo: ");
			numeCaba = new JLabel("" + (caballoSeleccionado + 1));
			
			LabelEtiquetaValorApostado = new JLabel("Total apostado: ");
			totalApostado = new JLabel(0 + "");
			
			auti.add(LabelEtiquetaNumeroCaballo);
			auti.add(numeCaba);
			auti.add(LabelEtiquetaValorApostado);
			auti.add(totalApostado);
			
			//Agregar al panel izquierda los dos de arriba
			izquierda.add(izquierdaCentro);
			izquierda.add(auti);
			//Funcionalidades de los botones
			botonAvanzar.setEnabled(false);	
			textNombreUsuario.setEditable(false);
			
			pack();
		}
		
		else if(e.getActionCommand().equals(APOSTAR))
		{
			int valorApostado = Integer.parseInt(valorApuesta.getText());
			
			valorApuestaClienteTotal += valorApostado;
			
			totalApostado.setText(valorApuestaClienteTotal + "");
			
			cliente.realizarApuesta(valorApostado, caballoSeleccionado);
			
			botonApuesta.setEnabled(false);
			valorApuesta.setEditable(false);
			
		}
		
	}
	
	public static void main(String[] args)
	{
		InterfazCliente interfaz = new InterfazCliente();
		interfaz.setVisible(true);
	}

}
