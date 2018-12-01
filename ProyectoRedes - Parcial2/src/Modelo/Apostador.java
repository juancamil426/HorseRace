package Modelo;

public class Apostador 
{
	String cedula;
	String valorApostado;
	int caballoApostado;
	boolean gano;
	
	
	public Apostador(String ced, String valr, int cab)
	{
		cedula = ced;
		valorApostado = valr;
		caballoApostado = cab;
		gano = false;
	}
	
	public String darcedula()
	{
		return cedula;
	}
	public String darvalorApostado()
	{
		return valorApostado;
	}
	public int darcaballoApostado()
	{
		return caballoApostado;
	}
	
	public boolean darGano()
	{
		return gano;
	}
	public void cambiarGano(boolean nBol)
	{
		gano = true;
	}
	
	
}
