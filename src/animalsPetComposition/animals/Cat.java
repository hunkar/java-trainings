package animalsPetComposition.animals;

import animalsPetComposition.movements.Swimming;
import animalsPetComposition.movements.Walking;

public class Cat extends Mammal implements Walking, Swimming {
    public Cat(String voice, String favouriteFood) {
        super(voice, favouriteFood);
    }

    @Override
    public void move() {
        swim();
        walk();
    }

    @Override
    public void walk() {
        System.out.printf("I can walk on %d legs.\n", getLegCount());
    }

    @Override
    public int getLegCount() {
        return 4;
    }
}
