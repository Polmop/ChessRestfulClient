package com.chess.classes.helpful;

public class GameSpecToReturn {
	
	Integer gameNumber;
	Integer playerNumber;
	

	public GameSpecToReturn(Integer gameNumber, Integer playerNumber) {
		this.gameNumber = gameNumber;
		this.playerNumber = playerNumber;
	}

	public Integer getGameNumber() {
		return gameNumber;
	}
	
	public Integer getPlayerNumber() {
		return playerNumber;
	}
	
}
