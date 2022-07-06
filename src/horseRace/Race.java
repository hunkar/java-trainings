package horseRace;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Data
@Builder
public class Race {
    @NonNull
    int raceLength;

    @NonNull
    @Builder.Default
    List<Horse> horses = new ArrayList<>();

    void addHorse(Horse horse) {
        horse.setRaceLength(this.raceLength);
        horses.add(horse);
    }

    void start() throws InterruptedException, ExecutionException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        ThreadPoolExecutor monitorExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executor);


        horses.forEach(completionService::submit);

        monitorExecutor.submit(Monitor.builder().horses(horses).poolExecutor(executor).build());
        monitorExecutor.shutdown();
        monitorExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        for (Horse h : horses) {
            System.out.println(completionService.take().get());
        }

        System.out.println("Race Finished");
    }
}
