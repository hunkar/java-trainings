package shapes.models;

public class Circle extends Shape {
    private float radius;

    public Circle(String label, String color, float radius) {
        super(label, color);
        this.radius = radius;
    }


    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(this.radius, 2);
    }

    @Override
    public double calculateCircumference() {
        return 2 * Math.PI * this.radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
