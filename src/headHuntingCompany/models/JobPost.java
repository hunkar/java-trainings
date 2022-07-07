package headHuntingCompany.models;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
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


}
