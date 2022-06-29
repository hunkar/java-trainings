package shapes.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shapes.models.Circle;
import shapes.models.Rectangle;
import shapes.models.Square;
import shapes.models.Triangle;

public class ShapeTests {
    @Test
    public void rectangleAreaTest() {
        Rectangle rectangle = new Rectangle("rect", "red", 12, 13);

        Assertions.assertEquals(156.0, rectangle.calculateArea(), 0.01);
        Assertions.assertEquals(50, rectangle.calculateCircumference(), 0.01);
    }

    @Test
    public void squareAreaTest() {
        Square square = new Square("square", "red", 12);

        Assertions.assertEquals(144.0, square.calculateArea(), 0.01);
        Assertions.assertEquals(48, square.calculateCircumference(), 0.01);
    }

    @Test
    public void triangleAreaTest() {
        Triangle triangle = new Triangle("triangle", "red", 5, 12, 13);

        Assertions.assertEquals(30.0, triangle.calculateArea(), 0.01);
        Assertions.assertEquals(30, triangle.calculateCircumference(), 0.01);
    }

    @Test
    public void triangleValidityTest() {
        Triangle validTriangle = new Triangle("triangle", "red", 5, 12, 13);
        Triangle invalidTriangle = new Triangle("triangle", "red", 5, 12, 0);

        Assertions.assertTrue(validTriangle.isValid());
        Assertions.assertFalse(invalidTriangle.isValid());
    }

    @Test
    public void circleAreaTest() {
        Circle circle = new Circle("circle", "red", 10);

        Assertions.assertEquals(314.15, circle.calculateArea(), 0.01);
        Assertions.assertEquals(62.83, circle.calculateCircumference(), 0.01);
    }
}