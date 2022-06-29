package horseRace;

import lombok.Builder;

import java.util.ArrayList;
import java.util.stream.IntStream;

@Builder
public class Monitor extends Thread {
    @Builder.Default
    ArrayList<Horse> horses = new ArrayList<>();

    @Override
    public void run() {
        printRace();
    }

    void printRace(){
        //Print horses paths
        horses.forEach(horse -> {
            System.out.printf(String.format("%-" + 20 + "s", horse.getHorseName()));
            System.out.print("#");
            IntStream.range(0, horse.getTotalRaceLength())
                    .forEach(index -> System.out.printf(horse.getCoveredDistance() == index ? "X" : "-"));
            System.out.print("#\n");
        });

        //Sleep 2 seconds
        try {
            sleep(2 * 1000);
            this.printRace();
        } catch (InterruptedException e) {
            this.interrupt();
        }
    }
}
