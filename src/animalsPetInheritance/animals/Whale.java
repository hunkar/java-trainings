package animalsPetInheritance.animals;

import animalsPetInheritance.movements.Swimming;
import lombok.NonNull;

public class Whale extends Animal implements Swimming {
    public Whale(@NonNull String voice, @NonNull String favouriteFood) {
        super(voice, favouriteFood);
    }

    @Override
    public void move() {
        swim();
    }
}
