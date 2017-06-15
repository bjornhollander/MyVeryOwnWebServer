package nl.bjovin.client;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BjovinClient {

	private static final Logger logger = Logger.getLogger(BjovinClient.class.getName());
	private static final int PORT = 1234;
	
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", PORT);
			
			logger.info("Succesfully connected to server at port" + PORT + ".");
		} catch (IOException e) {
			String message = "Unable to connect to server at port " + PORT + ".";
			logger.log(Level.SEVERE, message, e);
		}
	}
	
}
