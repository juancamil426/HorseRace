package Audios;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class clienteAudioFinal extends Thread
{
		AudioInputStream audioInputStream;
		SourceDataLine sourceDataLine;
		
		MulticastSocket socket;

		public clienteAudioFinal() {
			// TODO Auto-generated constructor stub
			
		}
		public void run()
		{
			initiateAudio();
		}
		
		
		private AudioFormat getAudioFormat() {
			float sampleRate = 16000F;
			int sampleSizeInBits = 16;
			int channels = 1;
			boolean signed = true;
			boolean bigEndian = false;
			return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		}

		private void playAudio() {
			byte[] buffer = new byte[10000];
			try {
				int count;
				while ((count = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
					if (count > 0) {
						sourceDataLine.write(buffer, 0, count);
					}
				}
			} catch (Exception e) {
				// Handle exceptions
			}
		}

		private void initiateAudio() {
			try {
				socket = new MulticastSocket(9786);
				InetAddress inetAddress = InetAddress.getByName("229.5.6.7");
				socket.joinGroup(inetAddress);
				byte[] audioBuffer = new byte[10000];
				// ...
				while (true) 
				{
					System.out.println("aqui va re good");
					DatagramPacket packet = new DatagramPacket(audioBuffer, audioBuffer.length);
					System.out.println("Aqui va goog x 0");
					socket.receive(packet);
					System.out.println("Aqui va goog x 1");
					try {
						System.out.println("Aqui va goog x 2");
						byte audioData[] = packet.getData();
						InputStream byteInputStream = new ByteArrayInputStream(audioData);
						AudioFormat audioFormat = getAudioFormat();
						audioInputStream = new AudioInputStream(byteInputStream, audioFormat,
								audioData.length / audioFormat.getFrameSize());
						DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

						sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
						sourceDataLine.open(audioFormat);
						sourceDataLine.start();
						playAudio();
						System.out.println("Aqui va goog x 3");
					} catch (Exception e) {
						System.out.println("erro");
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


