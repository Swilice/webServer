package webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
				//error
			}
			isRunning = true;
		}
		else {
			//error
		}
	}
	
	public void run() {
		if(serverConnection == null) {
			//error
		}
		else {
			try {
				while (isRunning) {
					Socket clientConnection = serverConnection.accept();
					HttpRequest request = clientHandler.handle(clientConnection);
					if (request == null) {
						//error
					}
					RequestHandler requestHandler = facto.defineHandler(request);
					HttpResponse response = requestHandler.handle(request, clientConnection);
					// client handler send response ?
				}
			} catch (IOException ioe) {
				//error
			}
		}
	}
	
	public void stop() {
		if(isRunning) {
			isRunning = false;
			try {
				serverConnection.close();
			} catch (IOException ioe) {
				//error
			}
		}
	}

}
