package animalsPetComposition.animals;

import javax.swing.*;

public class Pet {
    private final  String name;
    private final String activity;
    private final boolean isTrained;

    public Pet(String name, String activity, boolean isTrained) {
        this.name = name;
        this.activity = activity;
        this.isTrained = isTrained;
    }

    public String getName() {
        return name;
    }

    public String getActivity() {
        return activity;
    }

    public boolean isTrained() {
        return isTrained;
    }

    public void play() {
        System.out.println(this.getName() + " " + this.getActivity());
    }
}
