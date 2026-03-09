package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Writer implements Runnable {
	boolean isRunning = false;
	@Override
	public void run () {
		isRunning = true;
		try (Socket serverSocket = new Socket("localhost",4002);) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
					serverSocket.getInputStream())); 
			PrintWriter out = new PrintWriter(serverSocket.getOutputStream());
			
			out.println("write");
			
			String response = in.readLine();
			
			if (response == "okToWrite") {
				out.println("okToRead");
				String line = "";
				for (int i =0;i<10;i++) {
					line = String.valueOf(i);
					line += "adadadad";
					out.println(line);
				}
				out.println("*E*O*F*");
			} else {
				System.out.println("error when trying to write");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main (String args[]) {
		System.out.println(System.getProperty("user.dir"));
	}
}
