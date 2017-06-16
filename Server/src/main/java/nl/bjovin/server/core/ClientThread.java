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

	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			logger.info("Client wrote: " + inputReader.readLine());
		}catch (IOException e) {
			String message = "Unable to accuire input listener.";
			logger.log(Level.SEVERE, message, e);
		}

		try (PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
			writer.println("Hello from server!");
			logger.info("Sending response...");
			writer.flush();
		} catch (IOException e) {
			String message = "Unable to accuire output writer.";
			logger.log(Level.SEVERE, message, e);
		}
	}

}
