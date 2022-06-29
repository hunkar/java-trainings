package animalsPetComposition.animals;

import animalsPetComposition.movements.Walking;

public class Bird extends Animal implements Walking {
    public Bird(String voice, String favouriteFood) {
        super(voice, favouriteFood);
    }

    @Override
    public void move() {
        walk();
    }

    @Override
    public void walk() {
        System.out.printf("I can walk on %d legs.\n", getLegCount());
    }

    @Override
    public int getLegCount() {
        return 2;
    }
}
