package com.chess.master;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chess.classes.functional.MessagesQueue;
import com.chess.classes.helpful.GameSpecToReturn;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ManagmentController {
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/SubscribeTo/{SingleOrCluster}/{AsEnergySafeOrNot}", method = RequestMethod.POST)
	public @ResponseBody String subscribeTo(Locale locale, Model model, @PathVariable String SingleOrCluster , @PathVariable String AsEnergySafeOrNot) {
		if(SingleOrCluster.equals("Single")){
			GameSpecToReturn gameSpecification = MessagesQueue.getInstance().registerEngineToDuoGame();
			return gameSpecification.getGameNumber()+" "+gameSpecification.getPlayerNumber() ;
		}
		return "null";
	}
	
	@RequestMapping(value = "/UnsubscribeFrom/{numberOfGame}/{NumberOfPlayer}", method = RequestMethod.POST)
	public String unsubscribeFrom(Locale locale, Model model) {
		return "home";
	}
}
