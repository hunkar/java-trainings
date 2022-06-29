package horseRace;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Data
@Builder
public class Race {
    int raceLength;

    @Builder.Default
    ArrayList<Horse> horses = new ArrayList<>();

    void addHorse(Horse horse){
        horse.setTotalRaceLength(this.raceLength);
        horses.add(horse);
    }

    void start() throws InterruptedException {
        //Set monitor tread and start
        Monitor monitor = Monitor.builder()
                .horses(horses)
                .build();

        monitor.start();

        //Start horse threads and wait for finish.
        ExecutorService es = Executors.newCachedThreadPool();
        horses.forEach(es::execute);

        es.shutdown();

        boolean finished = es.awaitTermination(10, TimeUnit.MINUTES);

        //If race is finish, interrupt monitor thread, show orders.
        if(finished){
            monitor.interrupt();

            System.out.println("Race completed.");
            horses.sort((o1, o2) -> o1.getFinishDate().isBefore(o2.getFinishDate()) ? -1: 1);

            horses.forEach((horse) -> System.out.printf("%s, %s\n", horse.getHorseName(), horse.getFinishDate()));
        }
    }
}
