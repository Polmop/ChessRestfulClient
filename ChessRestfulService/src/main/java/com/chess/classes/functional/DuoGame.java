package com.chess.classes.functional;

import com.chess.classes.helpful.GameState;
import com.chess.classes.helpful.JSONMessage;
import com.chess.classes.helpful.JSONMessage.Topic;

public class DuoGame extends RegisteredGame {
	
	
	
	
	/**
	 * Set which engine send move and what move he did
	 */
	@Override
	public void setEngineMove(Integer numberOfEngine, String move){
		if(numberOfEngine.equals(1)){
			playerOneMessageAwait = 1;
			playerOneMove = move;
		}else if(numberOfEngine.equals(2)){
			playerTwoMove = move;
			playerTwoMessageAwait = 1;
		}
	}
	
	/**
	 * Gets engine move
	 */
	@Override
	public String getOppositePlayerMove(int numberOfPlayerWhoSendRequest){
		
		if(numberOfPlayerWhoSendRequest==1 && playerTwoMessageAwait==1){
			playerTwoMessageAwait=0;
			return playerTwoMove;
		} else if(numberOfPlayerWhoSendRequest==2 && playerOneMessageAwait==1){
			playerOneMessageAwait=0;
			return playerOneMove;
		}
		return null;
	}
	
	public String parseSendMessageInDuoGame(JSONMessage jsonMessage){
		if(jsonMessage.getTopic()==Topic.SEND_MESSAGE){
			if( getStateOfGame() == GameState.AWAIT_ON_SECOND_PLAYER){
				return "Your message is not accepted! Second player is not connected.";
			}
			setEngineMove(jsonMessage.getPlayerNumber(), jsonMessage.getMessage());
		}
		return "Game accepted your message";
	}
	
	public JSONMessage parseGetMessageInDuoGame(JSONMessage jsonMessage){
		String move = getOppositePlayerMove(jsonMessage.getPlayerNumber());
		jsonMessage.setMessage(move);
		return jsonMessage;
	}
	
}
