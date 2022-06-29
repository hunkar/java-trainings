package bankAccounts.tests.views;

import bankAccounts.controllers.AccountController;
import bankAccounts.enums.AccountType;
import bankAccounts.enums.MaturationType;
import bankAccounts.models.Account;
import bankAccounts.models.CheckingAccount;
import bankAccounts.models.RecurringDepositAccount;
import bankAccounts.views.AccountView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import static bankAccounts.models.DataHolder.accounts;

public class AccountViewTest {
    @Test
    public void getAccountTypeRecurringDepositAccount() {
        RecurringDepositAccount account = RecurringDepositAccount.builder()
                .name("recurring deposit account")
                .maturationStartDate(new Date())
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build();

        Assertions.assertEquals(AccountView.getAccountTypeString(account), "Recurring Deposit Account");
    }

    @Test
    public void getAccountTypeCheckingAccount() {
        CheckingAccount account = CheckingAccount.builder()
                .name("recurring deposit account")
                .customerId("123")
                .balance(500)
                .build();

        Assertions.assertEquals(AccountView.getAccountTypeString(account), "Checking Deposit Account");
    }

    @Test
    public void getAccountTypeUnknownAccount() {
        Account account = Account.builder()
                .name("recurring deposit account")
                .customerId("123")
                .balance(500)
                .build();

        Assertions.assertEquals(AccountView.getAccountTypeString(account), "Unknown");
    }

    @Test
    public void getAccountType() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);

        AccountType result = AccountView.getAccountType();

        Assertions.assertEquals(result, AccountType.RECURRING_DEPOSIT);

        System.setIn(sysInBackup);
    }

    @Test
    public void getAccount() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(Account.builder().name("ac-1").customerId("c-1").balance(100).build());
        accounts.add(Account.builder().name("ac-2").customerId("c-1").balance(100).build());
        accounts.add(Account.builder().name("ac-3").customerId("c-1").balance(100).build());


        Account result = AccountView.getAccount(accounts);

        Assertions.assertEquals(result.getId(), accounts.get(1).getId());

        System.setIn(sysInBackup);
    }

    @Test
    public void getNoAccount() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("5".getBytes());
        System.setIn(in);

        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(Account.builder().name("ac-1").customerId("c-1").balance(100).build());
        accounts.add(Account.builder().name("ac-2").customerId("c-1").balance(100).build());
        accounts.add(Account.builder().name("ac-3").customerId("c-1").balance(100).build());


        Account result = AccountView.getAccount(accounts);

        Assertions.assertNull(result);

        System.setIn(sysInBackup);
    }

    @Test
    public void createRecurringDepositAccount() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("account\n1200\n1\n1\n".getBytes());
        System.setIn(in);


        RecurringDepositAccount result = AccountView.createRecurringAccount("123");

        Assertions.assertEquals(result.getName(), "account");
        Assertions.assertEquals(result.getBalance(), 1200, 0.0001);
        Assertions.assertEquals(result.getMaturationType(), MaturationType.SIX_MONTHS);
        Assertions.assertFalse(result.isPaymentToCheckingAccount());

        System.setIn(sysInBackup);
    }

    @Test
    public void createCheckingAccount() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("account2\n1200\n750".getBytes());
        System.setIn(in);


        CheckingAccount result = AccountView.createCheckingAccount("123");

        Assertions.assertEquals(result.getName(), "account2");
        Assertions.assertEquals(result.getBalance(), 1200, 0.0001);
        Assertions.assertEquals(result.getDailyWithdrawLimit(), 750, 0.0001);

        System.setIn(sysInBackup);
    }

    @Test
    public void depositToAccount() {
        accounts.clear();
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("1200".getBytes());
        System.setIn(in);

        AccountController controller = new AccountController();

        CheckingAccount account = CheckingAccount.builder().id("111").name("account").balance(300).customerId("123").build();
        controller.createCheckingAccount(account);


        AccountView.depositToAccount(controller, account);

        Assertions.assertEquals(account.getBalance(), 1500, 0.0001);

        System.setIn(sysInBackup);
    }

    @Test
    public void withdrawFromAccount() {
        accounts.clear();
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("300".getBytes());
        System.setIn(in);

        AccountController controller = new AccountController();

        CheckingAccount account = CheckingAccount.builder()
                .id("111")
                .name("account")
                .balance(800)
                .dailyWithdrawLimit(1000)
                .customerId("123")
                .build();

        controller.createCheckingAccount(account);


        AccountView.witdrawFromAccount(controller, account);

        Assertions.assertEquals(account.getBalance(), 500, 0.0001);

        System.setIn(sysInBackup);
    }

    @Test
    public void deleteAccount() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);

        AccountController controller = new AccountController();

        CheckingAccount account = CheckingAccount.builder()
                .id("111")
                .name("account")
                .balance(800)
                .dailyWithdrawLimit(1000)
                .customerId("123")
                .build();

        controller.createCheckingAccount(account);


        AccountView.deleteAccount(controller, account);

        Assertions.assertFalse(account.isActive());

        System.setIn(sysInBackup);
    }
}
