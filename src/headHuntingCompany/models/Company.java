package headHuntingCompany.models;

import headHuntingCompany.JobEventListener;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mockito.Mock;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@RequiredArgsConstructor
public class Company extends JobEventListener {
    private String id = UUID.randomUUID().toString();
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String phone;
    @Mock
    private Map<String, List<JobPost>> jobPosts = new HashMap<>();

    public void addJob(JobPost jobPost) {
        jobPost.getSkills().forEach(skill -> {
            jobPosts.computeIfAbsent(skill, k -> new ArrayList<>());
            jobPosts.get(skill).add(jobPost);
        });

        this.jobEventManager.postJob(this, jobPost);
    }

    public List<JobPost> getjobPostsList(List<String> skills){
        Map<String, List<JobPost>> filteredMap = this.jobPosts.entrySet()
                .stream().filter(x->skills.contains(x.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return Stream.of(filteredMap.values().stream().flatMap(Collection::stream))
                .flatMap(x -> x)
                .collect(Collectors.toList());
    }


    @Override
    public void notifyCandidateResponse(String jobSeekerName, JobPost jobPost){
        System.out.println(jobSeekerName + "(job seeker) accepted to interview for job " + jobPost.getTitle());
    }
}
