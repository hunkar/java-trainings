package animalsPetComposition.animals;


public abstract class Animal {
    private final String voice;
    private String favouriteFood;
    private boolean isPet = false;
    private Pet petProperties = null;

    public Animal(String voice, String favouriteFood) {
        this.voice = voice;
        this.favouriteFood = favouriteFood;
    }

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

    public void makePet(String name, String activity, boolean isTrained) {
        this.isPet = true;
        this.petProperties = new Pet(name, activity, isTrained);
    }

    public void clearPet() {
        this.isPet = false;
        this.petProperties = null;
    }

    public boolean isPet() {
        return isPet;
    }

    public Pet getPetProperties() {
        return petProperties;
    }
}
