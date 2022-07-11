package headHuntingCompany;

import headHuntingCompany.models.Company;
import headHuntingCompany.models.JobPost;
import headHuntingCompany.models.JobSeeker;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class JobEventManagerImpl implements JobEventManager {
    private Map<String, List<JobSeeker>> jobSeekers = new HashMap<>();
    private Map<String, Company> companies = new HashMap<>();

    @Override
    public void register(JobEventListener listener) {
        if (listener instanceof Company) {
            Company company = (Company) listener;
            companies.put(company.getId(), company);
        } else if (listener instanceof JobSeeker) {
            addJobSeeker((JobSeeker) listener);
        }

    }

    private void addJobSeeker(JobSeeker jobSeeker) {
        jobSeeker.getSkills().forEach(skill -> {
            jobSeekers.computeIfAbsent(skill, k -> new ArrayList<>());
            jobSeekers.get(skill).add(jobSeeker);
        });

        checkAvailableJobPostsForJobSeeker(jobSeeker);
    }

    @Override
    public void postJob(Company company, JobPost jobPost) {
        companies.putIfAbsent(company.getId(), company);
        checkAvailableJobSeekersForJobPost(jobPost);
    }

    @Override
    public void respondToJobOffer(String jobSeekerName, JobPost jobPost) {
        Company company = companies.get(jobPost.getCompanyId());
        company.notifyCandidateResponse(jobSeekerName, jobPost);
    }

    private void checkAvailableJobPostsForJobSeeker(JobSeeker jobSeeker) {
        List<JobPost> jobPostsBySkill = companies.values()
                .stream()
                .map(company -> company.getjobPostsList(jobSeeker.getSkills()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<String> sentJobPosts = new ArrayList<>();

        jobPostsBySkill.forEach(jobPost -> {
            if (!sentJobPosts.contains(jobPost.getId())) {
                jobSeeker.notifyNewJob(companies.get(jobPost.getCompanyId()).getName(), jobPost);

                sentJobPosts.add(jobPost.getId());
            }
        });
    }

    private void checkAvailableJobSeekersForJobPost(JobPost jobPost) {
        List<String> sentJobSeekers = new ArrayList<>();

        jobPost.getSkills().forEach(skill -> {
            List<JobSeeker> jobSeekersBySkill = jobSeekers.get(skill);

            if (jobSeekersBySkill != null) {
                jobSeekersBySkill.forEach(jobSeeker -> {
                    if (!sentJobSeekers.contains(jobSeeker.getId())) {
                        jobSeeker.notifyNewJob(companies.get(jobPost.getCompanyId()).getName(), jobPost);

                        sentJobSeekers.add(jobSeeker.getId());
                    }
                });
            }
        });
    }
}
