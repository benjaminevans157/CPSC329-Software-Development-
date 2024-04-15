package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * @author sm9039
 *
 */
public class ClientRunner implements Runnable {
private Socket connect_;
private Board board_;
private List<Player> players_; 
private Piece piece_;

public ClientRunner (Socket connection) {
	connect_ = connection;
}
	@Override
	public void run () {
		// TODO Auto-generated method stub
		try {
		BufferedReader read = new BufferedReader(new InputStreamReader(connect_.getInputStream())); 
		PrintWriter writer = new PrintWriter(connect_.getOutputStream());
			
				
		
		} catch(IOException e) {
			System.out.println("connection error"); 
		}
	}

}
