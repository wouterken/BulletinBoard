import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * A simple input output client, that writes and reads to a socket
 * in an infinite loop.
 * Assumes the user will use Ctrl-Z to kill from command line when they are done.
 * @author wouterken
 *
 */
public class Client {

	private static String command;

	public static void main(String[] args) {

		try {

			//Attempt connection
			Socket clientSocket = new Socket("localhost",
					Integer.parseInt(args[0]));
			
			//Get output stream
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());

			//Client input
			BufferedReader inFromUser = new BufferedReader(
					new InputStreamReader(System.in));

			//Server output
			ClientWriter write = new ClientWriter(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));

			write.start();
			
			//Continuously send latest command to server.
			while (true) {
				command = inFromUser.readLine();
				outToServer.writeBytes(command + '\n');
			}
		} catch (Exception e) {
			//Something went wrong, blame it on the user and show them our usage info.
			usage();
		}
	}

	private static void usage() {
		System.out.println("Usage: java BBClient [portnumber]");
	}
}
