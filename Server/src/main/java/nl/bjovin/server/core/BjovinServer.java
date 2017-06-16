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

	private static final Logger logger = Logger.getLogger(BjovinServer.class.getName());
	private static final int PORT = 1234;

	private static ServerSocket server;
	private static Socket socket;

	private static boolean errorOccured = false;

	public static void main(String[] args) {

		launchServer();
		if(!errorOccured) {
			try {
				while(true) {
					socket = server.accept();
					logger.info("Client connected.");
					new ClientThread(socket).start();
					logger.info("New server instance started.");

				}

			} catch (IOException e) {
				errorOccured = true;
				String message = "Unable to accept connection at socket.";
				logger.log(Level.SEVERE, message, e);
			}

		}

		logger.info("Shutting down server.");
	}

	private static void launchServer() {
		try {
			server = new ServerSocket(PORT);
			logger.info("Server launched. Listening at port " + PORT + ".");
		} catch (IOException e) {
			errorOccured = true;
			String message = "Unable to open server socket at port " + PORT;
			logger.log(Level.SEVERE, message, e);
		}
	}
}
