package uk.tlscott.AStarDemo;
import processing.core.PApplet;
import uk.tlscott.BookingBot.Board;

public class DemoBoard extends Board {
	// Processing fields
	PApplet parent;
	private int gridSqSize;
	
	/**
	 * Creates and empty board to have properties set after initialisation.
	 */
	public DemoBoard () {}
	
	/**
	 * Creates a board for use with the processing image package. 
	 * <br>
	 * This board can be displayed.
	 * 
	 * @param width
	 * @param height
	 * @param gridSize
	 * @param boardString
	 * @param p
	 */
	public DemoBoard(int width, int height, int gridSize, String boardString, PApplet p) {
		this.width = width;
		this.height = height;
		this.parent = p;
		this.gridSqSize = gridSize;
		this.grid = new char[this.height][this.width];
		updateGrid(boardString);
	}
	
	/*
	 * 
	 *  Processing Drawing methods
	 * 
	 */
	
	public void draw() {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				parent.fill(getFill(this.grid[y][x]));
				parent.rect(x*gridSqSize, y*gridSqSize, gridSqSize, gridSqSize);
			}
		}
	}

	private int getFill(char c) {
		switch (c) {
		case FLOOR:
			return parent.color(224,244,244);
		case WALL:
			return parent.color(128,128,128);
		case PLAYER_ZERO:
			return parent.color(255,128,0);
		case PLAYER_ONE:
			return parent.color(0,0,204);
		case GOAL:
			return parent.color(0,204,0);
		case SEARCHED:
			return parent.color(204,153,255);
		case PATH:
			return parent.color(102,255,102);
		case ENEMY:
			return parent.color(204,0,0);
		default:
			return parent.color(224,244,244);
		}
	}
}
