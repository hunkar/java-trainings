package bankAccounts.models;

public interface AccountTransactions {
    void withdrawMoney(double withdrawValue);

    void depositMoney(double depositValue);
}
