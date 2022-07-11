package headHuntingCompany.models;

import headHuntingCompany.JobEventListener;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Data
@RequiredArgsConstructor
public class JobSeeker extends JobEventListener {
    private String id = UUID.randomUUID().toString();
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String email;
    @NonNull
    private String phone;
    @NonNull
    private List<String> skills;

    @Override
    public void notifyNewJob(String companyName, JobPost jobPost) {
        System.out.println(companyName + "(company) wants to interview for " + jobPost.getTitle() + " with " + this.name);

        if (makeResult()) {
            this.jobEventManager.respondToJobOffer(this.getName(), jobPost);
        }
    }

    public boolean makeResult() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
