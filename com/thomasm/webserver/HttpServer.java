package com.thomasm.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.thomasm.logger.Logger;

public class HttpServer implements Runnable {
	private int port;
	private int nbMaxThread;
	private RequestHandlerFactory facto;
	private ClientHandler clientHandler;
	
	boolean isRunning;
	ServerSocket serverConnection;
	
	public HttpServer(int port, int nbMaxThread, RequestHandlerFactory facto) {
		this.port = port;
		this.nbMaxThread = nbMaxThread;
		this.facto = facto;
		
		clientHandler = new ClientHandler();
		isRunning = false;
	}
	
	public void start() {
		if(!isRunning) {
			try {
				serverConnection = new ServerSocket(port);
			} catch (IOException ioe) {
				Logger.getInstance().printLogToConsole("500: Creation of serverSocket on port : " + port + " failed");
			}
			isRunning = true;
		}
		else {
			Logger.getInstance().printLogToConsole("500: Server is already running");
		}
	}
	
	public void run() {
		if(serverConnection == null) {
			Logger.getInstance().printLogToConsole("500: ServerSocket hasn't been initialized before running");
		}
		else {
			try {
				while (isRunning) {
					Socket clientConnection = serverConnection.accept();
					HttpRequest request = clientHandler.handle(clientConnection);
					if (request == null) {
						Logger.getInstance().printLogToConsole("500: Request creation failed");
					}
					RequestHandler requestHandler = facto.getHandler(request);
					HttpResponse response = requestHandler.handle(request, clientConnection);
				}
			} catch (IOException ioe) {
				Logger.getInstance().printLogToConsole("500: IO error when opening client socket");
			}
		}
	}
	
	public void stop() {
		if(isRunning) {
			isRunning = false;
			try {
				serverConnection.close();
			} catch (IOException ioe) {
				Logger.getInstance().printLogToConsole("500: serverSocket closure failed");
			}
		}
	}

}
