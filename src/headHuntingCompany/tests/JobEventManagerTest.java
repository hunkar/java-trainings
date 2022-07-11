package headHuntingCompany.tests;

import headHuntingCompany.JobEventManager;
import headHuntingCompany.JobEventManagerImpl;
import headHuntingCompany.JobEventManagerProvider;
import headHuntingCompany.models.Company;
import headHuntingCompany.models.JobPost;
import headHuntingCompany.models.JobSeeker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.*;

public class JobEventManagerTest {
    @Before
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = JobEventManagerProvider.class.getDeclaredField("JOB_EVENT_MANAGER");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void addCompany() {
        JobEventManager jobEventManager = JobEventManagerProvider.getInstance();

        jobEventManager.register(new Company("Company 1", "com1@mail.com", "000"));

        Assert.assertEquals(((JobEventManagerImpl) jobEventManager).getCompanies().size(), 1);
    }

    @Test
    public void addSingleSkillJobSeeker() {
        JobEventManager jobEventManager = JobEventManagerProvider.getInstance();

        jobEventManager.register(new JobSeeker("JobSeeker 1", "1", "com1@mail.com", "000",
                Arrays.asList("Java")));

        Assert.assertEquals(((JobEventManagerImpl) jobEventManager).getJobSeekers().size(), 1);
    }

    @Test
    public void addMultipleSkillJobSeeker() {
        JobEventManager jobEventManager = JobEventManagerProvider.getInstance();

        jobEventManager.register(new JobSeeker("JobSeeker 1", "1", "com1@mail.com", "000",
                Arrays.asList("Java", "C")));

        Assert.assertEquals(((JobEventManagerImpl) jobEventManager).getJobSeekers().size(), 2);
    }

    @Test
    public void postSingleSkillJob() {
        JobEventManager jobEventManager = JobEventManagerProvider.getInstance();
        Company company = new Company("Company 1", "com1@mail.com", "000");

        jobEventManager.register(company);

        company.addJob(new JobPost("description", "title",
                Arrays.asList("C"),
                company.getId()
        ));

        Assert.assertEquals(company.getJobPosts().size(), 1);
    }

    @Test
    public void postMultipleSkillJob() {
        JobEventManager jobEventManager = JobEventManagerProvider.getInstance();
        Company company = new Company("Company 1", "com1@mail.com", "000");

        jobEventManager.register(company);

        company.addJob(new JobPost("description", "title",
                Arrays.asList("C", "Java"),
                company.getId()
        ));

        Assert.assertEquals(company.getJobPosts().size(), 2);
    }


    @Test
    public void fitAcceptedJobSeeker() {
        JobEventManager jobEventManager = JobEventManagerProvider.getInstance();

        Company company = mock(Company.class);
        when(company.getId()).thenReturn("12");
        when(company.getName()).thenReturn("company1");
        doCallRealMethod().when(company).addJob(any());
        doCallRealMethod().when(company).getjobPostsList(any());
        Whitebox.setInternalState(company, "jobPosts", new HashMap<>());
        Whitebox.setInternalState(company, "jobEventManager", JobEventManagerProvider.getInstance());

        jobEventManager.register(company);

        JobSeeker jobSeeker = mock(JobSeeker.class);
        when(jobSeeker.getSkills()).thenReturn(Arrays.asList("C", "Java"));
        when(jobSeeker.getId()).thenReturn("123");
        when(jobSeeker.getName()).thenReturn("jobSeeker1");
        when(jobSeeker.makeResult()).thenReturn(true);
        doCallRealMethod().when(jobSeeker).notifyNewJob(any(), any());
        Whitebox.setInternalState(jobSeeker, "jobEventManager", JobEventManagerProvider.getInstance());

        jobEventManager.register(jobSeeker);

        JobPost jobPost = new JobPost("description", "title",
                Arrays.asList("C", "Java"),
                company.getId()
        );


        company.addJob(jobPost);

        Mockito.verify(jobSeeker, Mockito.times(1)).notifyNewJob(company.getName(), jobPost);
        Mockito.verify(company, Mockito.times(1)).notifyCandidateResponse(jobSeeker.getName(), jobPost);
    }

    @Test
    public void fitNotAcceptedJobSeeker() {
        JobEventManager jobEventManager = JobEventManagerProvider.getInstance();

        Company company = mock(Company.class);
        when(company.getId()).thenReturn("12");
        when(company.getName()).thenReturn("company1");
        doCallRealMethod().when(company).addJob(any());
        doCallRealMethod().when(company).getjobPostsList(any());
        Whitebox.setInternalState(company, "jobPosts", new HashMap<>());
        Whitebox.setInternalState(company, "jobEventManager", JobEventManagerProvider.getInstance());

        jobEventManager.register(company);

        JobSeeker jobSeeker = mock(JobSeeker.class);
        when(jobSeeker.getSkills()).thenReturn(Arrays.asList("C", "Java"));
        when(jobSeeker.getId()).thenReturn("123");
        when(jobSeeker.getName()).thenReturn("jobSeeker1");
        when(jobSeeker.makeResult()).thenReturn(false);
        doCallRealMethod().when(jobSeeker).notifyNewJob(any(), any());
        Whitebox.setInternalState(jobSeeker, "jobEventManager", JobEventManagerProvider.getInstance());

        jobEventManager.register(jobSeeker);

        JobPost jobPost = new JobPost("description", "title",
                Arrays.asList("C", "Java"),
                company.getId()
        );


        company.addJob(jobPost);

        Mockito.verify(jobSeeker, Mockito.times(1)).notifyNewJob(company.getName(), jobPost);
        Mockito.verify(company, Mockito.times(0)).notifyCandidateResponse(jobSeeker.getName(), jobPost);
    }

