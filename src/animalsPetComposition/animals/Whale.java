package animalsPetComposition.animals;

import animalsPetComposition.movements.Swimming;


public class Whale extends Mammal implements Swimming {
    public Whale(String voice, String favouriteFood) {
        super(voice, favouriteFood);
    }

    @Override
    public void move() {
        swim();
    }
}
