import java.util.*;

/**
 * This represents the board. Really what it is going to do is just have a 2d
 * array of the vehicles (which we'll refer to as grid), and it will be in
 * charge of doing the bounds type checking for doing any of the moves. It will
 * also have a method display board which will give back a string representation
 * of the board
 * 
 * @author Osvaldo
 *
 */

public class Board {
	Vehicle[][] grid;
	private int numRows;
	private int numCols;

	/**
	 * Constructor for the board which sets up an empty grid of size rows by columns
	 * Use the first array index as the rows and the second index as the columns
	 * 
	 * @param rows number of rows on the board
	 * @param cols number of columns on the board
	 */
	public Board(int rows, int cols) {
		this.grid = new Vehicle[rows][cols];
		this.numRows = rows;
		this.numCols = cols;
	}

	/**
	 * @return number of columns the board has
	 */
	public int getNumCols() {
		return this.numCols;
	}

	/**
	 * @return number of rows the board has
	 */
	public int getNumRows() {
		return this.numRows;
	}

	/**
	 * Grabs the vehicle present on a particular space if any is there If a Vehicle
	 * occupies three spaces, the same Vehicle pointer should be returned for all
	 * three spaces
	 * 
	 * @param s the desired space where you want to look to see if a vehicle is
	 *          there
	 * @return a pointer to the Vehicle object present on that space, if no Vehicle
	 *         is present, null is returned
	 */
	public Vehicle getVehicleAt(Location s) {
		if (isInBounds(s)) {
			Vehicle vehicleAt = this.grid[s.getRow()][s.getCol()];
			return vehicleAt;
		}
		return null;
	}

	/**
	 * adds a vehicle to the board. It would be good to do some checks for a legal
	 * placement here.
	 * 
	 * @param type     type of the vehicle
	 * @param startRow row for location of vehicle's top
	 * @param startCol column for for location of vehicle leftmost space
	 * @param vert     true if the vehicle should be vertical
	 * @param length   number of spaces the vehicle occupies on the board
	 */
	public void addVehicle(VehicleType type, int startRow, int startCol, boolean vert, int length) {
		Vehicle vehicle = new Vehicle(type, startRow, startCol, vert, length);

		if (Arrays.stream(vehicle.locationsOn()).allMatch(this::isValidPlacement)) {
			for (Location currCell : vehicle.locationsOn())
				this.grid[currCell.getRow()][currCell.getCol()] = vehicle;
		} else {
			new Warning(
					"One or more of the vehicle's expected cells to be placed on were occupied or are out of bonds for the current board, so the vehicle wasn't added.")
					.print();
		}
	}

	private boolean isValidPlacement(Location location) {
		return isInBounds(location) && getVehicleAt(location) == null;
	}

	/**
	 * This method moves the vehicle at a certain location a specific number of
	 * spaces and updates the board's grid to reflect it
	 * 
	 * @param start     the starting location of the vehicle in question
	 * @param numSpaces the number of spaces to be moved by the vehicle (can be
	 *                  positive or negative)
	 * @return whether or not the move actually happened
	 */
	public boolean moveVehicleAt(Location start, int numSpaces) {
		Vehicle vehicle = getVehicleAt(start);

		if (canMoveAVehicleAt(start, numSpaces)) {

			// Clear the vehicle's current locations
			for (Location locationOn : vehicle.locationsOn())
				this.grid[locationOn.getRow()][locationOn.getCol()] = null;

			vehicle.move(numSpaces);

			// Place the vehicle on the new locations
			for (Location locationOn : vehicle.locationsOn())
				this.grid[locationOn.getRow()][locationOn.getCol()] = vehicle;

			return true;
		}

		return false;
	}

	/**
	 * This method just checks to see if the vehicle on a certain location can move
	 * a specific number of spaces, though it will not move the vehicle. You should
	 * use this when you wish to move or want to see if you can move a vehicle
	 * numSpaces without going out of bounds or hitting another vehicle
	 * 
	 * @param start     the starting row/column of the vehicle in question
	 * @param numSpaces number of spaces to be moved by the vehicle (positive or
	 *                  negative)
	 * @return whether or not the move is possible
	 */

