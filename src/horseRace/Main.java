package horseRace;

import java.util.concurrent.ExecutionException;

public class Main {
    private static final int TOTAL_RACE_LENGTH = 30;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Race race = Race.builder()
                .raceLength(TOTAL_RACE_LENGTH)
                .build();

        race.addHorse(Horse.builder().name("A").distance(0).build());
        race.addHorse(Horse.builder().name("B").distance(0).build());
        race.addHorse(Horse.builder().name("C").distance(0).build());


        race.start();
    }
}
