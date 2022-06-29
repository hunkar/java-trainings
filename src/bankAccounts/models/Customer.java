package bankAccounts.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
public class Customer {
    @NonNull
    @Builder.Default
    private boolean isActive = true;

    @NonNull
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @NonNull String name;

    @NonNull String address;

    @NonNull int nationalNumber;
}
