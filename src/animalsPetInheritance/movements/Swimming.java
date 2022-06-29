package animalsPetInheritance.movements;

public interface Swimming {
    default void swim() { System.out.print("I can swim."); }
}
