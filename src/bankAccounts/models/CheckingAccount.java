package bankAccounts.models;

import bankAccounts.enums.TransactionLogType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
public class CheckingAccount extends Account implements AccountTransactions {
    @NonNull
    @Builder.Default
    private double dailyWithdrawLimit = 100;

    private String dayStamp = "";

    private double dailyWithdrawValue;

    protected CheckingAccount(final CheckingAccount.CheckingAccountBuilder<?, ?> b) {
        super(b);

        dailyWithdrawLimit = Math.max(Math.min(b.dailyWithdrawLimit$value, 1000), 0);
    }

    public double getDailyWithdrawValue(String dayStamp) {
        if (!dayStamp.equals(this.dayStamp)) {
            this.dayStamp = dayStamp;
            this.dailyWithdrawValue = 0;
        }

        return this.dailyWithdrawValue;
    }

    public void setDailyWithdrawLimit(int dailyWithdrawLimit) {
        this.dailyWithdrawLimit = Math.max(Math.min(dailyWithdrawLimit, 1000), 0);
        this.addTransactionLog(Transaction.builder()
                .date(new Date())
                .value(dailyWithdrawLimit)
                .balance(getBalance())
                .transactionLogType(TransactionLogType.LIMIT_CHANGE)
                .build());
    }

    @Override
    public void withdrawMoney(double value) {
        setBalance(getBalance() - value);
        addTransactionLog(Transaction.builder()
                .date(new Date())
                .value(value)
                .balance(getBalance())
                .transactionLogType(TransactionLogType.WITHDRAW)
                .build());

        this.dailyWithdrawValue += value;
    }

    @Override
    public void depositMoney(double value) {
        setBalance(getBalance() + value);
    }
}
