package pgnFileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SingleChessGame{
	
	private Map <String, String> game = new HashMap<>();
	private int lastMove = 1;
	
	/**
	 * Sets clear games
	 */
	public void loadClearGame(){
		game.clear();
		lastMove = 1;
		game.put("Event", null);
		game.put("Site", null);
		game.put("Date", null);
		game.put("Round", null);
		game.put("White", null);
		game.put("Black", null);
		game.put("Result", null);
		game.put("WhiteElo", null);
		game.put("BlackElo", null);
		game.put("ECO", null);
	}
	
	/**
	 * Parse game from indicated file
	 * File can store multiples games so we must point direct one.
	 * @param numberOfGameInFile - No. of game from file 
	 * @throws IOException
	 */
	public void parseOneGame(int numberOfGameInFile) throws IOException{
		if (!(PortableGameNotationFiles.gamesMap.size() < 0) && PortableGameNotationFiles.gamesMap.size() > numberOfGameInFile){
			BufferedReader br = new BufferedReader(new FileReader(new File(PortableGameNotationFiles.currentGameFilePath)));
			loadClearGame();
			try {
				int toSkip = PortableGameNotationFiles.gamesMap.get(numberOfGameInFile);
				for(int i=0; i<toSkip; i++)
				{
					br.readLine();
				}
		        String line = br.readLine();
		        do{
		        	String[] splitter = line.split(" ");
		        	String key = splitter[0].substring(1);
		        	for(int i=2; i<splitter.length ; i++)
		        	{
		        		splitter[1] = splitter[1].concat(" "+splitter[i]);
		        	}
		        	String value = splitter[1].substring(1).replace("]", "").replace("\"", "");
		        	if(value != "") {
			        	game.put(key, value);
		        	} else {
		        		game.put(key, null);
		        	}
			        line = br.readLine();
		        }while(!line.equals(""));
		        do{
		        	String[] splitter = line.split(" ");
		        	if(splitter[0].equals("")){
				        line = br.readLine();
		        		continue;
		        	}
		        	for(int i=0; i+1<splitter.length; i+=2){
		        		if(!splitter[i+1].contains(".")){
		        			splitter[i]=splitter[i].concat(" "+splitter[i+1]);
		        			splitter[i+1]=null;
		        		}
		        	}
		        	for(int i=0; i<splitter.length; i++)
		        	{
		        		if(splitter[i]==null){
		        			continue;
		        		}
		        		String[] split = splitter[i].split("\\."); 
		        		if(split.length==2){
			        		if(split[1] != "") {
				        		game.put("Move"+split[0], split[1]);
				        	} else {
				        		game.put("Move"+split[0], null);
				        	}
		        		}
		        	}
			        line = br.readLine();
		        }while(!line.equals(""));
		    } finally {
		        br.close();
		    }
		} else {
			return;
		}
	}
	

	/**
	 * @return Chess Tournament Name
	 */
	public String getEventName(){
		return game.get("Event");
	}
	
	/**
	 * @return Location Name
	 */
	public String getSiteLocation(){
		return game.get("Site");
	}
	
	/**
	 * @return Chess Tournament Date
	 */
	public String getDateOfEvent(){
		return game.get("Date");
	}
	
	/**
	 * @return Round in Chess Tournament
	 */
	public String getRoundNo(){
		return game.get("Round");
	}
	

	/**
	 * @return Player of White Chess
	 */
	public String getWhitePlayer(){
		return game.get("White");
	}
	
	/**
	 * @return Player of Black Chess
	 */
	public String getBlackPlayer(){
		return game.get("Black");
	}

	/**
	 * @return Result of Chess Tournament
	 */
	public String getResultOfTheGame(){
		return game.get("Result");
	}
	/**
	 * @return Elo of player with white chess
	 */
	public String getWhitePlayerElo(){
		return game.get("WhiteElo");
	}
	
	/**
	 * @return Elo of player with black chess
	 */
	public String getBlackPlayerElo(){
		return game.get("BlackElo");
	}
	/**
	 * @return Opening Code of loaded round
	 */
	public String getChessOpeningCode(){
		return game.get("ECO");
	}
	/**
	 * @return get chess move
	 */
	public String getMove(int numberOfMove){
		return game.get("Move"+numberOfMove);
	}



	/**
	 * @param EventName - Name of chess tournament
	 */
	public void setEventName(String EventName){
		game.put("Event", EventName);
	}
	
	/**
	 * @param SiteLocationName - Site Location
	 */
	public void setSiteLocation(String SiteLocationName){
		game.put("Site", SiteLocationName);
	}
	
	/**
	 * @param DateOfEvent
	 */
	public void setDateOfEvent(String DateOfEvent){
		game.put("Date", DateOfEvent);
	}
	
	/**
	 * @param RoundNo - number of round on the event
	 */
	public void setRoundNo(String RoundNo){
		game.put("Round", RoundNo);
	}
	
	/**
	 * @param WhitePlayerName
	 */
	public void setWhitePlayer(String WhitePlayerName){
		game.put("White", WhitePlayerName);
	}
	
	/**
	 * @param BlackPlayerName
	 */
	public void setBlackPlayer(String BlackPlayerName){
		game.put("Black", BlackPlayerName);
	}
	
	/**
	 * @param ResultOfTheGame
	 */
	public void setResultOfTheGame(String ResultOfTheGame){
		game.put("Result", ResultOfTheGame);
	}
	
	/**
	 * @param WhitePlayerElo
	 */
	public void setWhitePlayerElo(String WhitePlayerElo){
		game.put("WhiteElo", WhitePlayerElo);
	}
	
	/**
	 * @param BlackPlayerElo
	 */
	public void setBlackPlayerElo(String BlackPlayerElo){
		game.put("BlackElo", BlackPlayerElo);
	}
	
	/**
	 * @param ChessOpeningCode
	 */
	public void setChessOpeningCode(String ChessOpeningCode){
		game.put("ECO", ChessOpeningCode);
	}
	
	/**
	 * @param move - move in FEN notation
	 */
	public void appendMove(String move){
		game.put("Move"+lastMove, move);
		lastMove++;
	}
	
}
