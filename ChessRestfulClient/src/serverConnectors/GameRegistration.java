package serverConnectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.logging.impl.Log4JLogger;
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

public class GameRegistration {
	
	private final static Logger LOGGER = Logger.getLogger(GameRegistration.class.getName()); 
	
	public static JSONMessage registerToDuoGame(/*EnergyOrNot EnergySafeOrNot*/) throws Exception {

		String url = ServerDetails.SERVER_ADDRESS + ServerDetails.SUBSCRIBE_SUFIX;
		
		JSONMessage message = new JSONMessage();
		message.setTypeOfGame(TypeOfGame.SINGLE);
		//message.setEnergyOrNot(EnergySafeOrNot);
		message.setTopic(Topic.REGISTER);
		
		Gson gson = new Gson();
		String responseString;
		while(true){
			try {
				HttpPost post = new HttpPost(url);
				StringEntity postingString = new StringEntity(gson.toJson(message));//convert your message to json
				post.setEntity(postingString);
		        CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpResponse response = httpClient.execute(post);
				responseString = new BasicResponseHandler().handleResponse(response);
				message = gson.fromJson(responseString, JSONMessage.class);
				LOGGER.info("Game connect to game number " + message.getGameNumber()
						+ " on chess server and receive player number " + message.getPlayerNumber());
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
		return message;

	}
}
