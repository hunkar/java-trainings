package headHuntingCompany;

import headHuntingCompany.models.Company;
import headHuntingCompany.models.JobPost;

public interface JobEventManager {
    void postJob(Company company, JobPost jobPost);

    void respondToJobOffer(String jobSeekerName ,JobPost jobPost);

    void register(JobEventListener listener);
}
