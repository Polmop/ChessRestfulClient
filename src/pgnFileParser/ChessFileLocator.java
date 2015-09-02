package pgnFileParser;

public final class ChessFileLocator {

	
	/**
	 * Locations of chess files
	 */
	private String chessFileLocation="C:/Users/Alex/Documents/workspace_mgr/MasterThesis/resources/";
	
	/**
	 * "working" chess file
	 */
	private String chessFile="Adams.pgn";
	
	
	/**
	 * @return path to "working" chess file
	 */
	public String getChessFile() {
		return chessFileLocation+chessFile;
	}

	/**
	 * Sets location of chess files
	 * @param chessFileLocation 
	 */
	public void setChessFileLocation(String chessFileLocation) {
		this.chessFileLocation = chessFileLocation;
	}
	
	/**
	 * Sets name of actually working file
	 * @param chessFile
	 */
	public void setChessFile(String chessFile) {
		this.chessFile = chessFile;
	}
	
}
