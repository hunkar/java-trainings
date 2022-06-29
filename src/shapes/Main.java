package shapes;

import shapes.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void printShapeWithArea(Shape shape) {
        System.out.printf("Label: %s, Color: %s, Area: %.2f\n",
                shape.getLabel(), shape.getColor(), shape.calculateArea());
    }

    public static void printShape(Shape shape) {
        System.out.printf("Label: %s, Color: %s\n",
                shape.getLabel(), shape.getColor());
    }

    public static void main(String[] args) {
        List<Shape> shapeList = new ArrayList<>();
        shapeList.add(new Rectangle("rect", "blue", 12, 12));
        shapeList.add(new Rectangle("rect2", "yellow", 12, 10));
        shapeList.add(new Square("square", "green", 12));
        shapeList.add(new Triangle("triangle", "gray", 5, 12, 13));
        shapeList.add(new Circle("circle", "blue", 10));

        List<Shape> rectangleList, squareList, blueList;

        System.out.println("All shapes");
        for (Shape shapeItem : shapeList) {
            printShapeWithArea(shapeItem);
        }

        rectangleList = shapeList.stream().filter(shapeItem -> shapeItem instanceof Rectangle && !(shapeItem instanceof Square)).collect(Collectors.toList());
        squareList = shapeList.stream().filter(shapeItem -> shapeItem instanceof Square).collect(Collectors.toList());
        blueList = shapeList.stream().filter(shapeItem -> shapeItem.getColor().equals("blue")).collect(Collectors.toList());

        System.out.println("Rectangle shapes");
        for (Shape shapeItem : rectangleList) {
            printShape(shapeItem);
        }

        System.out.println("Square shapes");
        for (Shape shapeItem : squareList) {
            printShape(shapeItem);
        }

        System.out.println("Blue shapes");
        for (Shape shapeItem : blueList) {
            printShape(shapeItem);
        }
    }
}
