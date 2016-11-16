package uk.tlscott.spike;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import processing.core.PApplet;
import uk.tlscott.AStarDemo.DemoBoard;
import uk.tlscott.BookingBot.Board;

public class GCost extends PApplet{

	
	private static final int BOARD_WIDTH = 7;
	private static final int BOARD_HEIGHT = 5;
	private static final int GRID_SQAURE_SIZE = 100;
	private static final int PATH_COLOUR = -3077949;

	private DemoBoard board;
	private Vector playerTile;
	private Vector goal;
	
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
		PApplet.main("uk.tlscott.spike.GCost");
	}

	public void settings() {
		size(BOARD_WIDTH * GRID_SQAURE_SIZE, BOARD_HEIGHT * GRID_SQAURE_SIZE);
	}
	
	public void setup() {
		frameRate(1);
		strokeWeight(5);
		board = new DemoBoard(BOARD_WIDTH, BOARD_HEIGHT, GRID_SQAURE_SIZE, "...................................................................", this);
		playerTile = new Vector(1, 2);
		goal = new Vector(5, 2);
		board.updateGridSquare(1, 2, Board.PLAYER_ONE);
		board.updateGridSquare(3, 1, Board.WALL);
		board.updateGridSquare(3, 2, Board.WALL);
		board.updateGridSquare(3, 3, Board.WALL);
		board.updateGridSquare(5, 2, Board.GOAL);
	}
	
	public void draw() {
		board.draw();
		
		ArrayList<GNode> path = findPath(playerTile, goal);
		
		if (path != null) {
			for (GNode n : path) {
				drawBox(n.getX(), n.getY(), PATH_COLOUR);
				noLoop();
			}
		}
		
		
	}
	
	private ArrayList<GNode> findPath(Vector start, Vector goal) {
		ArrayList<GNode> open = new ArrayList<GNode>();
		ArrayList<GNode> closed = new ArrayList<GNode>();
		GNode current = new GNode(start, null, 0, getDistance(start, goal));
		
		open.add(current);
		
		while(!open.isEmpty()) {
			Collections.sort(open, nodeSorter);
			current = open.get(0);
			
			if (current.tile.equals(goal)) {
				ArrayList<GNode> path = new ArrayList<GNode>();
				
				while(current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				open.clear();
				closed.clear();
				return path;
			}
			
			open.remove(current);
			closed.add(current);
			
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
			closed.clear();
			return null;
	}

	
	private int getDistance(Vector start, Vector end) {
		return 10*(Math.abs(start.x - end.x) + Math.abs(start.y - end.y));
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
