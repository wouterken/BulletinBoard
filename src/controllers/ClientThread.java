package controllers;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.Socket;
import exceptions.InvalidUserException;
import actions.Action;

/**
 * A Client thread handles stream input and output
 * in two separate threads to allow for multi-client broadcasts
 * and simultaneous reading and writing.
 * @author wouterken
 *
 */
public class ClientThread extends Thread {

	private Socket s;
	private WriterThread writer;
	private boolean repeat = true;

	/**
	 * Create a new client thread.
	 * @param s
	 */
	public ClientThread(Socket s) {
		this.s = s;
	}

	/**
	 * maintains this current thread and a new writer thread in a while loop
	 * that allows for simultaneous reading and writing.
	 */
	public void run() {

		try {
			//Input stream reader.
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

			// Writer as a separate thread so we can write while waiting for input.
			// Allows for message broadcasts to all clients.
			writer = new WriterThread(s, ClientState.instance());

			writer.setOutput( useage() );
			//Read and process input and feed responses to the writer thread.
			while (repeat) {
				writer.setOutput( processInput(in.readLine()) );
			}
			s.close();

		}
		// Clean up after ourselves when we are finished.
		catch (ConnectException e) {
			ClientState.removeSelf();
			writer.interrupt();
		}
		catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	private String useage() {
		StringBuffer useage = new StringBuffer();
		useage.append("WELCOME TO THE BULLETIN BOARD\n");
		useage.append("\nType '.commands' to see a list of available commands\n");
		useage.append("Feel free to read posts on the board\n");
		useage.append("To join in with communications you will need to log-in.\n");
		useage.append("Some operations are restricted and can only be performed by an administrator\n");
		useage.append("\n(Admin credentials are user:'admin' password:'admin') \n");
		useage.append("\nThe bullet in board keeps all users up to date in a multi-threaded manner\n");
		useage.append("while still using only telnet. Try logging in two with different shells, and posting in one of them\n");
		useage.append("you will see a notification in the other.\n");
		return useage.toString();
	}

	/**
	 * broadcasts a message to all other writers (except our own)
	 * @param message
	 */
	public void broadcastMessage(String message){
		WriterThread.broadCastMessage = message;
		WriterThread.broadCastID++;
		writer.broadcastState++;
	}

	/**
	 * Attempt to process input from a client. If no input is passed,
	 * we assume the client has left and throw a ConnectException.
	 * Otherwise we parse and validate the input, and return a response.
	 * @param request
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public String processInput(String request) throws InstantiationException, IllegalAccessException, Exception {

		if(request == null)
			// We assume the client has disconnected.
			throw new ConnectException();

		String reply = "";
		try {
			// Create the appropriate action
			Action actionRequest = Actionfactory.createAction(request);

			// Perform the action
			try{
			reply = actionRequest.perform();
			}catch(InvalidUserException e ){
				//Naughty user tried to log in, kick him off
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				pw.write("ERROR: Invalid credentials, closing connection\n\n(admin credentials are user: \"admin\" pw: \"admin\"\n\nNOTE: The admin can create new users.)\n");
				pw.flush();
				ClientState.removeSelf();
				repeat = false;
				s.close();
			}

			// If the action has a broadcast message we send it to all.
			if(!actionRequest.broadcast().equals(""))
				this.broadcastMessage(actionRequest.broadcast());

		}
		// If command fails, we set the reply as the error message, so user knows
		// what went wrong.
		catch (Exception e) {
			reply =(e instanceof InvocationTargetException)?((InvocationTargetException) e).getTargetException().getMessage(): e.toString();
		}
		return reply;
	}
}
