import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class GraphicsGame extends GraphicsProgram {
	/**
	 * Here are all of the constants
	 */
	public static final int PROGRAM_WIDTH = 500;
	public static final int PROGRAM_HEIGHT = 500;
	public static final String lABEL_FONT = "Arial-Bold-22";
	public static final String EXIT_SIGN = "EXIT";
	public static final String IMG_FILENAME_PATH = "images/";
	public static final String IMG_EXTENSION = ".png";
	public static final String VERTICAL_IMG_FILENAME = "_vert";
	public static final int NUM_ROWS = 6;
	public static final int NUM_COLS = NUM_ROWS;
	public static final Location winLocation = new Location(2, 5);

	private Level level;
	private Vehicle toDrag;
	private GImage toDragImage;
	private int lastX;
	private int lastY;
	private Location lastLocationPressed;
	private Location lastLocationReleased;
	private GRect transparentOverlay;
	private GRect screenOverTheVehicle;
	private GRect screenBelowTheVehicle;
	private int movesCounter;
	private GLabel movesCounterLabel;

	public void init() {
		this.level = new Level(NUM_ROWS, NUM_COLS);
		this.movesCounter = 0;
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
	}

	public void run() {
		drawLevel();
		addMouseListeners();
	}

	private void drawLevel() {
		drawGridLines();
		drawCars();
		drawWinningTile();
		drawMovesCounter();
	}

	private void drawMovesCounter() {
		movesCounterLabel = new GLabel("Moves: " + movesCounter, 10, cellHeight() * 0.5);
		movesCounterLabel.setFont(lABEL_FONT);
		movesCounterLabel.setColor(Color.BLACK);
		add(movesCounterLabel);
	}

	/**
	 * This should draw the label EXIT and add it to the space that represents the
	 * winning tile.
	 */
	private void drawWinningTile() {
		double x = (cellWidth() * 5) + (cellWidth() / 4);
		double y = (cellHeight() * 1.5) + (cellHeight() / 10);
		final GLabel exitTitle = new GLabel(EXIT_SIGN, x, y);
		exitTitle.setFont(lABEL_FONT);
		exitTitle.setColor(Color.RED);
		add(exitTitle);
	}

	/**
	 * draw the lines of the grid. Test this out and make sure you have it working
	 * first. Should draw the number of grids based on the number of rows and
	 * columsn in Level
	 */
	private void drawGridLines() {
		// Creating lines dividing screen per cells and store them in the array
		final GLine[] linesDividingCells = new GLine[(NUM_ROWS - 1) + (NUM_COLS - 1)];
		for (int i = 1; i < NUM_COLS; i++) {
			final double x = i * cellWidth();
			linesDividingCells[i - 1] = new GLine(x, 0, x, PROGRAM_HEIGHT);
		}
		for (int i = 1; i < NUM_ROWS; i++) {
			final double y = i * cellHeight();
			linesDividingCells[NUM_COLS - 1 + i - 1] = new GLine(0, y, PROGRAM_WIDTH, y);
		}
		// Drawing these lines
		for (GLine line : linesDividingCells) {
			add(line);
		}
	}

	/**
	 * Maybe given a list of all the cars, you can go through them and call drawCar
	 * on each?
	 */
	private void drawCars() {
		for (Vehicle vehicle : level.getBoard().getVehiclesOnBoard()) {
			drawCar(vehicle);
		}

		// drawCar(new Vehicle(VehicleType.AUTO, 0, 0, false, 2));
	}

	/**
	 * Given a vehicle object, which we will call v, use the information from that
	 * vehicle to then create a GImage and add it to the screen. Make sure to use
	 * the constants for the image path ("/images"), the extension ".png" and the
	 * additional suffix to the filename if the object is vertical when creating
	 * your GImage. Also make sure to set the images size according to the size of
	 * your spaces
	 * 
	 * @param v the Vehicle object to be drawn
	 */
	private void drawCar(Vehicle v) {
		String picture = IMG_FILENAME_PATH;

		switch (v.getVehicleType()) {
			case AUTO:
				picture += "auto";
				break;
			case MYCAR:
				picture += "car";
				break;
			case TRUCK:
				picture += "truck";
				break;
			default:
				break;
		}

		if (v.getIsVertical())
			picture += VERTICAL_IMG_FILENAME;

		picture += IMG_EXTENSION;

		double[] coordinates = convertLocationToXY(v.locationsOn()[0]);

		double row_px = coordinates[0];
		double col_px = coordinates[1];

		GImage vehicle = new GImage(picture, row_px, col_px);

		double lengthMultiplicator = v.getVehicleType() == VehicleType.TRUCK ? 3 : 2;

		double carWidth = cellWidth();
		double carHeight = cellHeight();

		if (v.getIsVertical())
			carHeight *= lengthMultiplicator;
		else
			carWidth *= lengthMultiplicator;

		vehicle.setSize(carWidth, carHeight);

		add(vehicle);
	}

	private void drawWinScreen() {
		getGCanvas().removeMouseListener(this);
		System.out.println("\nCongrats! You won this level!");
		final GRect winBgOverlay = new GRect(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		winBgOverlay.setFilled(true);
		winBgOverlay.setFillColor(new Color(144, 238, 144, 127));

		final GLabel winningTitle = new GLabel("You won!🥳🤩🎂🎉", PROGRAM_WIDTH / 3, PROGRAM_HEIGHT / 2);
		winningTitle.setFont(lABEL_FONT);
		winningTitle.setColor(Color.BLACK);

		add(winBgOverlay);
		add(winningTitle);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		lastLocationPressed = convertXYToLocation(e.getX(), e.getY());
		toDrag = getVehicleFromXY(e.getX(), e.getY());
		System.out.println(toDrag);
		if (toDrag != null) {
			toDragImage = (GImage) getElementAt(e.getX(), e.getY());
			if (toDragImage != null) {
				lastX = e.getX();
				lastY = e.getY();
				// Making the image half-transparent while dragging;
				transparentOverlay = new GRect(toDragImage.getWidth(), toDragImage.getHeight());
				transparentOverlay.setFilled(true);
				transparentOverlay.setFillColor(new Color(255, 255, 255, 127));
				add(transparentOverlay, toDragImage.getX(), toDragImage.getY());

				// Drawing half transparent overlay over the cells
				// which are prohibited to put the vehicle on
				double[] pressedCellStartingCoordinates = convertLocationToXY(lastLocationPressed);
				if (toDrag.getIsVertical()) {
					screenOverTheVehicle = new GRect(0, 0, pressedCellStartingCoordinates[0], PROGRAM_HEIGHT);
					screenBelowTheVehicle = new GRect(pressedCellStartingCoordinates[0] + cellWidth(), 0, PROGRAM_WIDTH,
							PROGRAM_HEIGHT);
				} else {
					screenOverTheVehicle = new GRect(0, 0, PROGRAM_WIDTH, pressedCellStartingCoordinates[1]);
					screenBelowTheVehicle = new GRect(0, pressedCellStartingCoordinates[1] + cellHeight(),
							PROGRAM_WIDTH, 1000);
				}

				Color inactiveCellsColor = new Color(169, 169, 169, 127);
				screenOverTheVehicle.setFilled(true);
				screenOverTheVehicle.setFillColor(inactiveCellsColor);
				screenBelowTheVehicle.setFilled(true);
				screenBelowTheVehicle.setFillColor(inactiveCellsColor);

				add(screenOverTheVehicle);
				add(screenBelowTheVehicle);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (toDragImage != null) {
			toDragImage.move(e.getX() - lastX, e.getY() - lastY);
			lastX = e.getX();
			lastY = e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (transparentOverlay != null)
			remove(transparentOverlay);
		if (screenOverTheVehicle != null && screenBelowTheVehicle != null) {
			remove(screenOverTheVehicle);
			remove(screenBelowTheVehicle);
		}
		lastLocationReleased = convertXYToLocation(e.getX(), e.getY());
		System.out.println("The result from calculateNumSpacesMoved: " + calculateNumSpacesMoved());
		System.out.println("Verifying that it was in fact the right car clicked: " + toDrag + "\n");

		if (toDragImage != null) {
			level.getBoard().moveVehicleAt(lastLocationPressed, calculateNumSpacesMoved());

			remove(toDragImage);
			drawCar(toDrag);

			final boolean moveSucceded = level.getBoard().getVehicleAt(lastLocationReleased) == toDrag;
			if (moveSucceded) {
				movesCounter++;
				movesCounterLabel.setLabel("Moves: " + movesCounter);
			}
			toDragImage = null;
			toDrag = null;
			lastX = 0;
			lastY = 0;
		}

		if (isWin())
			drawWinScreen();
	}

	/**
	 * Given a xy coordinates, return the Vehicle that is currently at those x and y
	 * coordinates, returning null if no Vehicle currently sits at those
	 * coordinates.
	 * 
	 * @param x the x coordinate in pixels
	 * @param y the y coordinate in pixels
	 * @return the Vehicle object that currently sits at that xy location
	 */
	private Vehicle getVehicleFromXY(double x, double y) {
		return level.getBoard().getVehicleAt(convertXYToLocation(x, y));
	}

	/**
	 * This is a useful helper function to help you calculate the number of spaces
	 * that a vehicle moved while dragging so that you can then send that
	 * information over as numSpacesMoved to that particular Vehicle object.
	 * 
	 * @return the number of spaces that were moved
	 */
	private int calculateNumSpacesMoved() {
		return (toDrag == null) ? 0
				: toDrag.getIsVertical() ? lastLocationReleased.getRow() - lastLocationPressed.getRow()
						: lastLocationReleased.getCol() - lastLocationPressed.getCol();

	}

	/**
	 * Another helper function/method meant to return the location given an x and y
	 * coordinate system. Use this to help you write getVehicleFromXY
	 * 
	 * @param x x-coordinate (in pixels)
	 * @param y y-coordinate (in pixels)
	 * @return the Location associated with that x and y
	 */
	private Location convertXYToLocation(double x, double y) {
		return new Location((int) (y / cellHeight()), (int) (x / cellWidth()));
	}

	private double[] convertLocationToXY(Location l) {
		return new double[] { l.getCol() * cellWidth(), l.getRow() * cellHeight() };
	}

	public boolean isWin() {
		return level.getBoard().getVehicleAt(level.getWinLocation()) != null;
	}

	/**
	 * 
	 * @return the width (in pixels) of a single cell in the grid
	 */
	private double cellWidth() {
		return PROGRAM_WIDTH / level.getColumns();
	}

	/**
	 * 
	 * @return the height in pixels of a single cell in the grid
	 */
	private double cellHeight() {
		return PROGRAM_HEIGHT / level.getRows();
	}

	public static void main(String[] args) {
		new GraphicsGame().start();
	}
}
