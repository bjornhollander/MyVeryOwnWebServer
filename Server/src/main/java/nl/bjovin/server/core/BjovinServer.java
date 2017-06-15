package nl.bjovin.server.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BjovinServer {
		
	private static Logger logger = Logger.getLogger(BjovinServer.class.getName());
	private static final int PORT = 1234;
	
	public static void main(String[] args) {
		try {
			Socket socket = launchServer();
			
			BufferedReader inputReader = getInputReader(socket);
			logger.info("Client wrote: " + inputReader.readLine());
			
			PrintWriter writer = getWriter(socket);
			writer.println("Hello from server!");
			logger.info("Sending response...");
			writer.flush();
			
		} catch (IOException e) {
			String message = "Unable to open server socket at port " + PORT;
			logger.log(Level.SEVERE, message, e);
		}
		
		logger.info("Shutting down server.");
	}

	private static PrintWriter getWriter(Socket socket) throws IOException {
		return new PrintWriter(socket.getOutputStream());
	}

	private static BufferedReader getInputReader(Socket socket) throws IOException {
		InputStream socketInputStream = socket.getInputStream();
		InputStreamReader reader = new InputStreamReader(socketInputStream);
		return new BufferedReader(reader);
	}

	private static Socket launchServer() throws IOException {
		ServerSocket server = new ServerSocket(PORT);
		logger.info("Server launched. Listening at port " + PORT + ".");
		
		Socket socket = server.accept();
		
		logger.info("Client connected.");
		
		return socket;
	}
	
}
