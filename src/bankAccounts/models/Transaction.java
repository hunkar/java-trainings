package bankAccounts.models;

import bankAccounts.enums.TransactionLogType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class Transaction {
    @NonNull
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @NonNull
    Date date;

    @NonNull
    double value;

    @NonNull
    double balance;

    @NonNull TransactionLogType transactionLogType;
}
