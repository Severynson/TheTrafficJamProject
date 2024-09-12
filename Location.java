/**
 * Simple class that represents a row and a column, with simple getters and
 * setters for both
 * 
 * @author Osvaldo
 */

public class Location {
	// TODO Put your instance variables here
	private int row;
	private int col;

	/**
	 * The constructor that will set up the object to store a row and column
	 * 
	 * @param row
	 * @param col
	 */
	public Location(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public void setRow(int row) {
		if (row > -1)
			this.row = row;
		else
			System.out.println("Error: row number can't be negative.");
	}

	public int getRow() {
		return row;
	}

	public void setCol(int col) {
		if (col > -1)
			this.col = col;
		else
			System.out.println("Error: column number can't be negative.");
	}

	public int getCol() {
		return col;
	}

	@Override
	public String toString() {
		return "r-" + getRow() + "-c-" + getCol();
	}

	// Small test code to put in Location.java to check if your class works
	public static void main(String[] args) {
		Location one = new Location(3, 4);
		Location two = new Location(1, 6);
		two.setRow(two.getRow() + 1);
		two.setCol(two.getCol() - 1);
		System.out.println("one r: " + one.getRow() + ", c: " + one.getCol());
		System.out.println("two r: " + two.getRow() + ", c: " + two.getCol());
	}
}
