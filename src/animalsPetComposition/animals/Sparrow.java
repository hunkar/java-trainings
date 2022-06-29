package animalsPetComposition.animals;

import animalsPetComposition.movements.Flying;


public class Sparrow extends Bird implements Flying {
    public Sparrow(String voice, String favouriteFood) {
        super(voice, favouriteFood);
    }

    @Override
    public void move() {
        walk();
        fly();
    }
}
