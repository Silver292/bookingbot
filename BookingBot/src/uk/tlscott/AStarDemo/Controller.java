package uk.tlscott.AStarDemo;

import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;
import uk.tlscott.BookingBot.Node;
import uk.tlscott.BookingBot.NodeList;
import uk.tlscott.BookingBot.Player;

public class Controller extends PApplet{

	public static int BOARD_WIDTH = 20;
	public static int BOARD_HEIGHT = 14;
	public static int GRID_SQAURE_SIZE = 30;
	public static char WALL = 'x';
	
	public DemoBoard board;
	public Player player;
	
	public static void main(String[] args) {
		PApplet.main("uk.tlscott.AStarDemo.Controller");
	}

	public void settings() {
		size(BOARD_WIDTH * GRID_SQAURE_SIZE, BOARD_HEIGHT * GRID_SQAURE_SIZE);
	}
	
	public void setup() {
		frameRate(3);
		String boardString = "C,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,x,x,x,x,x,.,x,x,x,x,x,x,.,x,x,x,x,x,.,.,x,.,.,.,.,.,x,x,x,x,x,x,.,.,.,.,.,x,.,.,x,.,x,x,x,.,.,.,x,x,.,.,.,x,x,x,.,x,.,.,.,.,.,.,x,x,x,.,x,x,.,x,x,x,.,.,.,.,.,.,x,x,x,.,x,.,.,.,.,.,.,.,.,x,.,x,x,x,.,.,.,.,x,.,x,.,x,x,x,x,x,x,.,x,.,x,.,.,.,x,x,0,x,.,.,.,x,x,x,x,x,x,.,.,.,x,1,x,x,.,.,.,x,x,x,.,x,x,x,x,x,x,.,x,x,x,.,.,.,.,x,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,x,.,.,x,x,x,.,x,x,x,x,x,x,x,x,x,x,.,x,x,x,.,.,x,x,x,.,.,.,.,.,.,.,.,.,.,.,.,x,x,x,.,.,x,x,x,.,x,x,x,.,x,x,.,x,x,x,.,x,x,x,.,.,.,.,.,.,.,.,.,.,x,x,.,.,.,.,.,.,.,.,C";
		board = new DemoBoard(BOARD_WIDTH, BOARD_HEIGHT, GRID_SQAURE_SIZE, boardString, this);
		// find Player once
		player = new Player(board.getNode('1'), '1');
		System.out.println("MAIN METHOD");
	}
	
	public void draw() {
		board.draw();
		NodeList openList = new NodeList();
		NodeList closedList = new NodeList();
		ArrayList<Node> goalList = new ArrayList<Node>();
				
		// add player to open list
		openList.add(player);
		
		goalList = board.getAllNodes('C');		

		// no goals, do nothing
		if(goalList.isEmpty()) return;
		
		// set goals hValues
		for (Node node : goalList) {
			node.sethValue(player);
		}
		
		// get closest goal
		Node closestGoal = Collections.min(goalList); 
		
		// check adjacent squares
		openList.addWalkableNodes(player, closestGoal, closedList, board);
		
		//add starting square to to closed list and remove from openlist
		closedList.add(player);
		openList.remove(player);
		
		
		// Find entire path
		// Continue the search
		findPathToGoal(openList, closedList, closestGoal);

		
		// get path back to player
		ArrayList<Node> path = getBestPath(closedList, closestGoal);
		
		// Only move one step
		board.updateNode(player, '.');
		player.moveTo(path.get(path.size()-1));
		board.updateNode(player, player.getIcon());
	}

	/**
	 * Returns the best path from the goal to the player
	 * @param closedList list of nodes leading from player to goal
	 * @param closestGoal the end goal
	 * @return returns a path of nodes as ArrayList
	 */
	private ArrayList<Node> getBestPath(NodeList closedList, Node closestGoal) {
		ArrayList<Node> path = new ArrayList<Node>();
		Node pathNode = closedList.getLast();
		
		while (!pathNode.equals(player)) {
			path.add(pathNode);
			if(!pathNode.equals(closestGoal))
			board.updateNode(pathNode, '#');
			board.draw();
			pathNode = pathNode.getParent();
		}
		return path;
	}

	/**
	 * Adds nodes to closed list that lead from the first node on closedList
	 * to the goal
	 * @param openList List to contain considered nodes on the way to the goal
	 * @param closedList List to store best nodes on the path
	 * @param closestGoal End point goal
	 */
	private void findPathToGoal(NodeList openList, NodeList closedList, Node closestGoal) {
		while (!openList.isEmpty()) {
			// check goal has been reached 
			if (closedList.getLast().equals(closestGoal)) {
				break;
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
