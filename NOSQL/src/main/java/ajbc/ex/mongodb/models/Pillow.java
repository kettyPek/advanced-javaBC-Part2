package ajbc.ex.mongodb.models;

public class Pillow {

	private Shape shape;
	private Color color;

	public Pillow(Shape shape, Color color) {
		this.shape = shape;
		this.color = color;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Pillow [shape=" + shape + ", color=" + color + "]";
	}

	public static enum Shape {
		SQUARE, RECTANGLE;
	}

	public static enum Color {
		RED, GREEN, BLU, YELLOW, PURPLE, BROWN;
	}
}
