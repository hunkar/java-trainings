package animalsPetInheritance.animals;

import lombok.*;

@Getter
@Setter
public class Pet extends Animal {
    @NonNull
    private String name;

    @NonNull
    private String activity;

    @NonNull
    private boolean isTrained;

    public Pet(String voice, String favouriteFood, String name, String activity, boolean isTrained) {
        super(voice, favouriteFood);
        this.name = name;
        this.activity = activity;
        this.isTrained = isTrained;
    }

    @Override
    public void move() {
    }

    public void describeItself() {
        System.out.printf("My name is %s. I like to %s, I am %strained.\n",
                this.name, this.activity, this.isTrained ? "" : "not ");
    }
}
