package animalsPetComposition;

import animalsPetComposition.animals.*;
import animalsPetComposition.movements.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(new Lion("Roar", "meat"));
        animals.add(new Cat("Miaow", "milk"));
        animals.add(new Duck("Quack", "wheat"));
        animals.add(new Sparrow("Cheep, Chirrup.", "wheat"));
        animals.add(new Hawk("Screaming", "meat"));
        animals.add(new Wolf("Barking", "meat"));
        animals.add(new Whale("Whistles", "fish"));

        Animal myPet = new Cat("Miaow", "milk");
        myPet.makePet("Fluffy", "likes to play with a toy mouse", true);

        animals.add(myPet);

        int sumOfLegs = animals.stream()
                .map(animal -> animal instanceof Walking ? ((Walking) animal).getLegCount() : 0)
                .reduce(Integer::sum)
                .orElse(0);


        System.out.println("Sum of legs: " + sumOfLegs);

        animals.forEach(Animal::move);
        animals.forEach(Animal::giveVoice);
        animals.forEach(Animal::eat);
        animals.stream().filter(Animal::isPet).forEach(animal -> {
            animal.getPetProperties().play();
        });
    }
}
