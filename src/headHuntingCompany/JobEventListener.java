package headHuntingCompany;

import headHuntingCompany.models.JobPost;
import headHuntingCompany.models.JobSeeker;
import org.mockito.Mock;

public class JobEventListener {
    @Mock
    protected JobEventManager jobEventManager = JobEventManagerProvider.getInstance();

    public void notifyNewJob(String companyName, JobPost jobPost){}

    protected void notifyCandidateResponse(String jobSeekerName, JobPost jobPost){}
}
