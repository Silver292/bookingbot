package uk.tlscott.BookingBot;

public class Player extends Node{
	
	private char icon;
	private String name;
	private boolean isParalyzed = false;
	
	public Player() {}
	
	public Player(int x, int y, Node parent, char icon) {
		super(x, y, parent);
		this.icon = icon;
	}
	
	public Player(Node node, char icon) {
		super(node);
		this.icon = icon;
	}
	
	public char getIcon() {
		return icon;
	}
	
	public void setIcon(char icon) {
		this.icon = icon;
	}

	public void moveTo(Node node) {
		System.out.println(this.getDirection(node));
		this.update(node);
	}
	
	public void update(Node node) {
		this.setX(node.getX());
		this.setY(node.getY());
		this.setParent(node.getParent());
	}
	
	private String getDirection(Node node) {
		if (node.getX() > this.getX())
			return "right";
		if (node.getX() < this.getX())
			return "left";
		if (node.getY() > this.getY())
			return "down";
		if (node.getY() < this.getY())
			return "up";
		return null;

	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setParalyzed(boolean isParalyzed) {
		this.isParalyzed = isParalyzed;
	}
	
	public boolean isParalyzed() {
		return isParalyzed;
	}

	public void setLocation(Board board) {
		Node location = board.getNode(this.getIcon());
		super.setX(location.getX());
		super.setY(location.getY());
	}
}
