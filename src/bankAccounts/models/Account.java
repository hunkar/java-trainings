package bankAccounts.models;

import bankAccounts.enums.TransactionLogType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
public class Account {
    @NonNull
    @Builder.Default
    private boolean isActive = true;

    @NonNull
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    @NonNull
    private String name;

    @NonNull
    private double balance;

    @NonNull String customerId;

    @NonNull
    @Builder.Default
    List<Transaction> transactions = new ArrayList<>();

    public void addTransactionLog(Transaction transaction) {
        transactions.add(transaction);
    }

    public void close() {
        addTransactionLog(Transaction.builder()
                .date(new Date())
                .balance(balance)
                .value(0)
                .transactionLogType(TransactionLogType.ACCOUNT_CLOSE)
                .build());

        this.isActive = false;
        this.balance = 0;
    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof Account)) return false;

        Account other = (Account) o;
        return (this.id.equals(other.getId()));
    }
}
