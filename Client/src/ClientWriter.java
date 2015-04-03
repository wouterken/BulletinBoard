import java.io.BufferedReader;
import java.io.IOException;

/**
 * A simple thread that writes output from a buffered reader continuously.
 * @author wouterken
 *
 */
public class ClientWriter extends Thread {

	private BufferedReader in;

	public ClientWriter(BufferedReader input) {
		in = input;
	}

	@Override
	/**
	 * Run forever writing output from the server, 
	 * We assume the user will kill the application with an escape code when they want to quit. 
	 */
	public void run() {
		while (true) {
			try {
				System.out.println(in.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
