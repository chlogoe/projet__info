package Model;

public enum Direction {
	Up,
	Right,
	Down,
	Left;
	public int getX() {
		if(this == Left) {
			return -1;
		}
		else if (this == Right) {
			return 1;
		}
		else {
			return 0;
		}
	}
	public int getY() {
		if(this == Up) {
			return -1;
		}
		else if (this == Down) {
			return 1;
		}
		else {
			return 0;
		}
	}
}