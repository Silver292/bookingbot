package uk.tlscott.spike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import processing.core.PApplet;
import uk.tlscott.AStarDemo.DemoBoard;
import uk.tlscott.BookingBot.Board;

public class GCostDemo extends PApplet{

	private static final int WORDOFFSET_TOP = 25;
	private static final int WORDOFFSET_BOTTOM = 90;
	private static final int WORDOFFSET_LEFT = 10;
	private static final int WORDOFFSET_RIGHT = 70;
	
	private static final int BOARD_WIDTH = 7;
	private static final int BOARD_HEIGHT = 5;
	private static final int GRID_SQAURE_SIZE = 100;
	private static final int OPENLIST_COLOUR = -16271580;
	private static final int CLOSEDLIST_COLOUR = -15932679;
	private static final int PATH_COLOUR = -3077949;

	ArrayList<GNode> open;
	ArrayList<GNode> closed;
	ArrayList<GNode> path;
	
	private DemoBoard board;
	private Vector playerTile;
	private Vector goal;
	private GNode current;
	private boolean pathFound;
	private boolean complete;
	
	private Comparator<GNode> nodeSorter = new Comparator<GNode>() {
		public int compare(GNode n0, GNode n1) {
			if (n1.fCost < n0.fCost) return 1;
			if (n1.fCost > n0.fCost) return -1;
			// check h values second
			if (n1.hCost < n0.hCost) return 1;
			if (n1.hCost > n0.hCost) return -1;
			return 0;
		}
	};
	
	public static void main(String[] args) {
		PApplet.main("uk.tlscott.spike.GCostDemo");
	}

	public void settings() {
		size(BOARD_WIDTH * GRID_SQAURE_SIZE, BOARD_HEIGHT * GRID_SQAURE_SIZE);
	}
	
	public void setup() {
		frameRate(4);
		strokeWeight(5);
		board = new DemoBoard(BOARD_WIDTH, BOARD_HEIGHT, GRID_SQAURE_SIZE, "...................................................................", this);
		playerTile = new Vector(1, 2);
		goal = new Vector(5, 2);
		board.updateGridSquare(1, 2, Board.PLAYER_ONE);
		board.updateGridSquare(3, 1, Board.WALL);
		board.updateGridSquare(3, 2, Board.WALL);
		board.updateGridSquare(3, 3, Board.WALL);
		board.updateGridSquare(5, 2, Board.GOAL);
		
		open = new ArrayList<GNode>();
		closed = new ArrayList<GNode>();
		path = new ArrayList<GNode>();
		current = new GNode(playerTile, null, 0, getDistance(playerTile, goal));
		
		open.add(current);
	}
	
	public void draw() {
		// draw background
		board.draw();
		
		// draw a single square being put on the openlist
		
		// once openlist is complete draw each sqare being put on the closed list
		
		// repeat
		if(!complete) {
			findPath(playerTile, goal);
		} else {
			drawAllPaths();
			noLoop();
//			if (!pathPointer.tile.equals(playerTile))
//				pathPointer = path.get(path.size() - 1);
		}
		
	}

	/**
	 * 
	 */
	private void drawAllPaths() {
		for (GNode n : open) {
			drawNode(n, OPENLIST_COLOUR);
		}
		
		// drawing
		for (GNode n : closed) {
			drawNode(n, CLOSEDLIST_COLOUR);
		}
		
		for (GNode n : path) {
			drawBox(n.getX(), n.getY(), PATH_COLOUR);
		}
	}
	
	private void findPath(Vector start, Vector goal) {
		
		if (current.tile.equals(goal) || pathFound) {
			
			if(current.parent != null) {
				path.add(current);
				current = current.parent;
				
				drawAllPaths();
			} else {
				// demo vars
				complete = true;
			}
			pathFound = true;
			return;
//				open.clear();
//				closed.clear();
			
		}
		
		if(!open.isEmpty()) {
			Collections.sort(open, nodeSorter);
			current = open.get(0);
			
			
			open.remove(current);
			closed.add(current);
			
//			checkAllSurroundingTiles(goal);
			checkCardinalTiles(goal);
			
			for (GNode n : open) {
				drawNode(n, OPENLIST_COLOUR);
				
			}
			
			// drawing
			for (GNode n : closed) {
				drawNode(n, CLOSEDLIST_COLOUR);
			}
		}
		//closed.clear();
	}

