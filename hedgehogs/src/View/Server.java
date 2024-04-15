package View;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author al9226
 *
 */
public class Server {
	
	private static ServerSocket socket_;
	
	public static void main(String[] args) {
		
		try {
			
			socket_ = new ServerSocket(9050);

			for (; true;) {
			
				Socket connection = socket_.accept();
				//Thread thread = new Thread(new Game(0));
				//thread.start();
				
			}
			
		} 

		catch (IOException e) {
			
			System.out.println("socket error: " + e.getMessage());
			
		}

	}

}