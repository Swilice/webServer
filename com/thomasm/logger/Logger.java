package com.thomasm.logger;

public class Logger {
	
	private static Logger instance;
	

    public static Logger getInstance() {
        if (null == instance) { 
            instance = new Logger();
        }
        return instance;
    }

    private Logger() {
    	
    }

    public void printLogToConsole(String error) {
    	System.out.println(error);
    }
}
