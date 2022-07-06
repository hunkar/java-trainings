package bankAccounts.tests.models;

import bankAccounts.enums.MaturationType;
import bankAccounts.models.RecurringDepositAccount;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class RecurringAccountModelTest {
    @Test
    public void create() {
        Date maturationDate = new Date();
        RecurringDepositAccount account = RecurringDepositAccount.builder()
                .name("recurring deposit account")
                .maturationStartDate(maturationDate)
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build();

        Assert.assertEquals("recurring deposit account", account.getName());
        Assert.assertEquals(maturationDate, account.getMaturationStartDate());
        Assert.assertTrue(account.isPaymentToCheckingAccount());
        Assert.assertEquals("123", account.getCustomerId());
        Assert.assertEquals(500, account.getBalance(), 0.00001);
        Assert.assertEquals(400, account.getMaturationBalance(), 0.00001);
        Assert.assertEquals(MaturationType.MONTH, account.getMaturationType());
    }

    @Test
    public void getEarningByMaturationType() {
        Date maturationDate = new Date();
        RecurringDepositAccount account = RecurringDepositAccount.builder()
                .name("recurring deposit account")
                .maturationStartDate(maturationDate)
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(1000)
                .maturationBalance(1000)
                .maturationType(MaturationType.MONTH)
                .build();

        Assert.assertEquals(50, account.getEarningByMaturationType(), 0.00001);

        account.setMaturationType(MaturationType.SIX_MONTHS);
        Assert.assertEquals(600, account.getEarningByMaturationType(), 0.00001);

        account.setMaturationType(MaturationType.YEAR);
        Assert.assertEquals(1800, account.getEarningByMaturationType(), 0.00001);
    }

    @Test
    public void getMaturationFinishDate() {
        LocalDateTime maturationDate = LocalDateTime.now();
        RecurringDepositAccount account = RecurringDepositAccount.builder()
                .name("recurring deposit account")
                .maturationStartDate(Date.from(maturationDate.atZone(ZoneId.systemDefault()).toInstant()))
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(1000)
                .maturationBalance(1000)
                .maturationType(MaturationType.MONTH)
                .build();

        Assert.assertEquals(maturationDate.plusMonths(1), LocalDateTime.ofInstant(account.getMaturationFinishDate().toInstant(), ZoneId.systemDefault()));

        account.setMaturationType(MaturationType.SIX_MONTHS);
        Assert.assertEquals(maturationDate.plusMonths(6), LocalDateTime.ofInstant(account.getMaturationFinishDate().toInstant(), ZoneId.systemDefault()));

        account.setMaturationType(MaturationType.YEAR);
        Assert.assertEquals(maturationDate.plusYears(1), LocalDateTime.ofInstant(account.getMaturationFinishDate().toInstant(), ZoneId.systemDefault()));
    }

    @Test
    public void withdrawMoneyWithDifferentBalances() {
        RecurringDepositAccount recurringDepositAccount = RecurringDepositAccount.builder()
                .name("account")
                .balance(4000)
                .maturationBalance(4000)
                .maturationType(MaturationType.MONTH)
                .maturationStartDate(new Date())
                .customerId("123")
                .build();

        recurringDepositAccount.withdrawMoney(1000);

        Assert.assertEquals(3000, recurringDepositAccount.getBalance(), 0.0001);
        Assert.assertEquals(3000, recurringDepositAccount.getMaturationBalance(), 0.0001);
    }

    @Test
    public void withdrawMoneyWithSameBalances() {
        RecurringDepositAccount recurringDepositAccount = RecurringDepositAccount.builder()
                .name("account")
                .balance(5000)
                .maturationBalance(4000)
                .maturationType(MaturationType.MONTH)
                .maturationStartDate(new Date())
                .customerId("123")
                .build();

        recurringDepositAccount.withdrawMoney(500);

        Assert.assertEquals(4500, recurringDepositAccount.getBalance(), 0.0001);
        Assert.assertEquals(4000, recurringDepositAccount.getMaturationBalance(), 0.0001);
        Assert.assertEquals(1, recurringDepositAccount.getTransactions().size());
    }

    @Test
    public void deposit() {
        RecurringDepositAccount recurringDepositAccount = RecurringDepositAccount.builder()
                .name("account")
                .balance(5000)
                .maturationBalance(4000)
                .maturationType(MaturationType.MONTH)
                .maturationStartDate(new Date())
                .customerId("123")
                .build();

        recurringDepositAccount.depositMoney(1000);

        Assert.assertEquals(6000, recurringDepositAccount.getBalance(), 0.0001);
        Assert.assertEquals(4000, recurringDepositAccount.getMaturationBalance(), 0.0001);
        Assert.assertEquals(0, recurringDepositAccount.getTransactions().size());
    }
}
