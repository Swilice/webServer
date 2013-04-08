package com.thomasm.webserver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
	private String httpVersion;
	private String statusCode;
	private String statusReason;
	private Map<String, String> headers;
	private OutputStream body;
	private boolean bodyUseStarted;
	
	public HttpResponse(Socket clientConnection) throws IOException{
		bodyUseStarted = false;
		headers = new HashMap<String, String>();
		body = new BufferedOutputStream(clientConnection.getOutputStream());
		
		this.setHttpVersion("HTTP/1.1");
		this.addHeader("Server", "ThomasMwebServer/1.0");
		this.addHeader("Connection", "Close");
	}
	
	/* Function copied from external source */
	private void sendHeaders() throws IOException {
		if ( bodyUseStarted )
			return;

		bodyUseStarted = true;

		PrintWriter pw = new PrintWriter( body );
		// Status Line
		pw.print(httpVersion);
		pw.print(' ');
		pw.print(statusCode);
		pw.print(' ');
		pw.print(statusReason);
		pw.print("\r\n");
		// Headers
		for (String headerKey : headers.keySet() ) {
			pw.print(headerKey);
			pw.print(": ");
			pw.print(headers.get(headerKey));
			pw.print("\r\n");
		}
		// Separator
		pw.print("\r\n");
		// This is important, else this never gets written/sent:
		pw.flush();
		// Do NOT pw.close(); here yet... it would close the underlying OutputStream & Socket already now, much too early!
	}
	/* End of copy */
	
	/* Accessors */
	public String getHttpVersion() {
		return httpVersion;
	}
	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusReason() {
		return statusReason;
	}
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public String getHeader(String key) {
		return headers.get(key);
	}
	public void addHeader(String key, String value) {
		headers.put(key, value);
	}
	public OutputStream getBody() throws IOException {
		sendHeaders();
		return body;
	}
}
