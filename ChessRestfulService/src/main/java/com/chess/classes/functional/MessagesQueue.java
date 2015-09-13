package com.chess.classes.functional;

import java.util.LinkedList;

import com.chess.classes.helpful.GameSpecToReturn;
import com.chess.classes.helpful.GameState;


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
	
	public GameSpecToReturn registerEngineToDuoGame(){
		// poszukiwania czy nie ma silnika oczekujacego na towarzysza
		for (int i=0; i<queue.size() ; i++) {
			RegisteredGame registeredGame = queue.get(i);
			
			if(registeredGame.getStateOfGame().equals(GameState.AWAIT_ON_SECOND_PLAYER)){
				/* Najpierw poszukaj gier gdzie brakuje tylko jednego gracza */
				registeredGame.setStateOfGame(GameState.GAME_IN_PROGRESS);
				return new GameSpecToReturn(i, 2);
			} else if(registeredGame.getStateOfGame().equals(GameState.AWAIT_ON_ENGINES)){
				/*  Potem gier gdzie nie ma zadnego gracza i stoja puste ale jeszcze nie wyczyscil ich ChessGameCleaner */
				registeredGame.setStateOfGame(GameState.AWAIT_ON_SECOND_PLAYER);
				return new GameSpecToReturn(i, 1);
			}
		}
		// Jesli nic nie znalazles zaloz nowa gre
		synchronized (this) {
			DuoGame registeredGame = new DuoGame();
			registeredGame.setStateOfGame(GameState.AWAIT_ON_SECOND_PLAYER);
			queue.add(registeredGame);
			return new GameSpecToReturn(queue.size()-1, 1);
		}
	}
	
	public void registerEngineToCluster(){
		//TODO: Rejstruje silnik do klastra
	}
}
