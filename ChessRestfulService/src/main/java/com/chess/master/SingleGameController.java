package com.chess.master;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chess.classes.functional.DuoGame;
import com.chess.classes.functional.MessagesQueue;
import com.chess.classes.functional.RegisteredGame;
import com.chess.classes.helpful.GameState;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SingleGameController {

	
	/**
	 * Wysy³a ruch silnika do gry
	 */
	@RequestMapping(value = "/Single/sendMove/{NumberOfGame}/{NumberOfPlayer}/{move}", method = RequestMethod.POST)
	public @ResponseBody String sendMove(Locale locale, Model model, @PathVariable Integer NumberOfGame,
			@PathVariable Integer NumberOfPlayer, @PathVariable String move) {
		
			DuoGame theGame = (DuoGame) MessagesQueue.getInstance().getGame(NumberOfGame);
			GameState state = theGame.getStateOfGame();
			if(state==GameState.AWAIT_ON_SECOND_PLAYER){
				return "!! Your move is not accepted !! - second player is not connected";
			}
			else if(state==GameState.GAME_IN_PROGRESS){
				theGame.setEngineMove(NumberOfPlayer, move);
				return "Your move is accepted, waiting on opponent response";
			}
			return "Unknown Error";
	}
	
	/**
	 * Odbiera ruch silnika z gry
	 * @throws InterruptedException 
	 */
	@RequestMapping(value = "/Single/getMove/{NumberOfGame}/{NumberOfPlayer}", method = RequestMethod.POST)
	public @ResponseBody String getMove(Locale locale, Model model, @PathVariable Integer NumberOfGame,
			@PathVariable Integer NumberOfPlayer) throws InterruptedException {

			DuoGame theGame = (DuoGame) MessagesQueue.getInstance().getGame(NumberOfGame);
			String oppositeMove;
			long beforeMilis = System.currentTimeMillis();
			do{
				 oppositeMove = theGame.getOppositePlayerMove(NumberOfPlayer);
				 Thread.sleep(200);
			}
			while(oppositeMove==null && beforeMilis-System.currentTimeMillis()<10000);
			return oppositeMove;
	}
	
	

}
