package animalsPetInheritance.animals;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Animal {
    @NonNull
    private String voice;

    @NonNull
    private String favouriteFood;

    public void giveVoice() {
        System.out.println(this.voice);
    }

    public void eat() {
        System.out.printf("I like to eat %s\n", this.favouriteFood);
    }

    public void setFavouriteFood(String favouriteFood) {
        this.favouriteFood = favouriteFood;
    }

    public abstract void move();

    public String getVoice() {
        return voice;
    }

    public String getFavouriteFood() {
        return favouriteFood;
    }
}
