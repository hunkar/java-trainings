package animalsPetInheritance.animals;

import animalsPetInheritance.movements.Flying;
import animalsPetInheritance.movements.Walking;
import lombok.NonNull;

public class Sparrow extends Animal implements Flying, Walking {
    public Sparrow(@NonNull String voice, @NonNull String favouriteFood) {
        super(voice, favouriteFood);
    }

    @Override
    public void move() {
        walk();
        fly();
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