	public boolean canMoveAVehicleAt(Location start, int numSpaces) {
		Vehicle vehicle = getVehicleAt(start);
		if (vehicle == null) {
			new Warning("There is no vehicle on the stated location.").print();
			return false;
		}
		Location[] locationsTravelled = vehicle.locationsPath(numSpaces);

		for (Location locationTravelled : locationsTravelled) {
			if (/* checking if the location is occupied */ getVehicleAt(locationTravelled) != null) {
				new Warning("There is a vehicle on the way (r-" + locationTravelled.getRow() + "-c-"
						+ locationTravelled.getCol() + "), can't move your " + vehicle.getVehicleType()
						+ " to the final location.").print();
				return false;
			}
			if (!isInBounds(locationTravelled)) {
				new Warning("The location r-" + locationTravelled.getRow() + "-c-" + locationTravelled.getCol()
						+ " is out of bounds, can't move your " + vehicle.getVehicleType() + " through it.").print();
				return false;
			}
		}

		return true;
	}

	private boolean isInBounds(Location location) {
		boolean inBounds = location.getCol() < this.numCols && location.getRow() < this.numRows
				&& location.getCol() >= 0 && location.getRow() >= 0;
		if (!inBounds) {
			new Warning("Requested location r-" + location.getRow() + "-c-" + location.getCol()
					+ " is out of bounds for a current game map.").print();
		}
		return inBounds;
	}

	// This method helps create a string version of the board
	// You do not need to call this at all, just let it be
	public String toString() {
		return BoardConverter.createString(this);
	}

	/*
	 * Testing methods down here for testing the board make sure you run the board
	 * and it works before you write the rest of the program!
	 */

	public static void main(String[] args) {
		System.out.println("▶️ STARTING TESTS for 'Board.java' ▶️\n");
		Board b = new Board(5, 5);
		b.addVehicle(VehicleType.MYCAR, 1, 0, false, 2);
		b.addVehicle(VehicleType.TRUCK, 0, 2, true, 3);
		b.addVehicle(VehicleType.AUTO, 3, 3, true, 2);
		b.addVehicle(VehicleType.AUTO, 0, 3, true, 2);
		System.out.println(b);

		testCanMove(b);
		testMoving(b);
		System.out.println(b);
		testGetVehicleAt(b);
		testAddVehicle(b);

		System.out.println("\n✅ TESTS COMPLETED for 'Board.java' ✅");
	}

	public static void testMoving(Board b) {
		System.out.println("just moving some stuff around");
		b.moveVehicleAt(new Location(1, 2), 1);
		b.moveVehicleAt(new Location(1, 2), 1);
		b.moveVehicleAt(new Location(1, 1), 1);
	}

	public static void testCanMove(Board b) {
		System.out.println("Ok, now testing some moves...");
		System.out.println("These should all be true");
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(0, 2), 2));
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(1, 2), 2));
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(2, 2), 2));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(3, 3), -1));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(4, 3), -1));

		System.out.println("\nAnd these should all be false");
		System.out.println("Moving truck down " + b.canMoveAVehicleAt(new Location(3, 2), 2));
		System.out.println("Moving the car into truck " + b.canMoveAVehicleAt(new Location(1, 0), 1));
		System.out.println("Moving the car into truck " + b.canMoveAVehicleAt(new Location(1, 0), 2));
		System.out.println("Moving nothing at all " + b.canMoveAVehicleAt(new Location(4, 4), -1));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(3, 3), -2));
		System.out.println("Moving lower auto up " + b.canMoveAVehicleAt(new Location(4, 3), -2));
		System.out.println("Moving upper auto up " + b.canMoveAVehicleAt(new Location(0, 3), -1));
		System.out.println("Moving upper auto up " + b.canMoveAVehicleAt(new Location(1, 3), -1));

	}

	public static void testGetVehicleAt(Board b) {
		System.out.println(
				"Ensuring the same reference will be returned for 2 different locations where the same vehicle stands -> r-1-c-1: "
						+ b.getVehicleAt(new Location(1, 1)) + " & r-1-c-2: " + b.getVehicleAt(new Location(1, 2)));
		System.out.println("Ensuring the method will return null for an empty cell -> r-0-c-0: "
				+ b.getVehicleAt(new Location(0, 0)));
	}

	public static void testAddVehicle(Board b) {
		System.out.println(
				"\nEnsuring that it is impossible to place a new vehicle on the cell which is already occupied:");
		b.addVehicle(VehicleType.AUTO, 0, 3, true, 3);
	}

}
