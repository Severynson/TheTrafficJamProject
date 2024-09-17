import java.util.*;

public class Level {
	private Board board;
	private Location winLocation;
	private int numMoves;

	// TODO fill out this class with a Level constructor
	// all the other methods necessary and any other instance variables needed
	public Level(int nRows, int nCols) {
		this.board = new Board(nRows, nCols);
		this.winLocation = new Location(1, 5); // TODO: hard-coded location for this lab, change it later;
		this.numMoves = 0;
		
		this.board.addVehicle(VehicleType.MYCAR, 1, 0, false, 2);
		this.board.addVehicle(VehicleType.TRUCK, 0, 2, true, 3);
		this.board.addVehicle(VehicleType.AUTO, 3, 3, true, 2);
		this.board.addVehicle(VehicleType.AUTO, 0, 3, true, 2);
	}

	/**
	 * This method increments numMoves per 1;
	 */
	public void incrementNumMoves() {
		this.numMoves++;
	}

	/**
	 * @return the win location for this level
	 */
	public Location getWinLocation() {
		return this.winLocation;
	}

	/**
	 * @return the board of this level
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * @return the number of moves made in this level
	 */
	public int getNumMoves() {
		return this.numMoves;
	}

	/**
	 * @return the number of columns on the board
	 */
	public int getColumns() {
		return this.board.getNumCols();
	}

	/**
	 * @return the number of rows on the board
	 */
	public int getRows() {
		return this.board.getNumRows();
	}

	// Methods already defined for you
	/**
	 * generates the string representation of the level, including the row and
	 * column headers to make it look like a table
	 * 
	 * @return the string representation
	 */
	public String toString() {
		String result = generateColHeader(getColumns());
		result += addRowHeader(board.toString());
		return result;
	}

	/**
	 * This method will add the row information needed to the board and is used by
	 * the toString method
	 * 
	 * @param origBoard the original board without the header information
	 * @return the board with the header information
	 */
	private String addRowHeader(String origBoard) {
		String result = "";
		String[] elems = origBoard.split("\n");
		for (int i = 0; i < elems.length; i++) {
			result += (char) ('A' + i) + "|" + elems[i] + "\n";
		}
		return result;
	}

	/**
	 * This one is responsible for making the row of column numbers at the top and
	 * is used by the toString method
	 * 
	 * @param cols the number of columns in the board
	 * @return if the # of columns is five then it would return "12345\n-----\n"
	 */
	private String generateColHeader(int cols) {
		String result = "  ";
		for (int i = 1; i <= cols; i++) {
			result += i;
		}
		result += "\n  ";
		for (int i = 0; i < cols; i++) {
			result += "-";
		}
		result += "\n";
		return result;
	}
}
