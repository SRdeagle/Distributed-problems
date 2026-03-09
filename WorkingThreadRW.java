package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class WorkingThreadRW implements Runnable {
	private boolean readNotWrite;
	private Semaphore sem;
	private Semaphore mutex;
	private Socket client;
	private File file;
	private Integer rc;
	private Integer wc;
	public WorkingThreadRW (boolean readNotWrite, Semaphore sem, Semaphore mutex, Socket client, File file, Integer rc, Integer wc) {
		this.client = client;
		this.file = file;
		this.mutex = mutex;
		this.sem = sem;
		this.readNotWrite = readNotWrite;
		this.rc = rc;
		this.wc=wc;
	}
	
	
	
	@Override
	public void run() {
		try {
			if (readNotWrite) {
			System.out.println("read request on thread");
			mutex.acquire();
			rc--;
			mutex.release();
			sem.release();
			
		}
			
		else {
			System.out.println("write request on thread");
			mutex.acquire();
			wc--;
			mutex.release();
		}
		} catch (Exception e) {
		}
		
		
		
	}
	
	

}
