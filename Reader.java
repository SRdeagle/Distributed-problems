package main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Reader implements Runnable {
	boolean isRunning = false;
	@Override
	public void run () {
		isRunning = true;
		try (Socket serverSocket = new Socket("localhost",4002);) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
					serverSocket.getInputStream())); 
			PrintWriter out = new PrintWriter(serverSocket.getOutputStream(),true);
			
			out.println("read");
			
			//String response = in.readLine();
			
//			if (response == "okToRead") {
//				String line;
//				while ((line = in.readLine())!="*E*O*F*");
//				{
//					System.out.println(line);
//				}
//			} else {
//				System.out.println("error when trying to read");
//			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main (String args[]) {
		System.out.println("readeeeer initialized");
		Reader r = new Reader();
		Thread t = new Thread (r);
		t.start();
		try {
			t.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
