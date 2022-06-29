package horseRace;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Builder
@Data
public class Horse extends Thread {
    private String horseName;

    @Builder.Default
    private int coveredDistance = 0;
    private int totalRaceLength;

    private LocalDateTime finishDate;

    @Override
    public void run() {
        move();
    }

    private void move() {
        //Sleep random time. It will make difference for horses speed.
        try {
            sleep(ThreadLocalRandom.current().nextInt(200, 500));
        } catch (InterruptedException e) {
            this.interrupt();
        }

        //If race is finish, then interrupt thread. Set finish date time.
        if (coveredDistance >= totalRaceLength - 1) {
            finishDate = LocalDateTime.now();
            interrupt();
            return;
        }

        //Move one step.
        coveredDistance += 1;
        this.move();
    }
}
