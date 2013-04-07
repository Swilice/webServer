package com.thomasm.webserver;

import java.util.Map;

public class HttpRequest {
	
	private String method;
	private String url;
	private String httpVersion;
	private Map<String, String> headers;	// Unused in this simple example
	private String body;					// Same here
	
	/* Accessors */
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHttpVersion() {
		return httpVersion;
	}
	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}
}
