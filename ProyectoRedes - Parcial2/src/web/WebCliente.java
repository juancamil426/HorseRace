package web;

import java.net.*;
import java.util.StringTokenizer;

import Modelo.Servidor;

import java.io.*;

public class WebCliente implements Runnable
{
	private final Socket socket;
	private Servidor server;
	public WebCliente(Socket socket)
	{
		this.socket =  socket;
		server = new Servidor(null);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("\nClientHandler Started for " + this.socket);
		while(true) 
		{
			handleRequest(this.socket);
		}		
		//System.out.println("ClientHandler Terminated for "+  this.socket + "\n");
	}
	
	public void handleRequest(Socket socket)
	{
		try {
			
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String headerLine = in.readLine();
			if(headerLine!=null)
			{
				
			
				System.out.println(headerLine);
				// A tokenizer is a process that splits text into a series of tokens
				StringTokenizer tokenizer =  new StringTokenizer(headerLine);
				//The nextToken method will return the next available token
				String httpMethod = tokenizer.nextToken();
				// The next code sequence handles the GET method. A message is displayed on the
				// server side to indicate that a GET method is being processed
				if(httpMethod.equals("GET"))
				{
					System.out.println("Get method processed");
					String httpQueryString = tokenizer.nextToken();
					System.out.println(httpQueryString);
					if(httpQueryString.equals("/"))
					{
						StringBuilder responseBuffer =  new StringBuilder();
						String str="";
						BufferedReader buf = new BufferedReader(new FileReader("html/javascr.html"));
						
						while ((str = buf.readLine()) != null) {
							responseBuffer.append(str);
					    }
						sendResponse(socket, 200, responseBuffer.toString());		
					    buf.close();
					}
					if(httpQueryString.contains("/?ced="))
					{
						System.out.println("Get method processed");
						String[] response =  httpQueryString.split("=");
						String mensaje = server.pedirDatosAlServerBD(response[1]);
						String[] total = mensaje.split("\n");
						StringBuilder responseBuffer =  new StringBuilder();
						responseBuffer
						.append("<html>")
						.append("<head>")
						.append("<style>")
						.append("</style>")
						.append("<title>DATOS</title>")
						.append("</head>")
						.append("<body>")
						.append("<h1>Listado de Carreras Realizadas Por " + response[1].trim() + "</h1>")
						.append("<table>")
						.append("<tr>")
						.append("<td><strong>CEDULA APOSTADOR</strong></td>")
						.append("<td><strong>NUMERO DE CABALLO</strong></td>")
						.append("<td><strong>VALOR APOSTADO</strong></td>")
						.append("<td><strong>RESULTADO</strong></td>");
						agregarlista(total,responseBuffer, response[1].trim());
						responseBuffer.append("<body>")
						.append("<table>")
						.append("<body>")
						.append("</html>");
						
						sendResponse(socket, 200, responseBuffer.toString());		
					    




					    
					}
										    
				}
				
				else
				{
					System.out.println("The HTTP method is not recognized");
					sendResponse(socket, 405, "Method Not Allowed");
				}
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void agregarlista(String[] datos , StringBuilder responseBuffer, String cedula) 
	{
		for (int i = 0; i < datos.length; i++) 
		{
			if(datos[i].split(";")[0].equalsIgnoreCase(cedula))
			{
				System.out.println("CHEQUEO LISTA IMPRIMIR:" + datos[i]);
				responseBuffer.append("<tr>");
				responseBuffer.append("<td>"+datos[i].split(";")[0]+"</td>");
				responseBuffer.append("<td>"+datos[i].split(";")[1]+"</td>");
				responseBuffer.append("<td>"+datos[i].split(";")[2]+"</td>");
				if(datos[i].split(";")[3].equalsIgnoreCase("TRUE"))
				{
					responseBuffer.append("<td>"+ " GANADOR "+"</td>");
				}
				else
				{
					responseBuffer.append("<td>"+ " PERDEDOR "+"</td>");
				}
				responseBuffer.append("<tr>");
			}
			
		}
	}
	
		
	

	public void sendResponse(Socket socket, int statusCode, String responseString)
	{
		String statusLine;
		String serverHeader = "Server: WebServer\r\n";
		String contentTypeHeader = "Content-Type: text/html\r\n";
		
		try {
			DataOutputStream out =  new DataOutputStream(socket.getOutputStream());
			if (statusCode == 200) 
			{
				statusLine = "HTTP/1.0 200 OK" + "\r\n";
				String contentLengthHeader = "Content-Length: "
				+ responseString.length() + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes(serverHeader);
				out.writeBytes(contentTypeHeader);
				out.writeBytes(contentLengthHeader);
				out.writeBytes("\r\n");
				out.writeBytes(responseString);
				} 
			else if (statusCode == 405) 
			{
				statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			} 
			else 
			{
				statusLine = "HTTP/1.0 404 Not Found" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			}
			//out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
