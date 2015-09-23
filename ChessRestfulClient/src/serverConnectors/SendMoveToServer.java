package serverConnectors;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import modelClasses.JSONMessage;
import modelClasses.JSONMessage.EnergyOrNot;
import modelClasses.JSONMessage.Topic;
import modelClasses.JSONMessage.TypeOfGame;
import modelClasses.ServerDetails;

public class SendMoveToServer {
	public static String SendMove(Integer gameNumber, Integer playerNumber, String move){

		JSONMessage message = new JSONMessage();
		message.setGameNumber(gameNumber);
		message.setPlayerNumber(playerNumber);
		message.setMessage(move);
		message.setTypeOfGame(TypeOfGame.SINGLE);
		message.setEnergyOrNot(EnergyOrNot.DONT_LOOK_ON_ENERGY);
		message.setTopic(Topic.SEND_MESSAGE);
		
		Gson gson = new Gson();
		
		String responseString;
		while(true){
			try {
				String url = ServerDetails.SERVER_ADDRESS + ServerDetails.SEND_MESSAGE ;
				HttpPost post = new HttpPost(url);
				
				/*RequestConfig requestConfig = RequestConfig.custom()
						  .setSocketTimeout(3000)
						  .setConnectTimeout(3000)
						  .setConnectionRequestTimeout(10000)
						  .build();
				post.setConfig(requestConfig);*/
				StringEntity postingString = new StringEntity(gson.toJson(message));//convert your message to json
				post.setEntity(postingString);
		        CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpResponse response = httpClient.execute(post);
				responseString = new BasicResponseHandler().handleResponse(response);
				break;
			} catch (IOException e) {
				e.printStackTrace();
				responseString = "Unknown Error";
			}
		}
		return responseString;
		
	}
}
