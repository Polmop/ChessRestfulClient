package com.chess.master;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chess.classes.functional.DuoGame;
import com.chess.classes.functional.MessagesQueue;
import com.chess.classes.helpful.JSONMessage;
import com.chess.classes.helpful.JSONMessage.TypeOfGame;
import com.google.gson.Gson;


@Controller
public class MessagesController {

	@RequestMapping(value = "/sendMessage", method = {RequestMethod.POST})
	public @ResponseBody String sendMessage(@RequestBody String jsonMessageString)
	{
		Gson gson = new Gson();
		JSONMessage jsonMessage = gson.fromJson(jsonMessageString, JSONMessage.class);
		if(jsonMessage.getTypeOfGame() == TypeOfGame.SINGLE){
			DuoGame game = (DuoGame)MessagesQueue.getInstance().getGame(jsonMessage.getGameNumber());
			return game.parseSendMessageInDuoGame(jsonMessage);
		}
		return "Game accepted your message";
	}
	
	@RequestMapping(value = "/getMessage", method = RequestMethod.POST)
	public @ResponseBody String getMessage(@RequestBody String jsonMessageString) throws InterruptedException 
	{
		Gson gson = new Gson();
		JSONMessage jsonMessage = gson.fromJson(jsonMessageString, JSONMessage.class);
		JSONMessage jsonMessageToReturn = jsonMessage;
		long beforeMilis = System.currentTimeMillis();
		
		if(jsonMessage.getTypeOfGame() == TypeOfGame.SINGLE){
			do{
				DuoGame game = (DuoGame)MessagesQueue.getInstance().getGame(jsonMessage.getGameNumber());
				jsonMessageToReturn = game.parseGetMessageInDuoGame(jsonMessage);
				Thread.sleep(200);
			} while (jsonMessageToReturn.getMessage() == null && beforeMilis-System.currentTimeMillis()<10000);
		}
		return gson.toJson(jsonMessageToReturn);
	}
	
}
