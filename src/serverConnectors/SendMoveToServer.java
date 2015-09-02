package serverConnectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import modelClasses.ServerDetails;

public class SendMoveToServer {
	public static String SendMove(Integer gameNumber, Integer playerNumber, String move){

		StringBuffer response = new StringBuffer();
		while(true){
			try {
				String url = ServerDetails.SERVER_ADDRESS + ServerDetails.SINGLE_GAME_SEND_MOVE_SUFIX + 
						gameNumber + "/" + playerNumber + "/" + move ;
				
				URL obj = new URL(url);
			
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("POST");
				
				String inputLine;
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
		
				//print result
				System.out.println(response.toString());
				break;
			} catch (IOException e) {
				e.printStackTrace();
				response.append("Unknown Error");
			}
		}
		return response.toString();
	}
}
