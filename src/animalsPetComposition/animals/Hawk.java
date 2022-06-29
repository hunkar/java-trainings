package animalsPetComposition.animals;

import animalsPetComposition.movements.Flying;

public class Hawk extends Bird implements Flying {
    public Hawk(String voice, String favouriteFood) {
        super(voice, favouriteFood);
    }

    @Override
    public void move() {
        walk();
        fly();
    }
}
