package uk.tlscott.BookingBot;

import java.util.ArrayList;

public class StringBoard {

	protected int width;
	protected int height;
	protected String[][] grid;
	public static final char PLAYER_ZERO = '0';
	public static final char PLAYER_ONE = '1';
	public static final char WALL = 'x';
	public static final char FLOOR = '.';
	public static final char SEARCHED = '@';
	public static final char PATH = '#';
	public static final char GOAL = 'C';
	public static final char ENEMY = 'E';

	public StringBoard() {
		super();
	}

	public void updateGrid(String boardString) {
		
		// Remove commas
		String[] boardArray = boardString.split(",");
		
		// String is passed as [row][column]
		int charCount = 0;
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				grid[y][x] = boardArray[charCount++];
			}
		}
	}

	public void updateNode(Node node, String s) {
		grid[node.getY()][node.getX()] = s;
	}

	public void updateGridSquare(int x, int y, String s) {
		grid[y][x] = s;
	}

	public String getCharAt(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return " ";
		return this.grid[y][x];
	}

	public String getCharAt(Node node) {
		return this.grid[node.getY()][node.getX()];
	}

	public Node getNode(String s) {
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x].equals(s)) return new Node(x, y, null);
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
	public NodeList getAllNodes(String s) {
		NodeList goals = new NodeList();
		
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x].equals(s)) goals.add(new Node(x, y, null));
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
	public String[][] getGrid() {
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
		this.grid = new String[this.height][this.width];
	}

	/**
	 * Checks for char c along ArrayList of nodes path
	 * returns true if char is found, false otherwise
	 * @param path ArrayList of nodes
	 * @param c char to look for
	 * @return if char in path
	 */
	public boolean charInPath(ArrayList<Node> path, String s) {
		for (Node node : path) {
			if(this.getCharAt(node).equals(s)) return true;
		}
		return false;
	}

}
