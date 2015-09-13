package serverConnectors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import modelClasses.ServerDetails;

public class GameRegistration {

	public static String registerToDuoGame(String EnergySafeOrNot) throws Exception {

		final String TYPE_OF_GAME = "Single/";
		String url = ServerDetails.SERVER_ADDRESS + ServerDetails.SUBSCRIBE_SUFIX + TYPE_OF_GAME + EnergySafeOrNot;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		String inputLine;
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		return response.toString();

	}
}
