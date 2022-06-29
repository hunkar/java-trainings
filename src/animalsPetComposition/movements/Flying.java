package animalsPetComposition.movements;

public interface Flying  {
    default void fly(){ System.out.println("I can fly."); }
}
