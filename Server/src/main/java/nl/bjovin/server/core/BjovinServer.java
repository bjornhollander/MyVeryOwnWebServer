package nl.bjovin.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BjovinServer {
		
	private static Logger logger = Logger.getLogger(BjovinServer.class.getName());
	private static final int PORT = 1234;
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(PORT);
			logger.info("Server launched. Listening at port " + PORT + ".");
			
			Socket socket = server.accept();
			
			logger.info("Client connected.");
		} catch (IOException e) {
			String message = "Unable to open server socket at port " + PORT;
			logger.log(Level.SEVERE, message, e);
		}
		
	}
	
}
