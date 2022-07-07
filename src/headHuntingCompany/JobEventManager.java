package headHuntingCompany;

import headHuntingCompany.models.Company;
import headHuntingCompany.models.JobPost;
import headHuntingCompany.models.JobSeeker;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class JobEventManager {
    private Map<String, List<JobPost>> jobPosts = new HashMap<>();
    private Map<String, List<JobSeeker>> jobSeekers = new HashMap<>();
    private Map<String, Company> companies = new HashMap<>();

    public void addCompany(Company company) {
        companies.put(company.getId(), company);
    }

    public void addJobSeeker(JobSeeker jobSeeker) {
        jobSeeker.getSkills().forEach(skill -> {
            jobSeekers.computeIfAbsent(skill, k -> new ArrayList<>());
            jobSeekers.get(skill).add(jobSeeker);
        });

        checkAvailableJobPostsForJobSeeker(jobSeeker);
    }

    public void postNewJob(JobPost jobPost) {
        jobPost.getSkills().forEach(skill -> {
            jobPosts.computeIfAbsent(skill, k -> new ArrayList<>());
            jobPosts.get(skill).add(jobPost);
        });

        checkAvailableJobSeekersForJobPost(jobPost);
    }

    private void checkAvailableJobPostsForJobSeeker(JobSeeker jobSeeker) {
        List<String> sentJobPosts = new ArrayList<>();

        jobSeeker.getSkills().forEach(skill -> {
            List<JobPost> jobPostsBySkill = jobPosts.get(skill);
            if (jobPostsBySkill != null && jobPostsBySkill.size() > 0) {
                jobPostsBySkill.forEach(jobPost -> {
                    if (sentJobPosts.indexOf(jobPost.getId()) == -1) {
                        sendNotification(jobPost, jobSeeker);

                        sentJobPosts.add(jobPost.getId());
                    }

                    sendNotification(jobPost, jobSeeker);
                });
            }
        });
    }

    private void checkAvailableJobSeekersForJobPost(JobPost jobPost) {
        List<String> sentJobSeekers = new ArrayList<>();

        jobPost.getSkills().forEach(skill -> {
            List<JobSeeker> jobSeekersBySkill = jobSeekers.get(skill);

            if (jobSeekersBySkill != null && jobSeekersBySkill.size() > 0) {
                jobSeekersBySkill.forEach(jobSeeker -> {
                    if (sentJobSeekers.indexOf(jobSeeker.getId()) == -1) {
                        sendNotification(jobPost, jobSeeker);

                        sentJobSeekers.add(jobSeeker.getId());
                    }
                });
            }
        });
    }

    private void sendNotification(JobPost jobPost, JobSeeker jobSeeker) {
        Company company = companies.get(jobPost.getCompanyId());

        if (jobSeeker.onNotification(company, jobPost)) {
            company.onNotification(jobSeeker, jobPost);
        }
    }
}
