package Modelo;

public class Caballo 
{
	private double totalApostado;
	private int posicionx;
	private int posiciony;
	private int numero;
	private String rutaImagen;
	private boolean pasoMeta;
	
	public Caballo(int num, String ruta, int posx, int posy)
	{
		totalApostado = 0;
		
		posicionx = posx;
		posiciony = posy;
		numero = num;
		rutaImagen = ruta;
		pasoMeta = false;
	}
	
	public void sumarApuesta(double valor)
	{
		totalApostado += valor;
	}
	
	public double darApuestaTotal()
	{
		return totalApostado;
	}
	
	public int darPosicionx()
	{
		return posicionx;
	}
	
	public int darPosiciony()
	{
		return posiciony;
	}
	
	public int darNumero()
	{
		return numero;
	}
	
	public String darRuta()
	{
		return rutaImagen;
	}
	
	public void aumentarX(int aumento)
	{
		posicionx += aumento;
	}
	
	public boolean pasoMeta()
	{
		return pasoMeta;
	}

	public void cambiarPasoMeta()
	{
		pasoMeta = true;
		
	}
}
