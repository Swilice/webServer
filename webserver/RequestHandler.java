package webserver;

import java.io.IOException;
import java.net.Socket;

public abstract class RequestHandler {
	
	protected abstract void handle(Socket socket) throws IOException;

}
