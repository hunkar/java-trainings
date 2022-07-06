package bankAccounts.models;

import bankAccounts.enums.MaturationType;
import bankAccounts.enums.TransactionLogType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@SuperBuilder
public class RecurringDepositAccount extends Account implements AccountTransactions {
    final double MONTH_BANK_RATE = 0.05;
    final double SIX_MONTH_BANK_RATE = 0.1;
    final double YEAR_BANK_RATE = 0.15;

    @NonNull
    private MaturationType maturationType;
    @NonNull
    @Builder.Default
    private Date maturationStartDate = new Date();
    @NonNull
    private double maturationBalance;

    @NonNull
    @Builder.Default
    private boolean isPaymentToCheckingAccount = false;

    public double getEarningByMaturationType() {
        switch (this.maturationType) {
            case YEAR:
                return this.maturationBalance * YEAR_BANK_RATE * 12;
            case SIX_MONTHS:
                return this.maturationBalance * SIX_MONTH_BANK_RATE * 6;
            case MONTH:
                return this.maturationBalance * MONTH_BANK_RATE * 1;
            default:
                return 0;
        }
    }

    /*
    //This way is not suggested.
    public abstract static class RecurringDepositAccountBuilder<C extends RecurringDepositAccount, B extends RecurringDepositAccountBuilder<C, B>> extends Account.AccountBuilder<C, B>{
        public B balance(double balance) {
            this.maturationBalance = balance;
            return (B)this;
        }
    }
    */

    public Date getMaturationFinishDate() {
        Calendar tempDate = Calendar.getInstance();
        tempDate.setTime(this.maturationStartDate);

        switch (this.maturationType) {
            case YEAR:
                tempDate.set(Calendar.YEAR, tempDate.get(Calendar.YEAR) + 1);
                break;
            case SIX_MONTHS:
                tempDate.set(Calendar.MONTH, tempDate.get(Calendar.MONTH) + 6);
                break;
            case MONTH:
                tempDate.set(Calendar.MONTH, tempDate.get(Calendar.MONTH) + 1);
                break;
        }

        return tempDate.getTime();
    }

    @Override
    public void withdrawMoney(double value) {
        if(getBalance() - value < maturationBalance){
            setBalance(getBalance() - value);
            maturationBalance = getBalance();
        } else {
            setBalance(getBalance() - value);
        }

        addTransactionLog(Transaction.builder()
                .date(new Date())
                .value(value)
                .balance(getBalance())
                .transactionLogType(TransactionLogType.WITHDRAW)
                .build());
    }

    @Override
    public void depositMoney(double value) {
        setBalance(getBalance() + value);
    }
}
