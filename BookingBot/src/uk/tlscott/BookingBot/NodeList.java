package uk.tlscott.BookingBot;
import java.util.ArrayList;
/**
 * ArrayList extension to implements method addWalkableNodes.
 * @author Scott
 *
 */
public class NodeList extends ArrayList<Node> {

	private static final long serialVersionUID = -4677471985696982691L;

	public NodeList() {
		super();
	}

	/**
	 * Adds all walkable nodes to NodeList, that are not walls or enemies, are not already in the closedList and
	 * are not closer than the same node already in the NodeList.
	 * @param nodeToCheck Node to check around for walkable nodes
	 * @param goal Current goal used to set walkable nodes H Values
	 * @param closedList Current closed list to check node is not already added
	 * @param board Game board used to check bounds of 2d array
	 */
	public void addWalkableNodes(Node nodeToCheck, Node goal, NodeList closedList, Board board) {
		ArrayList<Node> walkableNodes = board.getAdjacent(nodeToCheck);
		
		for (Node node : walkableNodes) {
			if (board.getCharAt(node) == Board.WALL) continue;
			if (board.getCharAt(node) == Board.ENEMY) continue;
			if (closedList.hasNode(node))continue;
			
			// get H value for node
			node.sethValue(goal);
			
			if (this.hasNode(node)) {
				if (node.gethValue() > this.getNodeEqualTo(node).gethValue()) continue;
			}
			this.add(node);
		}
	}
	
	/**
	 * Returns node equal to arg node, used instead of ArrayList.indexOf()
	 * when two nodes may be in the same grid location but not be the same reference
	 * @param node
	 * @return null if node not found, node equal to param if found
	 */
	private Node getNodeEqualTo(Node node) {
		for (Node n : this) {
			if(n.equals(node)) return node;
		}
		return null;
	}

	/**
	 * Returns the last node added to the list, same as ArrayList.get(ArrayList.size() -1)
	 * @return The last node added to the list
	 */
	public Node getLast() {
		return this.get(this.size()-1);
	}
	
	/**
	 * Returns if a node is in NodeList, compares by x and y values
	 * @param node node to search for
	 * @return true if node is in list, otherwise false
	 */
	private boolean hasNode(Node node) {
		for (Node n : this) {
			if(n.equals(node)) return true;
		}
		return false;
	}
}
