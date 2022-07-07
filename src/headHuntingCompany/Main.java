package headHuntingCompany;

import headHuntingCompany.models.Company;
import headHuntingCompany.models.JobPost;
import headHuntingCompany.models.JobSeeker;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        JobEventManager jobEventManager = new JobEventManager();

        //Add companies
        jobEventManager.addCompany(new Company("Company 1", "com1@mail.com", "000"));
        jobEventManager.addCompany(new Company("Company 2", "com2@mail.com", "000"));
        jobEventManager.addCompany(new Company("Company 3", "com3@mail.com", "000"));

        //Add jobSeekers
        jobEventManager.addJobSeeker(new JobSeeker("JobSeeker 1", "1", "com1@mail.com", "000",
                Arrays.asList("Java", "Javascript")));
        jobEventManager.addJobSeeker(new JobSeeker("JobSeeker 2", "1", "com1@mail.com", "000",
                Arrays.asList("C", "C++")));
        jobEventManager.addJobSeeker(new JobSeeker("JobSeeker 3", "1", "com1@mail.com", "000",
                Arrays.asList("Java", "C#")));

        //Post new job
        jobEventManager.postNewJob(new JobPost("description", "title",
                Arrays.asList("C", "C++"),
                jobEventManager.getCompanies().values().stream().findFirst().get().getId()
        ));

        //Post new job
        jobEventManager.addJobSeeker(new JobSeeker("JobSeeker 4", "4", "com1@mail.com", "000",
                Arrays.asList("C")));

    }
}
