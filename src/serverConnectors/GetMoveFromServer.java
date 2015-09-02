package serverConnectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JLabel;

import modelClasses.ServerDetails;

public class GetMoveFromServer implements Runnable{
	
	
	Integer gameNumber;
	Integer playerNumber;
	JLabel informationLabel;
	JButton buttonToEnable;
	
	
	public GetMoveFromServer(Integer gameNumber, Integer playerNumber, JLabel informationLabel, JButton buttonToEnable) {
		// TODO Auto-generated constructor stub
		this.gameNumber = gameNumber;
		this.playerNumber = playerNumber;
		this.informationLabel = informationLabel;
		this.buttonToEnable = buttonToEnable;
	}
	
	public void getMove(){

		StringBuffer response = new StringBuffer();
		int counter_of_connection = 0;
		while(true){
			try {
				String url = ServerDetails.SERVER_ADDRESS + ServerDetails.SINGLE_GAME_GET_MOVE_SUFIX + 
						gameNumber + "/" + playerNumber ;
				
				URL obj;
					obj = new URL(url);
			
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("POST");
				con.setReadTimeout(15000);
				
				String inputLine;
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				//print result
				System.out.println("Odpowiedz serwera to {" + response.toString()+"}");
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				counter_of_connection++;
				if(counter_of_connection>=5){
					System.out.println("Resource is not ready too long - Game over");
					response.append("Game Over - Opponent walks away");
					break;
				}
				System.out.println("Resource is not ready - retry "+counter_of_connection+" time" );
				//e.printStackTrace();
			}
		}
		informationLabel.setText("Ruch drugiego gracza to: " + response.toString());
		buttonToEnable.setEnabled(true);
	}

	@Override
	public void run() {
		getMove();
	}
}
