package shapes.models;

public class Triangle extends Shape {
    private final float firstEdge;
    private final float secondEdge;
    private final float thirdEdge;

    public Triangle(String label, String color, float firstEdge, float secondEdge, float thirdEdge) {
        super(label, color);

        this.firstEdge = firstEdge;
        this.secondEdge = secondEdge;
        this.thirdEdge = thirdEdge;
    }

    @Override
    public double calculateArea() {
        if (this.isValid()) {
            float p = (firstEdge + secondEdge + thirdEdge) / 2;

            return Math.sqrt(p * (p - firstEdge) * (p - secondEdge) * (p - thirdEdge));
        } else {
            return 0;
        }
    }

    @Override
    public double calculateCircumference() {
        if (this.isValid()) {
            return this.firstEdge + this.secondEdge + this.thirdEdge;
        } else {
            return 0;
        }
    }

    public float getFirstEdge() {
        return firstEdge;
    }

    public float getSecondEdge() {
        return secondEdge;
    }

    public float getThirdEdge() {
        return thirdEdge;
    }

    public boolean isValid() {
        return (this.firstEdge + this.secondEdge) > this.thirdEdge
                && (this.firstEdge + this.thirdEdge) > this.secondEdge
                && (this.secondEdge + this.thirdEdge) > this.firstEdge;
    }
}
