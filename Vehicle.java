public class Vehicle {
	private VehicleType vehicleType;
	private Location startingLocation;
	private boolean isVertical;
	private int length;

	public Vehicle(VehicleType vehicleType, int startRow, int startCol, boolean isVertical, int length) {
		this.vehicleType = vehicleType;
		this.startingLocation = new Location(startRow, startCol);
		this.isVertical = isVertical;
		this.length = length;
	}

	public VehicleType getVehicleType() {
		return this.vehicleType;
	}

	public Location[] locationsOn() {
		Location[] vehicleLocations = new Location[this.length];
		for (int i = 0; i < this.length; i++) {
			if (isVertical)
				vehicleLocations[i] = new Location(this.startingLocation.getRow() + i, this.startingLocation.getCol());
			else
				vehicleLocations[i] = new Location(this.startingLocation.getRow(), this.startingLocation.getCol() + i);
		}
		return vehicleLocations;
	}

	public Location[] locationsPath(int numSpaces) {

		final int TRAVELLED_LOCATIONS_LENGTH = Math.abs(numSpaces);
		Location[] travelledLocations = new Location[TRAVELLED_LOCATIONS_LENGTH];
		boolean isNumSpacesPositive = numSpaces > 0;

		for (int i = 1; i <= TRAVELLED_LOCATIONS_LENGTH; i++) {
			int curRow = this.startingLocation.getRow();
			int curCol = this.startingLocation.getCol();

			if (isVertical)
				curRow += (isNumSpacesPositive ? ((this.length - 1) + i) : -i);
			else
				curCol += (isNumSpacesPositive ? ((this.length - 1) + i) : -i);

			travelledLocations[i - 1] = new Location(curRow, curCol);
		}
		if (!isNumSpacesPositive)
			java.util.Collections.reverse(java.util.Arrays.asList(travelledLocations));

		return travelledLocations;
	}

	public void move(int numSpace) {
		this.startingLocation = this.potentialMove(numSpace);
	}

	public Location potentialMove(int numSpace) {
		if (isVertical)
			return new Location(this.startingLocation.getRow() + numSpace, this.startingLocation.getCol());
		else
			return new Location(this.startingLocation.getRow(), this.startingLocation.getCol() + numSpace);
	}

	public static void printLocations(Location[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print("r" + arr[i].getRow() + "c" + arr[i].getCol() + "; ");
		}
		System.out.println();
	}

	public int getLength() {
		return this.length;
	}

	public boolean getIsVertical() {
		return this.isVertical;
	}

	public static void main(String[] args) {
		// Assume Vehicle constructor is type, startRow, startCol, isVertical, length
		Vehicle someTruck = new Vehicle(VehicleType.TRUCK, 1, 1, true, 3);
		Vehicle someAuto = new Vehicle(VehicleType.AUTO, 2, 2, false, 2);
		Vehicle oneMoreTruck = new Vehicle(VehicleType.TRUCK, 3, 3, true, 4);
		System.out.println("This next test is for locationsOn: ");
		System.out.println(
				"vert truck at r1c1 should give you r1c1; r2c1; r3c1 as the locations its on top of... does it?");
		printLocations(someTruck.locationsOn());
		System.out.println("horiz auto at r2c2 should give you r2c2; r2c3 as the locations its on top of... does it?");
		printLocations(someAuto.locationsOn());
		System.out.println("if we were to move horiz auto -2 it should give you at least r2c0; r2c1... does it?");
		printLocations(someAuto.locationsPath(-2));
		// Ensuring that positive movement also works correctly, had some issues with
		// this logic initially.
		System.out.println(
				"if we were to move vertical truck +5 it should give you r7c3; r8c3; r9c3; r10c3; r11c3... does it?");
		printLocations(oneMoreTruck.locationsPath(5));
	}

}
