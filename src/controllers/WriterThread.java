package controllers;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

/**
 * A writer thread waits until it receives output, and outputs it
 * upon arrival.
 * It periodically checks if there are any broadcast messages available
 * and if so sends them to its output before incrementing its broadcast counter
 * (so each broadcast is only sent once).
 * @author wouterken
 *
 */
public class WriterThread extends Thread {

	public static int broadCastID = 0;
	public static String broadCastMessage = "";
	public int broadcastState = 0;
	private final char[] clearScreen = new char[100];
	private PrintWriter out;
	private ClientState state;
	private String output = "";

	/**
	 * A writer thread needs a socket to write to,
	 * and a state to monitor for formatted output.
	 * @param s
	 * @param state
	 * @throws IOException
	 */
	public WriterThread(Socket s, ClientState state) throws IOException {
		//array of new line characters to clear the screen.
		Arrays.fill(clearScreen, '\n');
		this.state = state;
		this.out = new PrintWriter(s.getOutputStream(), true);
		this.setOutput("");
		this.broadcastState = broadCastID + 1;
		this.start();
	}

	/**
	 * Prints a formatted prompt to the user including their current username,
	 * and their currently selecte topic.
	 */
	public void prompt() {
		out.print(String.format("\nUSER: '%s', TOPIC [%s]\n", state.getName(), state.topicName()));
		out.print("Command : ");
		out.flush();
	}

	/**
	 * Clears the client screen
	 */
	public void clear() {
		out.print(String.valueOf(clearScreen));
	}

	/**
	 * In a loop
	 * the thread periodically checks for output and prints it on arrival.
	 * it also compares its internal broadcast counter with the global static variable
	 * and broadcasts any due messages if these are equal.
	 */
	public void run() {
		while (true) {
			//Prompt user for input.
			prompt();

			/**
			 * Wait for output.
			 */
			while (getOutput().equals("")) {

					// Broadcast any pending messages.
					if (broadcastState == broadCastID) {
						printBroadcastMessage();
					}
					pause();

			}
			// Clear screen and print output.
			clear();
			out.println(String.format("Response: --> %s", getOutput()));
			setOutput("");
			out.flush();
		}
	}

	/**
	 * Pauses current thread for 100 millis.
	 */
	private void pause() {
		try{
			sleep(100);
		}catch (InterruptedException e) {
		}
	}

	/**
	 * Prints the pending broadcast message to the client socket.
	 */
	private void printBroadcastMessage() {
		clear();
		out.println(broadCastMessage);
		broadcastState++;
		prompt();
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
