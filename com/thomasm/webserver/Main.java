package com.thomasm.webserver;

public class Main {
	
	private static int port = 1337;
	private static int nbMaxThread = 8;
	private static final String dataFolder = "src/data";
	
	public static void main(String[] args) {
		if(args.length == 2) {
			if(Integer.parseInt(args[0]) >= 0 && Integer.parseInt(args[0]) <= 65535) 
				port = Integer.parseInt(args[0]);
			if(Integer.parseInt(args[1]) >= 0 && Integer.parseInt(args[1]) <= 50) 
				nbMaxThread = Integer.parseInt(args[1]);
		}
		
		RequestRouter router = new RequestRouter(dataFolder);
		HttpServer webServer = new HttpServer(port, nbMaxThread, router);
		webServer.start();
		webServer.run();
	}
}
