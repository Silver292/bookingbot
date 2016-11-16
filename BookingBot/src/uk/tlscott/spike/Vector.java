package uk.tlscott.spike;

class Vector {
	int x;
	int y;
	
	Vector (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Vector v) {
		return (this.x == v.x) && (this.y == v.y);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Vector:[%d,%d]\n", this.x, this.y);
	}
}