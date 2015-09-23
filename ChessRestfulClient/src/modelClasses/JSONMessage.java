package modelClasses;

public class JSONMessage {

	private TypeOfGame typeOfGame;
	private EnergyOrNot energyOrNot;
	private Topic topic;
	private String message;
	private Integer gameNumber;
	private Integer playerNumber;
	
	
	public enum TypeOfGame {
		SINGLE,
		CLUSTER;
	}
	
	public enum EnergyOrNot {
		ENERGYSAFE,
		DONT_LOOK_ON_ENERGY;
	}

	public enum Topic {
		REGISTER,
		UNREGISTER,
		SEND_MESSAGE,
		GET_MESSAGE,
	}
	
	public TypeOfGame getTypeOfGame() {
		return typeOfGame;
	}

	public void setTypeOfGame(TypeOfGame typeOfGame) {
		this.typeOfGame = typeOfGame;
	}

	public EnergyOrNot getEnergyOrNot() {
		return energyOrNot;
	}

	public void setEnergyOrNot(EnergyOrNot energyOrNot) {
		this.energyOrNot = energyOrNot;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getGameNumber() {
		return gameNumber;
	}

	public void setGameNumber(Integer gameNumber) {
		this.gameNumber = gameNumber;
	}

	public Integer getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(Integer playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	
	
	
}
