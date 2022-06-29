package animalsPetInheritance.animals;

import animalsPetInheritance.movements.Swimming;
import animalsPetInheritance.movements.Walking;
import lombok.NonNull;

public class Wolf extends Animal implements Walking, Swimming {
    public Wolf(@NonNull String voice, @NonNull String favouriteFood) {
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
