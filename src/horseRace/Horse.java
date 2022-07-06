package horseRace;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Builder
@Data
public class Horse implements Callable {
    private String name;
    private int distance;
    private int raceLength;

      @Override
    public String toString() {
        StringBuilder path = new StringBuilder("Horse" + name + " #");
        for (int i = 1; i <= raceLength; i++) {
            if (i == distance) {
                path.append("X");
            } else {
                path.append("-");
            }
        }
        path.append("#");
        return path.toString();
    }

    @Override
    public Object call() {
        try {
            while (distance < raceLength) {
                long duration = ThreadLocalRandom.current().nextLong(0, 3000);
                long runLength = ThreadLocalRandom.current().nextLong(0, raceLength);
                distance += runLength;

                if (distance > raceLength)
                    distance = raceLength;
                TimeUnit.MILLISECONDS.sleep(duration);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return name;
    }
}
