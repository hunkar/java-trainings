package headHuntingCompany.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class JobPost {
    private String id = UUID.randomUUID().toString();
    @NonNull
    private String description;
    @NonNull
    private String title;
    @NonNull
    private List<String> skills;
    @NonNull
    private String companyId;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JobPost)) return false;

        JobPost other = (JobPost) o;
        return (this.id.equals(other.getId()));
    }
}
