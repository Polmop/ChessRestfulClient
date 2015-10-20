package serverConnectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import modelClasses.JSONMessage;
import modelClasses.ServerDetails;
//import modelClasses.JSONMessage.EnergyOrNot;
import modelClasses.JSONMessage.Topic;
import modelClasses.JSONMessage.TypeOfGame;

public class GetMoveFromServer implements Runnable{
	
	
	Integer gameNumber;
	Integer playerNumber;
	JLabel informationLabel;
	JButton buttonToEnable;
	
	
	public GetMoveFromServer(Integer gameNumber, Integer playerNumber, JLabel informationLabel, JButton buttonToEnable) {
		this.gameNumber = gameNumber;
		this.playerNumber = playerNumber;
		this.informationLabel = informationLabel;
		this.buttonToEnable = buttonToEnable;
	}
	
	public void getMove(){

		int counter_of_connection = 0;
		JSONMessage message = new JSONMessage();
		message.setGameNumber(gameNumber);
		message.setPlayerNumber(playerNumber);
		message.setTypeOfGame(TypeOfGame.SINGLE);
		//message.setEnergyOrNot(EnergyOrNot.DONT_LOOK_ON_ENERGY);
		message.setTopic(Topic.GET_MESSAGE);
		
		Gson gson = new Gson();
		
		String responseString;
		while(true){
			try {
				String url = ServerDetails.SERVER_ADDRESS + ServerDetails.GET_MESSAGE ;
				HttpPost post = new HttpPost(url);
				
				StringEntity postingString = new StringEntity(gson.toJson(message));//convert your message to json
				post.setEntity(postingString);
		        CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpResponse response = httpClient.execute(post);
				responseString = new BasicResponseHandler().handleResponse(response);
				JSONMessage messageFromServer = gson.fromJson(responseString, JSONMessage.class);
				if(messageFromServer.getMessage() != null){
					responseString = messageFromServer.getMessage();
					break;
				}
			} catch (IOException e) {
				counter_of_connection++;
				if(counter_of_connection>=50){
					responseString = "Game Over - Opponent walks away";
					break;
				}
			}
		}
		informationLabel.setText("Ruch drugiego gracza to: " + responseString);
		buttonToEnable.setEnabled(true);
	}

	@Override
	public void run() {
		// Non stop getting messages. If one get - wait till next will come
		while(true){
			getMove();
		}
	}
}
