package horseRace;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Builder
public class Monitor extends Thread {
    private List<Horse> horses;
    private ThreadPoolExecutor poolExecutor;

    public void run() {
        try {
            printHorses();

            while (poolExecutor.getActiveCount() > 0) {
                printHorses();
                TimeUnit.MILLISECONDS.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printHorses() {
        horses.forEach(System.out::println);
        System.out.print("\n");
    }
}
