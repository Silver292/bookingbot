package uk.tlscott.BookingBot;

import java.util.ArrayList;
import java.util.Random;

public class Board {

	protected int width;
	protected int height;
	protected char[][] grid;
	public static final char PLAYER_ZERO = '0';
	public static final char PLAYER_ONE = '1';
	public static final char WALL = 'x';
	public static final char FLOOR = '.';
	public static final char SEARCHED = '@';
	public static final char PATH = '#';
	public static final char GOAL = 'C';
	public static final char ENEMY = 'E';

	public Board() {
		super();
	}

	public void updateGrid(String boardString) {
		//TODO: BOARD DOES NOT RECORD TWO ENTITIES ON SAME TILE, THROWS BOARD OFF TO THE RIGHT
		// Now ignores the second entity on the grid square
		
		// Remove commas
		String[] boardArray= boardString.split(",");
		
		// String is passed as [row][column]
		int charCount = 0;
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				grid[y][x] = boardArray[charCount++].charAt(0);
			}
		}
	}

	public void updateNode(Node node, char c) {
		grid[node.getY()][node.getX()] = c;
	}

	public void updateGridSquare(int x, int y, char c) {
		grid[y][x] = c;
	}

	public char getCharAt(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return ' ';
		return this.grid[y][x];
	}

	public char getCharAt(Node node) {
		return this.grid[node.getY()][node.getX()];
	}

	public Node getNode(char c) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x] == c) return new Node(x, y, null);
			}
		}
		return null;
	}

	public ArrayList<Node> getAdjacent(Node node) {
		ArrayList<Node> adjacentNodes = new ArrayList<Node>();
		int x = node.getX();
		int y = node.getY();
		
	    if (x > 0)
	        adjacentNodes.add(new Node(x - 1, y, node));
	    if (x < width - 1)
	        adjacentNodes.add(new Node(x + 1, y, node));
	    if (y > 0)
	        adjacentNodes.add(new Node(x, y - 1, node));
	    if (y < height - 1)
	        adjacentNodes.add(new Node(x, y + 1, node));
		
		return adjacentNodes;
	}

	/**
	 * Returns an array list nodes containing char c on board.
	 * 
	 * @param c  character on the board to find.
	 * @return ArrayList<Node> of nodes containing character {@code c}
	 */
	public NodeList getAllNodes(char c) {
		NodeList goals = new NodeList();
		
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x] == c) goals.add(new Node(x, y, null));
			}
		}
		return goals;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the grid
	 */
	public char[][] getGrid() {
		return grid;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @param grid the grid to set
	 */
	public void setGrid() {
		this.grid = new char[this.height][this.width];
	}

	public void generateGoal() {
		boolean generated = false;
		Random rand = new Random();
		
		while(!generated) {
			int x = rand.nextInt(this.width);
			int y = rand.nextInt(this.height);
		
			if (grid[y][x] != WALL && grid[y][x] != PLAYER_ONE) {
				grid[y][x] = GOAL; 
				generated = true;
			}
		}
	}

	/**
	 * Replaces all instances of charToReplaced with charReplaceWith
	 * in the game board
	 * @param charToReplace
	 * @param charReplaceWith
	 */
	public void replaceAll(char charToReplace, char charReplaceWith) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if(grid[y][x] == charToReplace) grid[y][x] = charReplaceWith;
			}
		}
	}

	/**
	 * Checks for char c along ArrayList of nodes path
	 * returns true if char is found, false otherwise
	 * @param path ArrayList of nodes
	 * @param c char to look for
	 * @return if char in path
	 */
	public boolean charInPath(ArrayList<Node> path, char c) {
		for (Node node : path) {
			if(this.getCharAt(node) == c) return true;
		}
		return false;
	}

}