    @Test
    public void notFitJobSeeker() {
        JobEventManager jobEventManager = JobEventManagerProvider.getInstance();

        Company company = mock(Company.class);
        when(company.getId()).thenReturn("12");
        when(company.getName()).thenReturn("company1");
        doCallRealMethod().when(company).addJob(any());
        doCallRealMethod().when(company).getjobPostsList(any());
        Whitebox.setInternalState(company, "jobPosts", new HashMap<>());
        Whitebox.setInternalState(company, "jobEventManager", JobEventManagerProvider.getInstance());

        jobEventManager.register(company);

        JobSeeker jobSeeker = mock(JobSeeker.class);
        when(jobSeeker.getSkills()).thenReturn(Arrays.asList("C", "Java"));
        when(jobSeeker.getId()).thenReturn("123");
        when(jobSeeker.getName()).thenReturn("jobSeeker1");
        when(jobSeeker.makeResult()).thenReturn(false);
        doCallRealMethod().when(jobSeeker).notifyNewJob(any(), any());
        Whitebox.setInternalState(jobSeeker, "jobEventManager", JobEventManagerProvider.getInstance());

        jobEventManager.register(jobSeeker);

        JobPost jobPost = new JobPost("description", "title",
                Arrays.asList("C++"),
                company.getId()
        );


        company.addJob(jobPost);

        Mockito.verify(jobSeeker, Mockito.times(0)).notifyNewJob(company.getName(), jobPost);
        Mockito.verify(company, Mockito.times(0)).notifyCandidateResponse(jobSeeker.getName(), jobPost);
    }

    @Test
    public void addFitJobSeekerAfterJobPost() {
        JobEventManager jobEventManager = JobEventManagerProvider.getInstance();

        Company company = mock(Company.class);
        when(company.getId()).thenReturn("12");
        when(company.getName()).thenReturn("company1");
        doCallRealMethod().when(company).addJob(any());
        doCallRealMethod().when(company).getjobPostsList(any());
        Whitebox.setInternalState(company, "jobPosts", new HashMap<>());
        Whitebox.setInternalState(company, "jobEventManager", JobEventManagerProvider.getInstance());

        jobEventManager.register(company);

        JobPost jobPost = new JobPost("description", "title",
                Arrays.asList("C"),
                company.getId()
        );

        company.addJob(jobPost);

        JobSeeker jobSeeker = mock(JobSeeker.class);
        when(jobSeeker.getSkills()).thenReturn(Arrays.asList("C", "Java"));
        when(jobSeeker.getId()).thenReturn("123");
        when(jobSeeker.getName()).thenReturn("jobSeeker1");
        when(jobSeeker.makeResult()).thenReturn(true);
        doCallRealMethod().when(jobSeeker).notifyNewJob(any(), any());
        Whitebox.setInternalState(jobSeeker, "jobEventManager", JobEventManagerProvider.getInstance());

        jobEventManager.register(jobSeeker);

        Mockito.verify(jobSeeker, Mockito.times(1)).notifyNewJob(company.getName(), jobPost);
        Mockito.verify(company, Mockito.times(1)).notifyCandidateResponse(jobSeeker.getName(), jobPost);
    }

    @Test
    public void addFitAndNotFitJobSeekerAfterJobPost() {
        JobEventManager jobEventManager = JobEventManagerProvider.getInstance();

        Company company = mock(Company.class);
        when(company.getId()).thenReturn("12");
        when(company.getName()).thenReturn("company1");
        doCallRealMethod().when(company).addJob(any());
        doCallRealMethod().when(company).getjobPostsList(any());
        Whitebox.setInternalState(company, "jobPosts", new HashMap<>());
        Whitebox.setInternalState(company, "jobEventManager", JobEventManagerProvider.getInstance());

        jobEventManager.register(company);

        JobPost jobPost = new JobPost("description", "title",
                Arrays.asList("C"),
                company.getId()
        );

        company.addJob(jobPost);

        JobSeeker jobSeeker = mock(JobSeeker.class);
        when(jobSeeker.getSkills()).thenReturn(Arrays.asList("C", "Java"));
        when(jobSeeker.getId()).thenReturn("123");
        when(jobSeeker.getName()).thenReturn("jobSeeker1");
        when(jobSeeker.makeResult()).thenReturn(true);
        doCallRealMethod().when(jobSeeker).notifyNewJob(any(), any());
        Whitebox.setInternalState(jobSeeker, "jobEventManager", JobEventManagerProvider.getInstance());

        jobEventManager.register(jobSeeker);

        JobSeeker jobSeeker2 = mock(JobSeeker.class);
        when(jobSeeker2.getSkills()).thenReturn(Arrays.asList("C++"));
        when(jobSeeker2.getId()).thenReturn("123");
        when(jobSeeker2.getName()).thenReturn("jobSeeker1");
        when(jobSeeker2.makeResult()).thenReturn(true);
        doCallRealMethod().when(jobSeeker2).notifyNewJob(any(), any());
        Whitebox.setInternalState(jobSeeker2, "jobEventManager", JobEventManagerProvider.getInstance());

        jobEventManager.register(jobSeeker2);

        Mockito.verify(jobSeeker, Mockito.times(1)).notifyNewJob(company.getName(), jobPost);
        Mockito.verify(jobSeeker2, Mockito.times(0)).notifyNewJob(company.getName(), jobPost);
        Mockito.verify(company, Mockito.times(1)).notifyCandidateResponse(jobSeeker.getName(), jobPost);
    }
}
