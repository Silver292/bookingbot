package uk.tlscott.BookingBot;

public class Node  implements Comparable<Node>{
	private int x, y;
	private Node parent;
	private int hValue;
	
	public Node() {}
	
	public Node(int x, int y, Node parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
	}

	public Node(Node node) {
		this.x = node.x;
		this.y = node.y;
		this.parent = node.parent;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int gethValue() {
		return hValue;
	}

	public void sethValue(Node node) {
		this.hValue = 10*(Math.abs(this.getX() - node.getX()) + Math.abs(this.getY() - node.getY()));
	}
	
	public String toString() {
		return String.format("Node:[%d,%d]\nH Value: %d\n", this.x, this.y, this.hValue);
	}

	@Override
	public int compareTo(Node o) {
		return this.gethValue() > o.gethValue() ? 1 : (this.gethValue() == o.gethValue() ? 0 : -1);
	}
	
	public boolean equals(Node n) {
		return this.getX() == n.getX() && this.getY() == n.getY();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hValue;
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
}
