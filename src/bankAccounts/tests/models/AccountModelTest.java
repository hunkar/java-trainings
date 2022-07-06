package bankAccounts.tests.models;

import bankAccounts.enums.TransactionLogType;
import bankAccounts.models.Account;
import bankAccounts.models.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class AccountModelTest {
    @Test
    public void createAccount() {
        Account account = Account.builder().name("account").balance(5000).customerId("123").build();

        Assert.assertEquals("account", account.getName());
        Assert.assertEquals(5000, account.getBalance(), 0.0001);
        Assert.assertEquals("123", account.getCustomerId());
    }

    @Test
    public void closeAccount() {
        Account account = Account.builder().name("account").balance(5000).customerId("123").build();
        account.close();

        Assert.assertFalse(account.isActive());
        Assert.assertEquals(0, account.getBalance(), 0.0001);
    }

    @Test
    public void indexOfByStringId() {
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(Account.builder().id("1").name("account").balance(5000).customerId("123").build());
        accounts.add(Account.builder().id("2").name("account").balance(5000).customerId("123").build());

        Account searchAccount = Account.builder().id("2").name("account").customerId("123").build();

        Assert.assertEquals(1, accounts.indexOf(searchAccount));
    }

    @Test
    public void addTransaction() {
        Account account = Account.builder().name("account").balance(5000).customerId("123").build();
        account.addTransactionLog(Transaction.builder()
                .value(100)
                .date(new Date())
                .balance(5000)
                .transactionLogType(TransactionLogType.DEPOSIT)
                .build());

        Assert.assertEquals(1, account.getTransactions().size());
    }

}
