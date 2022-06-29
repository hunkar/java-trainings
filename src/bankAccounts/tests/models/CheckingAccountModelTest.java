package bankAccounts.tests.models;

import bankAccounts.models.CheckingAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CheckingAccountModelTest {
    @Test
    public void create() {
        CheckingAccount checkingAccount = CheckingAccount.builder()
                .name("account")
                .balance(5000)
                .customerId("123")
                .dailyWithdrawLimit(1000)
                .build();

        Assertions.assertEquals("account", checkingAccount.getName());
        Assertions.assertEquals(1000, checkingAccount.getDailyWithdrawLimit(), 0.0001);
        Assertions.assertEquals(5000, checkingAccount.getBalance(), 0.0001);
        Assertions.assertEquals("123", checkingAccount.getCustomerId());
    }

    @Test
    public void createWithNegativeLimitNotAllowed() {
        CheckingAccount checkingAccount = CheckingAccount.builder()
                .name("account")
                .balance(5000)
                .customerId("123")
                .dailyWithdrawLimit(-500)
                .build();

        Assertions.assertEquals("account", checkingAccount.getName());
        Assertions.assertEquals(0, checkingAccount.getDailyWithdrawLimit(), 0.0001);
        Assertions.assertEquals(5000, checkingAccount.getBalance(), 0.0001);
        Assertions.assertEquals("123", checkingAccount.getCustomerId());
    }

    @Test
    public void createWithMoreTanThousandLimitNotAllowed() {
        CheckingAccount checkingAccount = CheckingAccount.builder()
                .name("account")
                .balance(5000)
                .customerId("123")
                .dailyWithdrawLimit(1500)
                .build();

        Assertions.assertEquals("account", checkingAccount.getName());
        Assertions.assertEquals(1000, checkingAccount.getDailyWithdrawLimit(), 0.0001);
        Assertions.assertEquals(5000, checkingAccount.getBalance(), 0.0001);
        Assertions.assertEquals("123", checkingAccount.getCustomerId());
    }

    @Test
    public void setDailyLimit() {
        CheckingAccount checkingAccount = CheckingAccount.builder()
                .name("account")
                .balance(5000)
                .customerId("123")
                .dailyWithdrawLimit(750)
                .build();

        checkingAccount.setDailyWithdrawLimit(500);

        Assertions.assertEquals(500, checkingAccount.getDailyWithdrawLimit(), 0.0001);
        Assertions.assertEquals(1, checkingAccount.getTransactions().size());
    }

    @Test
    public void setDailyLimitNegativeNotAllowed() {
        CheckingAccount checkingAccount = CheckingAccount.builder()
                .name("account")
                .balance(5000)
                .customerId("123")
                .dailyWithdrawLimit(750)
                .build();

        checkingAccount.setDailyWithdrawLimit(-500);

        Assertions.assertEquals(0, checkingAccount.getDailyWithdrawLimit(), 0.0001);
    }

    @Test
    public void setDailyLimitMoreThanThousandNotAllowed() {
        CheckingAccount checkingAccount = CheckingAccount.builder()
                .name("account")
                .balance(5000)
                .customerId("123")
                .dailyWithdrawLimit(750)
                .build();

        checkingAccount.setDailyWithdrawLimit(1500);

        Assertions.assertEquals(1000, checkingAccount.getDailyWithdrawLimit(), 0.0001);
    }

    @Test
    public void withdrawMoney() {
        CheckingAccount checkingAccount = CheckingAccount.builder()
                .name("account")
                .balance(5000)
                .customerId("123")
                .dailyWithdrawLimit(1000)
                .build();

        checkingAccount.withdrawMoney(1000);

        Assertions.assertEquals(4000, checkingAccount.getBalance(), 0.0001);
    }

    @Test
    public void getDailyWithdrawValue() {
        CheckingAccount checkingAccount = CheckingAccount.builder()
                .name("account")
                .balance(5000)
                .customerId("123")
                .dailyWithdrawLimit(1000)
                .build();

        String dayStamp = "27-06-2022";

        Assertions.assertEquals(0, checkingAccount.getDailyWithdrawValue(dayStamp), 0.0001);
        checkingAccount.withdrawMoney(200);
        Assertions.assertEquals(200, checkingAccount.getDailyWithdrawValue(dayStamp), 0.0001);
    }

    @Test
    public void getDailyWithdrawValueWithDifferentStamp() {
        CheckingAccount checkingAccount = CheckingAccount.builder()
                .name("account")
                .balance(5000)
                .customerId("123")
                .dailyWithdrawLimit(1000)
                .build();

        String dayStamp = "27-06-2022";

        Assertions.assertEquals(0, checkingAccount.getDailyWithdrawValue(dayStamp), 0.0001);
        checkingAccount.withdrawMoney(200);
        Assertions.assertEquals(200, checkingAccount.getDailyWithdrawValue(dayStamp), 0.0001);

        dayStamp = "28-06-2022";
        Assertions.assertEquals(0, checkingAccount.getDailyWithdrawValue(dayStamp), 0.0001);
    }

    @Test
    public void deposit() {
        CheckingAccount checkingAccount = CheckingAccount.builder()
                .name("account")
                .balance(5000)
                .customerId("123")
                .dailyWithdrawLimit(1000)
                .build();
        checkingAccount.depositMoney(1000);

        Assertions.assertEquals(6000, checkingAccount.getBalance(), 0.0001);
    }
}
