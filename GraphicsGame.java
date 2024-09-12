import acm.program.*;
import acm.graphics.*;

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

	// TODO declare your instance variables here

	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
	}

	public void run() {
		// TODO write this part, which is like your main function
		String s = null;
		if (s != null && s.length() > 0) s.charAt(0);
	}

	private void drawLevel() {
		// TODO write the code to draw the entire level, which should
		// mostly be calls to some of your helper functions.
	}

	/**
	 * This should draw the label EXIT and add it to the space that represents
	 * the winning tile.
	 */
	private void drawWinningTile() {

	}

	/**
	 * draw the lines of the grid. Test this out and make sure you have it
	 * working first. Should draw the number of grids based on the number of
	 * rows and columsn in Level
	 */
	private void drawGridLines() {

	}

	/**
	 * Maybe given a list of all the cars, you can go through them and call
	 * drawCar on each?
	 */
	private void drawCars() {

	}

	/**
	 * Given a vehicle object, which we will call v, use the information from
	 * that vehicle to then create a GImage and add it to the screen. Make sure
	 * to use the constants for the image path ("/images"), the extension ".png"
	 * and the additional suffix to the filename if the object is vertical when
	 * creating your GImage. Also make sure to set the images size according to
	 * the size of your spaces
	 * 
	 * @param v
	 *            the Vehicle object to be drawn
	 */
	private void drawCar(Vehicle v) {
		// TODO implement drawCar
	}

	// TODO implement the mouse listeners here

	/**
	 * Given a xy coordinates, return the Vehicle that is currently at those x
	 * and y coordinates, returning null if no Vehicle currently sits at those
	 * coordinates.
	 * 
	 * @param x
	 *            the x coordinate in pixels
	 * @param y
	 *            the y coordinate in pixels
	 * @return the Vehicle object that currently sits at that xy location
	 */
	private Vehicle getVehicleFromXY(double x, double y) {
		// TODO fix this implementation
		return null;
	}

	/**
	 * This is a useful helper function to help you calculate the number of
	 * spaces that a vehicle moved while dragging so that you can then send that
	 * information over as numSpacesMoved to that particular Vehicle object.
	 * 
	 * @return the number of spaces that were moved
	 */
	private int calculateNumSpacesMoved() {
		return 0;
	}

	/**
	 * Another helper function/method meant to return the location given an x and y
	 * coordinate system. Use this to help you write getVehicleFromXY
	 * 
	 * @param x
	 *            x-coordinate (in pixels)
	 * @param y
	 *            y-coordinate (in pixels)
	 * @return the Location associated with that x and y
	 */
	private Location convertXYToLocation(double x, double y) {
		// TODO write this implementation hint (use helper methods below)
		return null;
	}

	/**
	 * 
	 * @return the width (in pixels) of a single cell in the grid
	 */
	private double cellWidth() {
		// TODO fix this method
		return 50;
	}

	/**
	 * 
	 * @return the height in pixels of a single cell in the grid
	 */
	private double cellHeight() {
		// TODO fix this method
		return 50;
	}
	
	public static void main(String[] args) {
		new GraphicsGame().start();
	}
}
