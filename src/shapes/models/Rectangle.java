package shapes.models;

public class Rectangle extends Shape {
    private final float width;
    private final float height;

    public Rectangle(String label, String color, float width, float height) {
        super(label, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculateCircumference() {
        return 2 * (width + height);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
