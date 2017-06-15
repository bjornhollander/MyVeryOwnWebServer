package nl.bjovin.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BjovinClient {

	private static final Logger logger = Logger.getLogger(BjovinClient.class.getName());
	private static final int PORT = 1234;
	
	public static void main(String[] args) {
		try {
			Socket client = new Socket("localhost", PORT);
			logger.info("Succesfully connected to server at port" + PORT + ".");
			
			PrintWriter outputWriter = getPrintWriter(client);
			outputWriter.println("Hello from client!");
			logger.info("Sending message...");
			outputWriter.flush();
			logger.info("Message send!");
			
			BufferedReader inputReader = getInputReader(client);
			logger.info("Server wrote: " + inputReader.readLine());
			
			
		} catch (IOException e) {
			String message = "Unable to connect to server at port " + PORT + ".";
			logger.log(Level.SEVERE, message, e);
		}
	}

	private static BufferedReader getInputReader(Socket client) throws IOException {
		InputStream inputStream = client.getInputStream();
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	private static PrintWriter getPrintWriter(Socket client) throws IOException {
		OutputStream outputStream = client.getOutputStream();
		return new PrintWriter(outputStream);
	}
	
}
