package animalsPetComposition.animals;

import animalsPetComposition.movements.Swimming;
import animalsPetComposition.movements.Flying;

public class Duck extends Bird implements Swimming, Flying {
    public Duck(String voice, String favouriteFood) {
        super(voice, favouriteFood);
    }

    @Override
    public void move() {
        this.walk();
        this.fly();
        this.swim();
    }
}
