import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import configuration.StringConstants;
import controllers.ClientThread;
import model.BulletinBoardIO;

// Main class for the bulletin board
class BulletinBoard {

	// Entry point for the bulletin board server
	public static void main(String args[]) throws Exception {

		// Create an unbounded socket.
		ServerSocket sock = new ServerSocket();

		// Create the address, args[0] should hold the port number.
		try{
			InetSocketAddress address = new InetSocketAddress(Integer.parseInt(args[0]));

			//Load boards into memory
			BulletinBoardIO.loadBoards();

			// Now, bind the socket to the address we just created.
			sock.bind(address, 5);

			// in a main loop, accept incoming connections and
			// create a thread to handle them.
			System.out.println(String.format(StringConstants.SERVER_LISTENING, args[0]));

			while (true) {
				Socket clientSocket = sock.accept();
				System.out.println(StringConstants.NEW_CLIENT_CONNECTION);
				ClientThread w = new ClientThread(clientSocket);
				w.start();
			}

		}catch(Exception e){
			printUsage();
		}
	}

	private static void printUsage() {
		System.out.println("Usage: java BulletinBoard port");
	}
}
