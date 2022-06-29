package horseRace;

public class Main {
    private static final int TOTAL_RACE_LENGTH = 30;

    public static void main(String[] args) throws InterruptedException {
        Race race = Race.builder().raceLength(TOTAL_RACE_LENGTH).build();

        race.addHorse(Horse.builder()
                .horseName("Horse-1")
                .coveredDistance(0)
                .build());

        race.addHorse(Horse.builder()
                .horseName("Horse-2")
                .coveredDistance(0)
                .build());

        race.addHorse(Horse.builder()
                .horseName("Horse-3")
                .coveredDistance(0)
                .build());

        race.addHorse(Horse.builder()
                .horseName("Horse-4")
                .coveredDistance(0)
                .build());

        race.addHorse(Horse.builder()
                .horseName("Horse-5")
                .coveredDistance(0)
                .build());

        race.addHorse(Horse.builder()
                .horseName("Horse-6")
                .coveredDistance(0)
                .build());

        race.start();
    }
}
