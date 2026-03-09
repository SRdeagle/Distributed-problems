package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Server implements Runnable {
	
	private File file = null;
	private int port;
	private String host;
	private Semaphore sem;
	private Semaphore mutex;
	private ExecutorService pool;
	private Integer wc=0;
	private Integer rc=0;

	
	public Server (int port, String host, String filePath) {
		this.port = port;
		this.host = host;
		this.file = new File(filePath);
		this.sem = new Semaphore(1);
		this.mutex = new Semaphore(1);
		this.pool = Executors.newFixedThreadPool(10);
	}
	public Server () {
		this(4002,"localhost","data.txt");
	}
	
	@Override
	public void run () {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (!Thread.interrupted()) {
			Socket client = serverSocket.accept();
			System.out.println("accepted a request");
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String request = in.readLine();
			mutex.acquire();
			if ("write".equals(request)) {
				if (wc>0 || rc>0) {
					mutex.release();
					sem.acquire();
					mutex.acquire();
					wc++;
					mutex.release();
				} else {
					wc++;
					mutex.release();
				}
				pool.execute(new WorkingThreadRW(true, sem, mutex, client, file,rc,wc));
				
				
			} else if ("read".equals(request)) {
				if (wc>0) {
					mutex.release();
					sem.acquire();
					mutex.acquire();
					rc++;
					mutex.release();
				} else {
					rc++;
					mutex.release();
				}
				pool.execute(new WorkingThreadRW(true, sem, mutex, client, file,rc,wc));
				
			} else {
				mutex.release();
				client.close();
				
			}
		}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void main (String args[]) {
		Server ser = new Server();
		Thread t = new Thread(ser);
		t.start();
	}

}
