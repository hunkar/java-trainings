package headHuntingCompany.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class JobSeeker implements Listener<Company> {
    private String id = UUID.randomUUID().toString();
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String email;
    @NonNull
    private String phone;
    @NonNull
    private List<String> skills;

    @Override
    public boolean onNotification(Company company, JobPost jobPost) {
        System.out.println(company.getName() + "(company) wants to interview with " + this.name);

        return true;
    }
}
