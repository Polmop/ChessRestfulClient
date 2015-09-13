package pgnFileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PortableGameNotationFiles {

	static String currentGameFilePath = new ChessFileLocator().getChessFile();

	private static PortableGameNotationFiles instance = null;
	protected static ArrayList<Integer> gamesMap = new ArrayList<>();
	
	public static PortableGameNotationFiles getInstance() {
	      if(instance == null) {
	         instance = new PortableGameNotationFiles();
	      }
	      return instance;	
	}
	
	/**
	 * Load all game from indicated file and keeps those in map <String NumberOfGame, int LineNumber>
	 * @throws IOException
	 */
	public static void loadAllGames() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(new File(currentGameFilePath)));
	    try {
	        String line = br.readLine();
	        int lineNumber=0;
	        while (line != null) {
	        	if(line.contains("Event")){
	        		gamesMap.add(lineNumber);
	        	}
	            line = br.readLine();
	            lineNumber++;
	        }
	    } finally {
	        br.close();
	    }
	}
	
}
