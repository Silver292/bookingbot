package uk.tlscott.AStarDemo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import processing.core.PApplet;
import uk.tlscott.BookingBot.Board;
import uk.tlscott.BookingBot.Node;
import uk.tlscott.BookingBot.NodeList;
import uk.tlscott.BookingBot.Player;

public class DebugController extends PApplet{

	public static int BOARD_WIDTH = 20;
	public static int BOARD_HEIGHT = 14;
	public static int GRID_SQAURE_SIZE = 30;
	public static char WALL = 'x';
	
	public DemoBoard board;
	public Player player;
	public Player enemy;
	Random rand;
	
	public Node closestGoal = null;
	
	public boolean pathSearched = false;
	public boolean routeFound = false;
	
	NodeList openList = new NodeList();
	NodeList closedList = new NodeList();
	ArrayList<Node> goalList = new ArrayList<Node>();
	ArrayList<Node> path = new ArrayList<Node>();
	Node pathNode = null;
	
	public static void main(String[] args) {
		PApplet.main("uk.tlscott.AStarDemo.DebugController");
	}

	public void settings() {
		size(BOARD_WIDTH * GRID_SQAURE_SIZE, BOARD_HEIGHT * GRID_SQAURE_SIZE);
	}
	
	public void setup() {
		frameRate(20);
		String boardString = "C,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,E,.,.,.,x,x,x,x,x,.,x,x,x,x,x,x,.,x,x,x,x,x,.,.,x,.,.,.,.,.,x,x,x,x,x,x,.,.,.,.,.,x,.,.,x,.,x,x,x,.,.,.,x,x,.,.,.,x,x,x,.,x,.,.,.,.,.,.,x,x,x,.,x,x,.,x,x,x,.,.,.,.,.,.,x,x,x,.,x,.,.,.,.,.,.,.,.,x,.,x,x,x,.,.,.,.,x,.,x,.,x,x,x,x,x,x,.,x,.,x,.,.,.,x,x,0,x,.,.,.,x,x,x,x,x,x,.,.,.,x,1,x,x,.,.,.,x,x,x,.,x,x,x,x,x,x,.,x,x,x,.,.,.,.,x,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,x,.,.,x,x,x,.,x,x,x,x,x,x,x,x,x,x,.,x,x,x,.,.,x,x,x,.,.,.,.,.,.,.,.,.,.,.,.,x,x,x,.,.,x,x,x,.,x,x,x,.,x,x,.,x,x,x,.,x,x,x,.,.,.,.,.,.,.,.,.,.,x,x,.,.,.,.,.,.,.,.,C";
		board = new DemoBoard(BOARD_WIDTH, BOARD_HEIGHT, GRID_SQAURE_SIZE, boardString, this);
		// find Player once
		player = new Player(board.getNode(Board.PLAYER_ONE), Board.PLAYER_ONE);
		enemy = new Player(board.getNode(Board.ENEMY), Board.ENEMY);
		
		System.out.println("DEBUG METHOD");
		rand = new Random();
	}
	
	public void draw() {
		board.draw();
		
		// generate goal every x frames
		if (frameCount % 240 == 0) board.generateGoal();
		
		// if we don't have a goal get one
		
		if(closestGoal == null) {
			openList = new NodeList();
			closedList = new NodeList();
			goalList = new ArrayList<Node>();
			
			// add player to open list
			openList.add(player);
		
			goalList = board.getAllNodes(Board.GOAL);		
	
			// no goals, do nothing
			if(goalList.isEmpty()) return;
		
			// set goals hValues
			for (Node node : goalList) {
				node.sethValue(player);
			}
		
			// get closest goal
			closestGoal = Collections.min(goalList); 
			
			// check adjacent squares
			openList.addWalkableNodes(player, closestGoal, closedList, board);
			
			//add starting square to to closed list and remove from openlist
			closedList.add(player);
			openList.remove(player);
		}
			
		// Find entire path
		if (!pathSearched) {
			// Continue the search
			findPathToGoal(openList, closedList, closestGoal);
		}
		
		if (pathSearched && !routeFound) {
			if(pathNode == null) {
				pathNode = closedList.getLast();
				path.add(pathNode);
			}
			// get path back to player
			path.add(getBestPath(closedList, closestGoal));
		}
		
		if (routeFound) {
			movePlayer();
			
			// move enemy
			moveEnemy();
		}
	}

	private void moveEnemy() {
		ArrayList<Node> nodes = board.getAdjacent(enemy);
		Iterator<Node> iter = nodes.iterator();
		
		while (iter.hasNext()) {
			char charAtNode = board.getCharAt(iter.next());
			if (charAtNode == Board.WALL || charAtNode == Board.ENEMY)
				iter.remove();
		}
		
		board.updateNode(enemy, '.');
		enemy.moveTo(nodes.get(new Random().nextInt(nodes.size())));
		board.updateNode(enemy, enemy.getIcon());
	}
	
	/**
	 * 
	 */
	private void movePlayer() {
		// remove player from path
		while(path.contains(player)) path.remove(player);
		
		// Check for enemy on path
		if(board.charInPath(path, Board.ENEMY)) {
			resetSearch();
			board.replaceAll(Board.PATH, Board.FLOOR);
			return;
		}
		
		//Check goal still exists
		if(board.getCharAt(closestGoal)!= Board.GOAL){
			resetSearch();
			board.generateGoal();
			return;
		}
		
		// if the path has ended reset
		if(path.size() <= 0){
			resetSearch();
			board.generateGoal();
			return;
		}
		Node lastPath = path.get(path.size()-1);
		
		// Only move one step
		board.updateNode(player, '.');
		player.moveTo(lastPath);
		board.updateNode(player, player.getIcon());
		
		path.remove(lastPath);
	}

	/**
	 * 
	 */
	private void resetSearch() {
		closestGoal = null;
		pathNode = null;
		routeFound = false;
		pathSearched = false;
		path = new NodeList();
		board.replaceAll(Board.SEARCHED, Board.FLOOR);
	}

	/**
	 * Returns the best path from the goal to the player
	 * @param closedList list of nodes leading from player to goal
	 * @param closestGoal the end goal
	 * @return returns a path of nodes as ArrayList
	 */
	private Node getBestPath(NodeList closedList, Node closestGoal) {
		if (pathNode.equals(player)) {
			routeFound = true;
			return pathNode;
		}
		
		if(!pathNode.equals(closestGoal))
			board.updateNode(pathNode, '#');
		board.draw();
		pathNode = pathNode.getParent();
		
		return pathNode;
	}

	/**
	 * Adds nodes to closed list that lead from the first node on closedList
	 * to the goal
	 * @param openList List to contain considered nodes on the way to the goal
	 * @param closedList List to store best nodes on the path
	 * @param closestGoal end point / goal
	 */
	private void findPathToGoal(NodeList openList, NodeList closedList, Node closestGoal) {
		if (!openList.isEmpty()) {
			// check goal has been reached
			if (closedList.getLast().equals(closestGoal)) {
				pathSearched = true;
				return;
			}
			
			// lowest hValue
			Node lowestHValueNode = Collections.min(openList);
			closedList.add(lowestHValueNode);
			openList.remove(lowestHValueNode);
				
			// check adjacent squares
			openList.addWalkableNodes(closedList.getLast(), closestGoal, closedList, board);
			
			// update board
			if(!closedList.getLast().equals(closestGoal))
				board.updateNode(closedList.getLast(), '@');
			board.draw();
		}
	}
}
