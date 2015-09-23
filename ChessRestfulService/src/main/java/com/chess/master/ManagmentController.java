package com.chess.master;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chess.classes.functional.MessagesQueue;
import com.chess.classes.helpful.JSONMessage;
import com.chess.classes.helpful.JSONMessage.TypeOfGame;
import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ManagmentController {
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/SubscribeTo", method = RequestMethod.POST)
	public @ResponseBody String subscribeTo(@RequestBody String jsonMessageString) 
	{
		Gson gson = new Gson();
		JSONMessage jsonMessage = gson.fromJson(jsonMessageString, JSONMessage.class);
		if(jsonMessage.getTypeOfGame().equals(TypeOfGame.SINGLE)){
			JSONMessage messageToReturn = MessagesQueue.getInstance().registerEngineToDuoGame(jsonMessage);
			return gson.toJson(messageToReturn);
		}
		return "null";
	}
	
	@RequestMapping(value = "/UnsubscribeFrom/{numberOfGame}/{NumberOfPlayer}", method = RequestMethod.POST)
	public String unsubscribeFrom(Locale locale, Model model) 
	{
		return "home";
	}
}
