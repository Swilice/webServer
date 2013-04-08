package com.thomasm.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.thomasm.logger.Logger;

public class HttpServer implements Runnable {
	private final int timeout = 1000;
	
	private int port;
	private RequestRouter router;
	
	private boolean isRunning;
	private ServerSocket serverConnection;
	private final ExecutorService pool;
	
	public HttpServer(int port, int nbMaxThread, RequestRouter router) {
		this.port = port;
		this.router = router;
		
		pool = Executors.newFixedThreadPool(nbMaxThread);
		isRunning = false;
	}
	
	public void start() {
		if(!isRunning) {
			try {
				serverConnection = new ServerSocket(port);
				serverConnection.setSoTimeout(timeout);
			} catch (IOException ioe) {
				Logger.getInstance().printLogToConsole("Creation of serverSocket on port : " + port + " failed");
			}
			isRunning = true;
		}
		else {
			Logger.getInstance().printLogToConsole("Server is already running");
		}
	}
	
	public void run() {
		if(serverConnection == null) {
			Logger.getInstance().printLogToConsole("ServerSocket hasn't been initialized before running");
		}
		else {
			try {
				while (isRunning) {
					pool.execute(new ClientHandler(serverConnection.accept(), router));
				}
			} catch (IOException ioe) {
				pool.shutdown();
				Logger.getInstance().printLogToConsole("IO error when opening client socket");
			}
		}
	}
	
	public void stop() {
		if(isRunning) {
			isRunning = false;
			try {
				serverConnection.close();
			} catch (IOException ioe) {
				Logger.getInstance().printLogToConsole("ServerSocket closure failed");
			}
		}
	}

}
