package animalsPetInheritance;

import animalsPetInheritance.animals.*;
import animalsPetInheritance.movements.Walking;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(new Lion("Roar", "meat"));

        Cat garfieldCat = Cat.builder()
                .name("Garfield")
                .favouriteFood("milk")
                .voice("Miaow")
                .activity("sleeping")
                .isTrained(true)
                .build();

        System.out.println("Cat activity" +  garfieldCat.getActivity());

        animals.add(garfieldCat);


        animals.add(new Duck("Quack", "wheat"));
        animals.add(new Sparrow("Cheep, Chirrup.", "wheat"));
        animals.add(new Hawk("Screaming", "meat"));
        animals.add(new Wolf("Barking", "meat"));
        animals.add(new Whale("Whistles", "fish"));

        int sumOfLegs = animals.stream()
                .map(animal -> animal instanceof Walking ? ((Walking) animal).getLegCount() : 0)
                .reduce(Integer::sum)
                .orElse(0);


        System.out.println("Sum of legs: " + sumOfLegs);

        animals.forEach(Animal::move);
        animals.forEach(Animal::giveVoice);
        animals.forEach(Animal::eat);
        animals.stream().filter(animal -> animal instanceof Pet).forEach(animal -> {
            ((Pet) animal).describeItself();
        });
    }
}
