package headHuntingCompany.tests;

import headHuntingCompany.JobEventManager;
import headHuntingCompany.models.Company;
import headHuntingCompany.models.JobPost;
import headHuntingCompany.models.JobSeeker;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class JobEventManagerTest {
    @Test
    public void addCompany() {
        JobEventManager jobEventManager = new JobEventManager();
        jobEventManager.addCompany(new Company("Company 1", "com1@mail.com", "000"));

        Assert.assertEquals(jobEventManager.getCompanies().size(), 1);
    }

    @Test
    public void addSingleSkillJobSeeker() {
        JobEventManager jobEventManager = new JobEventManager();
        jobEventManager.addJobSeeker(new JobSeeker("JobSeeker 1", "1", "com1@mail.com", "000",
                Arrays.asList("Java")));

        Assert.assertEquals(jobEventManager.getJobSeekers().size(), 1);
    }

    @Test
    public void addMultipleSkillJobSeeker() {
        JobEventManager jobEventManager = new JobEventManager();
        jobEventManager.addJobSeeker(new JobSeeker("JobSeeker 1", "1", "com1@mail.com", "000",
                Arrays.asList("Java", "C")));

        Assert.assertEquals(jobEventManager.getJobSeekers().size(), 2);
    }

    @Test
    public void postSingleSkillJob() {
        JobEventManager jobEventManager = new JobEventManager();
        jobEventManager.addCompany(new Company("Company 1", "com1@mail.com", "000"));

        jobEventManager.postNewJob(new JobPost("description", "title",
                Arrays.asList("C"),
                jobEventManager.getCompanies().values().stream().findFirst().get().getId()
        ));
        ;

        Assert.assertEquals(jobEventManager.getJobPosts().size(), 1);
    }

    @Test
    public void postMultipleSkillJob() {
        JobEventManager jobEventManager = new JobEventManager();
        jobEventManager.addCompany(new Company("Company 1", "com1@mail.com", "000"));

        jobEventManager.postNewJob(new JobPost("description", "title",
                Arrays.asList("C", "Java"),
                jobEventManager.getCompanies().values().stream().findFirst().get().getId()
        ));
        ;

        Assert.assertEquals(jobEventManager.getJobPosts().size(), 2);
    }

    @Test
    public void fitAcceptedJobSeeker() {
        JobEventManager jobEventManager = new JobEventManager();

        Company company = mock(Company.class);
        when(company.getId()).thenReturn("12");
        jobEventManager.addCompany(company);

        JobSeeker jobSeeker = mock(JobSeeker.class);
        when(jobSeeker.getSkills()).thenReturn(Arrays.asList("C", "Java"));
        when(jobSeeker.getId()).thenReturn("123");
        when(jobSeeker.onNotification(any(Company.class), any(JobPost.class))).thenReturn(true);
        jobEventManager.addJobSeeker(jobSeeker);

        JobPost jobPost = new JobPost("description", "title",
                Arrays.asList("C", "Java"),
                company.getId()
        );


        jobEventManager.postNewJob(jobPost);

        Mockito.verify(jobSeeker, Mockito.times(1)).onNotification(company, jobPost);
        Mockito.verify(company, Mockito.times(1)).onNotification(jobSeeker, jobPost);
    }

    @Test
    public void fitNotAcceptedJobSeeker() {
        JobEventManager jobEventManager = new JobEventManager();

        Company company = mock(Company.class);
        when(company.getId()).thenReturn("12");
        jobEventManager.addCompany(company);

        JobSeeker jobSeeker = mock(JobSeeker.class);
        when(jobSeeker.getSkills()).thenReturn(Arrays.asList("C", "Java"));
        when(jobSeeker.getId()).thenReturn("123");
        when(jobSeeker.onNotification(any(Company.class), any(JobPost.class))).thenReturn(false);
        jobEventManager.addJobSeeker(jobSeeker);

        JobPost jobPost = new JobPost("description", "title",
                Arrays.asList("C", "Java"),
                company.getId()
        );


        jobEventManager.postNewJob(jobPost);

        Mockito.verify(jobSeeker, Mockito.times(1)).onNotification(company, jobPost);
        Mockito.verify(company, Mockito.times(0)).onNotification(jobSeeker, jobPost);
    }

    @Test
    public void notFitJobSeeker() {
        JobEventManager jobEventManager = new JobEventManager();

        Company company = mock(Company.class);
        when(company.getId()).thenReturn("12");
        jobEventManager.addCompany(company);

        JobSeeker jobSeeker = mock(JobSeeker.class);
        when(jobSeeker.getSkills()).thenReturn(Arrays.asList("C", "Java"));
        when(jobSeeker.getId()).thenReturn("123");
        when(jobSeeker.onNotification(any(Company.class), any(JobPost.class))).thenReturn(true);
        jobEventManager.addJobSeeker(jobSeeker);

        JobPost jobPost = new JobPost("description", "title",
                Arrays.asList("C++", "Javascript"),
                company.getId()
        );


        jobEventManager.postNewJob(jobPost);

        Mockito.verify(jobSeeker, Mockito.times(0)).onNotification(company, jobPost);
        Mockito.verify(company, Mockito.times(0)).onNotification(jobSeeker, jobPost);
    }

    @Test
    public void addFitJobSeekerAfterJobPost() {
        JobEventManager jobEventManager = new JobEventManager();

        Company company = mock(Company.class);
        when(company.getId()).thenReturn("12");
        jobEventManager.addCompany(company);


        JobPost jobPost = new JobPost("description", "title",
                Arrays.asList("C", "Java"),
                company.getId()
        );

        JobSeeker jobSeeker = mock(JobSeeker.class);
        when(jobSeeker.getSkills()).thenReturn(Arrays.asList("C", "Java"));
        when(jobSeeker.getId()).thenReturn("123");
        when(jobSeeker.onNotification(any(Company.class), any(JobPost.class))).thenReturn(true);
        jobEventManager.addJobSeeker(jobSeeker);


        jobEventManager.postNewJob(jobPost);

        Mockito.verify(jobSeeker, Mockito.times(1)).onNotification(company, jobPost);
        Mockito.verify(company, Mockito.times(1)).onNotification(jobSeeker, jobPost);
    }

    @Test
    public void addFitAndNotFitJobSeekerAfterJobPost() {
        JobEventManager jobEventManager = new JobEventManager();

        Company company = mock(Company.class);
        when(company.getId()).thenReturn("12");
        jobEventManager.addCompany(company);


        JobPost jobPost = new JobPost("description", "title",
                Arrays.asList("C", "Java"),
                company.getId()
        );

        JobSeeker jobSeeker1 = mock(JobSeeker.class);
        when(jobSeeker1.getSkills()).thenReturn(Arrays.asList("C", "Java"));
        when(jobSeeker1.getId()).thenReturn("123");
        when(jobSeeker1.onNotification(any(Company.class), any(JobPost.class))).thenReturn(true);
        jobEventManager.addJobSeeker(jobSeeker1);

        JobSeeker jobSeeker2 = mock(JobSeeker.class);
        when(jobSeeker2.getSkills()).thenReturn(Arrays.asList("C++"));
        when(jobSeeker2.getId()).thenReturn("123");
        when(jobSeeker2.onNotification(any(Company.class), any(JobPost.class))).thenReturn(true);
        jobEventManager.addJobSeeker(jobSeeker2);


        jobEventManager.postNewJob(jobPost);

        Mockito.verify(jobSeeker1, Mockito.times(1)).onNotification(company, jobPost);
        Mockito.verify(jobSeeker2, Mockito.times(0)).onNotification(company, jobPost);
        Mockito.verify(company, Mockito.times(1)).onNotification(jobSeeker1, jobPost);
    }
}
