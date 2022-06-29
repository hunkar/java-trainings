package animalsPetInheritance.animals;

import animalsPetInheritance.movements.Swimming;
import animalsPetInheritance.movements.Walking;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cat extends Pet implements Walking, Swimming {
    @Builder
    public Cat(String voice, String favouriteFood, String name, String activity, boolean isTrained) {
        super(voice, favouriteFood, name, activity, isTrained);
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
