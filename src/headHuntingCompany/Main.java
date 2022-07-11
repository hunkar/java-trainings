package headHuntingCompany;

import headHuntingCompany.models.Company;
import headHuntingCompany.models.JobPost;
import headHuntingCompany.models.JobSeeker;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        JobEventManager jobEventManagerImpl = JobEventManagerProvider.getInstance();

        Company company1 = new Company("Company 1", "com1@mail.com", "000");

        //Add companies
        jobEventManagerImpl.register(company1);
        jobEventManagerImpl.register(new Company("Company 2", "com2@mail.com", "000"));
        jobEventManagerImpl.register(new Company("Company 3", "com3@mail.com", "000"));


        //Add jobSeekers
        jobEventManagerImpl.register(new JobSeeker("JobSeeker 1", "1", "com1@mail.com", "000",
                Arrays.asList("Java", "Javascript")));
        jobEventManagerImpl.register(new JobSeeker("JobSeeker 2", "1", "com1@mail.com", "000",
                Arrays.asList("C", "C++")));
        jobEventManagerImpl.register(new JobSeeker("JobSeeker 3", "1", "com1@mail.com", "000",
                Arrays.asList("Java", "C#")));

        //Post new job
        company1.addJob(new JobPost("description", "title",
                Arrays.asList("C", "C++"),
                company1.getId()
        ));

        //Register new job seeker
        jobEventManagerImpl.register(new JobSeeker("JobSeeker 4", "4", "com1@mail.com", "000",
                Arrays.asList("C")));

    }
}
