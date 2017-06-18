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
	private static int cookieNumber = 1;
	
	private Socket socket;

	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		logger.info("Client " + Thread.currentThread().getId() + " connected.");
		try(BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter outputWriter = new PrintWriter(socket.getOutputStream());) {

			boolean hasCookie = false;
			
			while(true) {
				String line = inputReader.readLine();
				if(line.startsWith("Cookie: ")) {
					hasCookie = true;
					logger.info("** Cookie line! **");
				}
				logger.info(line);
				
				
				if ("".equals(line))
					break;
			}

			outputWriter.println("HTTP/1.0 200 OK");
			outputWriter.println("Content-Type: text/html");
			if(!hasCookie) {
				String cookieName = "MyVeryOwnCookie";
				String cookieValue = "cookie_" + cookieNumber++;
				outputWriter.println("Set-Cookie: " + cookieName + ":" + cookieValue);
			}
			outputWriter.println();
			outputWriter.println("<HTML>");
			outputWriter.println("<HEAD><link rel=\"shortcut icon\" href=\"about:blank\" /></HEAD>");
			outputWriter.println("<BODY>");
			outputWriter.println("Hello from <br />My Very Own Web Server");
			outputWriter.println("</BODY>");
			outputWriter.println("</HTML>");

		} catch (IOException e) {
			String message = "Communication disrupted.";
			logger.log(Level.SEVERE, message, e);
		}
	}

}
