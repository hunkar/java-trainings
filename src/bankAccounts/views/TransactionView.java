package bankAccounts.views;

import bankAccounts.enums.TransactionLogType;
import bankAccounts.models.Transaction;

import java.util.List;

public class TransactionView {
    public static void showAccountTransactions(List<Transaction> transactions) {
        if (transactions == null || transactions.size() == 0) {
            transactionLogNotFound();
            return;
        }

        System.out.println("Transactions:");
        transactions.forEach(transaction -> {
            System.out.printf("%s, %s, %.3f, %.3f\n",
                    getTransactionTypeString(transaction.getTransactionLogType()),
                    transaction.getDate(),
                    transaction.getValue(),
                    transaction.getBalance());
        });

    }

    public static String getTransactionTypeString(TransactionLogType transactionLogType) {
        switch (transactionLogType) {
            case ACCOUNT_OPEN:
                return "Account Open";
            case ACCOUNT_CLOSE:
                return "Account Close";
            case DEPOSIT:
                return "Deposit";
            case WITHDRAW:
                return "Withdraw";
            case LIMIT_CHANGE:
                return "Limit Change";
            case INTEREST_EARNING:
                return "Interest Earning";
            case TRANSFER:
                return "Transfer";
            default:
                return "Unknown";
        }
    }

    public static void transactionLogNotFound() {
        System.out.println("There is no transaction log.");
    }

}