	/**
	 * @param goal
	 */
	@SuppressWarnings("unused")
	private void checkAllSurroundingTiles(Vector goal) {
		// check surrounding tiles
		for (int i = 0; i < 9; i++) {
			if (i == 4) continue;
			int x = current.getX();
			int y = current.getY();
			int xi = (i % 3) - 1;
			int yi = (i / 3) - 1;
			
			char at = board.getCharAt(x + xi, y + yi);
			if (at == ' ') continue;
			if (at == Board.WALL) continue;
			
			Vector a = new Vector(x + xi, y + yi);
			int gCost = current.gCost + getDistance(current.tile, a);
			int hCost = getDistance(a, goal);
			GNode node = new GNode(a, current, gCost, hCost);
			
			if (vecInList(closed, a) && gCost >= node.gCost) continue;
			if (!vecInList(open, a) || gCost < node.gCost) open.add(node);
			
		}
	}
	
	private void checkCardinalTiles(Vector goal) {
		// check surrounding tiles
		for (int i = 0; i < 9; i++) {
			if (i % 2 == 0) continue;
			int x = current.getX();
			int y = current.getY();
			int xi = (i % 3) - 1;
			int yi = (i / 3) - 1;
			
			char at = board.getCharAt(x + xi, y + yi);
			if (at == ' ') continue;
			if (at == Board.WALL) continue;
			
			Vector a = new Vector(x + xi, y + yi);
			int gCost = current.gCost + getDistance(current.tile, a);
			int hCost = getDistance(a, goal);
			GNode node = new GNode(a, current, gCost, hCost);
			
			if (vecInList(closed, a) && gCost >= node.gCost) continue;
			if (!vecInList(open, a) || gCost < node.gCost) open.add(node);
			
		}
	}

	private int getDistance(Vector current, Vector target) {
		return 10*(Math.abs(current.x - target.x) + Math.abs(current.y - target.y));
		
//		int dx = Math.abs(current.x -target.x);
//		int dy = Math.abs(current.y -target.y);
//		if (dx > dy) 
//			return 14 * dy + 10 * (dx -dy);
//		else 
//			return 14 * dx + 10 * (dy - dx);
	}

	private void drawNode(GNode n, int colour) {
		fill(0);
		textSize(18);

		// write fcost
		text(n.fCost, n.getX() * GRID_SQAURE_SIZE + WORDOFFSET_LEFT, n.getY() * GRID_SQAURE_SIZE + WORDOFFSET_TOP);
		// write gcost
		text(n.gCost, n.getX() * GRID_SQAURE_SIZE + WORDOFFSET_LEFT, n.getY() * GRID_SQAURE_SIZE + WORDOFFSET_BOTTOM);
		// write hcost
		text(n.hCost, n.getX() * GRID_SQAURE_SIZE + WORDOFFSET_RIGHT, n.getY() * GRID_SQAURE_SIZE + WORDOFFSET_BOTTOM);
		
		drawBox(n.getX(), n.getY(), colour);		
	}

	private boolean vecInList(ArrayList<GNode> list, Vector vector) {
		for (GNode n : list) {
			if (n.tile.equals(vector)) return true;
		}
		return false;
	}

	/**
	 * @param x
	 * @param y
	 */
	private void drawBox(int x, int y, int colour) {
		noFill();
		stroke(colour);
		rect(x * GRID_SQAURE_SIZE, y * GRID_SQAURE_SIZE, GRID_SQAURE_SIZE, GRID_SQAURE_SIZE);
		stroke(0);
	}
	
}

