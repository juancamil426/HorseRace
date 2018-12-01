package Audios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CancionServer extends Thread{


	//	public static void main(String[] args) throws IOException {}

	public CancionServer() {
		
	}

	public void run() {
		FileInputStream soundFile = null;
		
		try 
		{
			soundFile = new FileInputStream("./musica/MusicaCarrera.wav");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("server: " + soundFile);

		try (ServerSocket serverSocker = new ServerSocket(6666);) 
		{
			if (serverSocker.isBound()) {
				Socket client = serverSocker.accept();
				OutputStream out = client.getOutputStream();

				byte buffer[] = new byte[2048];
				int count;
				while ((count = soundFile.read(buffer)) != -1)
					out.write(buffer, 0, count);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("server: shutdown");
	}
}
