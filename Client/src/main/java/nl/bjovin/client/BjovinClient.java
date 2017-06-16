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

			while(true) {
				BufferedReader cmdReader = new BufferedReader(new InputStreamReader(System.in));
				System.out.print("Command> ");
				String command = cmdReader.readLine();

				PrintWriter outputWriter = getOutputWriter(client);
				outputWriter.println(command);
				outputWriter.flush();

				if("exit".equalsIgnoreCase(command)) {
					break;
				}

				BufferedReader inputReader = getInputReader(client);
				logger.info("Server wrote: " + inputReader.readLine());
			}
			
			client.close();

		} catch (IOException e) {
			String message = "Unable to connect to server at port " + PORT + ".";
			logger.log(Level.SEVERE, message, e);
		}
	}

	private static BufferedReader getInputReader(Socket client) throws IOException {
		InputStream inputStream = client.getInputStream();
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	private static PrintWriter getOutputWriter(Socket client) throws IOException {
		OutputStream outputStream = client.getOutputStream();
		return new PrintWriter(outputStream);
	}

}
