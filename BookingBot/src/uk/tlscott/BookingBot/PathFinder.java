package uk.tlscott.BookingBot;

import java.util.Collections;

public class PathFinder {
	private Board board = null;
	private Node goal = null;
	private NodeList openList = new NodeList();
	private NodeList closedList = new NodeList();
	private NodeList path = new NodeList();
	private Node currentGoal;
	private boolean goalOnPathIsClosest = true; // first goal found will be closest
	
	public PathFinder(Board board) {
		this.board = board;
	}
	
	public Node getNextMove(Player player) {
		
		// get closest goal
		goal = getNearestGoal(player);
		
		// if no goals return
		if (goal == null)
			return null;
		
		// if no goal has been set to the path, set the closest to the current
		if (currentGoal == null) 
			currentGoal = goal;
		
		/* 
		 * Preconditions 
		 *
		 *  - check goal exists
		 *  - check path is not over
		 *  - check path does not contain enemies
		 *  - currentGoal is the closest
		 *  
		 *  if these criteria are met we can return next step
		 */
		
		// only check against existing goal if we already have found a path
		if (!path.isEmpty())
			goalOnPathIsClosest = goal.gethValue() > currentGoal.gethValue();
		
		if (goalOnPathIsClosest &&
			board.getCharAt(currentGoal) == Board.GOAL && 
			!path.isEmpty() &&
			!board.charInPath(path, Board.ENEMY)
			)
		{
			Node nextMove = path.getLast();
			path.remove(nextMove);
			return nextMove;
		}
		
		// Otherwise we need a new path
		// reset lists and path
		openList.clear();
		closedList.clear();
		path.clear();
		Node current = new Node(player.getX(), player.getY(), null);
		
		// add current to openlist
		openList.add(current);
		
		while(!openList.isEmpty()) {
			current = Collections.min(openList);
			
			if (current.equals(goal)) {
				while(current.getParent() != null) {
					path.add(current);
					current = current.getParent();
				}
				// store current goal for this path
				currentGoal = goal;
				
				Node nextMove = path.getLast();
				path.remove(nextMove);
				return nextMove;
			}
			
			openList.remove(current);
			closedList.add(current);
			
			// check adjacent squares
			openList.addWalkableNodes(current, goal, closedList, board);
		
		}
		// Error
		return null;

	}

	/**
	 * @param player
	 */
	private Node getNearestGoal(Player player) {
		// get goal
		NodeList allGoals = board.getAllNodes(Board.GOAL);
		
		// no goals, do nothing
		if(allGoals.isEmpty()) return null;
	
		// set goals hValues
		for (Node node : allGoals) {
			node.sethValue(player);
		}
	
		// get closest goal
		return  Collections.min(allGoals);
	}
}
