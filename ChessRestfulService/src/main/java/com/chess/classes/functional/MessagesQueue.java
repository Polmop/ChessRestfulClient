package com.chess.classes.functional;

import java.util.LinkedList;

import com.chess.classes.helpful.GameState;
import com.chess.classes.helpful.JSONMessage;


public class MessagesQueue {
	
	private LinkedList<RegisteredGame> queue = new LinkedList<RegisteredGame>();
	private static MessagesQueue queueInstance = null;
	
	

	protected MessagesQueue() {
	      // Exists only to defeat instantiation.
	}
	public static MessagesQueue getInstance() {
		if(queueInstance == null) {
			queueInstance = new MessagesQueue(); 
		}
		return queueInstance;
	}
	
	public RegisteredGame getGame(int NumberOfGame){
		return queue.get(NumberOfGame);
	}
	
	public JSONMessage registerEngineToDuoGame(JSONMessage jsonMessage){
		// poszukiwania czy nie ma silnika oczekujacego na towarzysza
		for (int i=0; i<queue.size() ; i++) {
			RegisteredGame registeredGame = queue.get(i);
			
			if(registeredGame.getStateOfGame().equals(GameState.AWAIT_ON_SECOND_PLAYER)){
				/* Najpierw poszukaj gier gdzie brakuje tylko jednego gracza */
				registeredGame.setStateOfGame(GameState.GAME_IN_PROGRESS);
				jsonMessage.setGameNumber(i);
				jsonMessage.setPlayerNumber(2);
				return jsonMessage;
			} else if(registeredGame.getStateOfGame().equals(GameState.AWAIT_ON_ENGINES)){
				/*  Potem gier gdzie nie ma zadnego gracza i stoja puste ale jeszcze nie wyczyscil ich ChessGameCleaner */
				registeredGame.setStateOfGame(GameState.AWAIT_ON_SECOND_PLAYER);
				jsonMessage.setGameNumber(i);
				jsonMessage.setPlayerNumber(1);
				return jsonMessage;
			}
		}
		// Jesli nic nie znalazles zaloz nowa gre
		synchronized (this) {
			DuoGame registeredGame = new DuoGame();
			registeredGame.setStateOfGame(GameState.AWAIT_ON_SECOND_PLAYER);
			queue.add(registeredGame);
			jsonMessage.setGameNumber(queue.size()-1);
			jsonMessage.setPlayerNumber(1);
			return jsonMessage;
		}
	}
	
	public void registerEngineToCluster(){
		//TODO: Rejstruje silnik do klastra
	}
}
