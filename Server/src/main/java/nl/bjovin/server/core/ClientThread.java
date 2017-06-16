package nl.bjovin.server.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClientThread extends Thread {

	private static final Logger logger = Logger.getLogger(ClientThread.class.getName());
	private Socket socket;
	private boolean terminate = false;

	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		logger.info("Client " + Thread.currentThread().getId() + " connected.");
		try {
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter outputWriter = new PrintWriter(socket.getOutputStream());
			
			while(!terminate ) {
				processMessage(inputReader, outputWriter);
			}
			
			socket.close();
			logger.info("Client " + Thread.currentThread().getId() + " closed connection.");
		} catch (IOException e) {
			String message = "Communication disrupted.";
			logger.log(Level.SEVERE, message, e);
		}
	}

	private void processMessage(BufferedReader inputReader, PrintWriter outputWriter) throws IOException {
		String message = inputReader.readLine();
		
		if("exit".equalsIgnoreCase(message)) {
			terminate = true;
		}
		
		logger.info("Client " + Thread.currentThread().getId() + ": " + message);
		
		outputWriter.println("Echo: " + message);
		outputWriter.flush();
		
	}

}
