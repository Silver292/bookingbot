package uk.tlscott.spike;

import uk.tlscott.BookingBot.Node;

class GNode extends Node{
	
	Vector tile;
	int gCost;
	int hCost;
	int fCost;
	GNode parent;

	GNode (Vector tile, GNode parent, int gCost, int hCost) {
		super(tile.x, tile.y, parent);
		this.parent = parent;
		this.tile = tile;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = gCost + hCost;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("GNode:[%d,%d]\nF Cost: %d\nG Cost: %d\nH Cost: %d\n", super.getX(), super.getY(), this.fCost, this.gCost, this.hCost);
	}

}