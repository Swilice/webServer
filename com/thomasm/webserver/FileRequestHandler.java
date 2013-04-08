package com.thomasm.webserver;

public abstract class FileRequestHandler implements RequestHandler {
	
	protected String dataFolder;
	
	/* Accessors */
	public String getDataFolder() {
		return dataFolder;
	}
	public void setDataFolder(String dataFolder) {
		this.dataFolder = dataFolder;
	}
}
