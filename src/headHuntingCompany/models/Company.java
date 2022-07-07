package headHuntingCompany.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Company implements Listener<JobSeeker> {
    private String id = UUID.randomUUID().toString();
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String phone;


    @Override
    public boolean onNotification(JobSeeker jobSeeker, JobPost jobPost) {
        System.out.println(jobSeeker.getName() + "(job seeker) accepted to interview with " + this.name);
        return true;
    }
}
