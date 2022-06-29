package animalsPetInheritance.animals;

import animalsPetInheritance.movements.Swimming;
import animalsPetInheritance.movements.Walking;
import animalsPetInheritance.movements.Flying;

import lombok.NonNull;

public class Duck extends Animal implements Swimming, Flying, Walking {
    public Duck(@NonNull String voice, @NonNull String favouriteFood) {
        super(voice, favouriteFood);
    }

    @Override
    public void move() {
        this.walk();
        this.fly();
        this.swim();
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
