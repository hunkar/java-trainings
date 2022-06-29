package shapes.models;

public abstract class Shape {
    private String label, color;

    public Shape(String label, String color) {
        this.label = label;
        this.color = color;
    }

    public abstract double calculateArea();

    public abstract double calculateCircumference();

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
