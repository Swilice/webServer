package webserver;

public class Main {
	
	private static int port = 1337;
	private static int nbMaxThread = 8;
	private static String dataFolder = "data";
	
	static final void main(String[] args) {
		RequestHandlerFactory facto = new RequestHandlerFactory(dataFolder);
		HttpServer webServer = new HttpServer(port, nbMaxThread, facto);
		webServer.start();
		webServer.run();
	}
}
