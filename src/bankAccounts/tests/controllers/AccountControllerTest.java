package bankAccounts.tests.controllers;

import bankAccounts.controllers.AccountController;
import bankAccounts.enums.ControllerResponseType;
import bankAccounts.enums.MaturationType;
import bankAccounts.enums.TransactionLogType;
import bankAccounts.models.Account;
import bankAccounts.models.CheckingAccount;
import bankAccounts.models.RecurringDepositAccount;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static bankAccounts.models.DataHolder.accounts;

public class AccountControllerTest {
    @Test
    public void createCheckingAccount() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        ControllerResponseType controllerResponseType = controller.createCheckingAccount(CheckingAccount.builder()
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        Assert.assertEquals(ControllerResponseType.SUCCESSFUL, controllerResponseType);
    }

    @Test
    public void createRecurringAccountWithoutCheckingAccountNotAllowed() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        ControllerResponseType controllerResponseType = controller.createRecurringDepositAccount(RecurringDepositAccount.builder()
                .name("recurring deposit account")
                .maturationStartDate(new Date())
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build());

        Assert.assertEquals(ControllerResponseType.ERROR_CREATE_CHECKING_FIRST, controllerResponseType);
    }

    @Test
    public void createRecurringAccountAfterCheckingAccount() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        ControllerResponseType controllerResponseType = controller.createRecurringDepositAccount(RecurringDepositAccount.builder()
                .name("recurring deposit account")
                .maturationStartDate(new Date())
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build());

        Assert.assertEquals(ControllerResponseType.SUCCESSFUL, controllerResponseType);
    }

    @Test
    public void deleteExistAccount() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        controller.createRecurringDepositAccount(RecurringDepositAccount.builder()
                .id("2")
                .name("recurring deposit account")
                .maturationStartDate(new Date())
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build());

        ControllerResponseType controllerResponseType = controller.deleteAccount("1");

        Assert.assertEquals(ControllerResponseType.SUCCESSFUL, controllerResponseType);
        Assert.assertEquals(accounts.size(), 2);
        Assert.assertFalse(accounts.get(0).isActive());

    }

    @Test
    public void deleteNotExistAccount() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        controller.createRecurringDepositAccount(RecurringDepositAccount.builder()
                .id("2")
                .name("recurring deposit account")
                .maturationStartDate(new Date())
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build());

        ControllerResponseType controllerResponseType = controller.deleteAccount("4");

        Assert.assertEquals(ControllerResponseType.ACCOUNT_NOT_FOUND, controllerResponseType);
        Assert.assertEquals(accounts.size(), 2);

    }

    @Test
    public void depositNegativeValueNotAllowed() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        ControllerResponseType controllerResponseType = controller.depositToAccount(accounts.get(0),
                -100,
                TransactionLogType.DEPOSIT);

        Assert.assertEquals(ControllerResponseType.INVALID_DEPOSIT_VALUE, controllerResponseType);
    }

    @Test
    public void depositExistAccount() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        ControllerResponseType controllerResponseType = controller.depositToAccount(accounts.get(0),
                100,
                TransactionLogType.DEPOSIT);

        Assert.assertEquals(ControllerResponseType.SUCCESSFUL, controllerResponseType);
        Assert.assertEquals(600, accounts.get(0).getBalance(), 0.0001);
    }

    @Test
    public void depositNotExistAccount() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        Account notExistAccount = Account.builder()
                .name("account")
                .id("1212")
                .balance(500)
                .customerId("123")
                .build();

        ControllerResponseType controllerResponseType = controller.depositToAccount(notExistAccount,
                100,
                TransactionLogType.DEPOSIT);

        Assert.assertEquals(ControllerResponseType.ACCOUNT_NOT_FOUND, controllerResponseType);
        Assert.assertEquals(500, accounts.get(0).getBalance(), 0.0001);
    }

    @Test
    public void depositRecurringAccountBeforeMaturation() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        controller.createRecurringDepositAccount(RecurringDepositAccount.builder()
                .id("2")
                .name("recurring deposit account")
                .maturationStartDate(new Date())
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build());


        ControllerResponseType controllerResponseType = controller.depositToAccount(accounts.get(1),
                100,
                TransactionLogType.DEPOSIT);

        Assert.assertEquals(ControllerResponseType.SUCCESSFUL, controllerResponseType);

        Assert.assertEquals(600, accounts.get(1).getBalance(), 0.0001);
        Assert.assertEquals(500, ((RecurringDepositAccount) accounts.get(1)).getMaturationBalance(), 0.0001);
    }

    @Test
    public void depositRecurringAccountInMaturation() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        LocalDateTime now = LocalDateTime.now().minusDays(4);

        controller.createRecurringDepositAccount(RecurringDepositAccount.builder()
                .id("2")
                .name("recurring deposit account")
                .maturationStartDate(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build());

        ControllerResponseType controllerResponseType = controller.depositToAccount(accounts.get(1),
                100,
                TransactionLogType.DEPOSIT);

        Assert.assertEquals(ControllerResponseType.SUCCESSFUL, controllerResponseType);

        Assert.assertEquals(600, accounts.get(1).getBalance(), 0.0001);
        Assert.assertEquals(400, ((RecurringDepositAccount) accounts.get(1)).getMaturationBalance(), 0.0001);
    }

    @Test
    public void withdrawNegativeValueNotAllowed() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        ControllerResponseType controllerResponseType = controller.withdrawFromAccount(accounts.get(0),
                -100);

        Assert.assertEquals(ControllerResponseType.INVALID_WITHDRAW_VALUE, controllerResponseType);
        Assert.assertEquals(500, accounts.get(0).getBalance(), 0.0001);
    }

    @Test
    public void withdrawValueMoreThanBalanceNotAllowed() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        ControllerResponseType controllerResponseType = controller.withdrawFromAccount(accounts.get(0),
                600);

        Assert.assertEquals(ControllerResponseType.INVALID_WITHDRAW_VALUE, controllerResponseType);
        Assert.assertEquals(500, accounts.get(0).getBalance(), 0.0001);
    }

    @Test
    public void withdrawValueMoreThanLimit() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(1000)
                .dailyWithdrawLimit(750)
                .build());

        ControllerResponseType controllerResponseType = controller.withdrawFromAccount(accounts.get(0),
                1000);

        Assert.assertEquals(ControllerResponseType.DAILY_LIMIT_EXCEEDED, controllerResponseType);
        Assert.assertEquals(1000, accounts.get(0).getBalance(), 0.0001);
    }

    @Test
    public void withdrawRecurringAccountBeforeMaturation() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        controller.createRecurringDepositAccount(RecurringDepositAccount.builder()
                .id("2")
                .name("recurring deposit account")
                .maturationStartDate(new Date())
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build());


        ControllerResponseType controllerResponseType = controller.withdrawFromAccount(accounts.get(1),
                100);

        Assert.assertEquals(ControllerResponseType.SUCCESSFUL, controllerResponseType);

        Assert.assertEquals(400, accounts.get(1).getBalance(), 0.0001);
        Assert.assertEquals(400, ((RecurringDepositAccount) accounts.get(1)).getMaturationBalance(), 0.0001);
    }

    @Test
    public void withdrawRecurringAccountInMaturation() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        LocalDateTime now = LocalDateTime.now().minusDays(4);

        controller.createRecurringDepositAccount(RecurringDepositAccount.builder()
                .id("2")
                .name("recurring deposit account")
                .maturationStartDate(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build());

        ControllerResponseType controllerResponseType = controller.withdrawFromAccount(accounts.get(1),
                100);

        Assert.assertEquals(ControllerResponseType.NO_WITHDRAW_IN_MATURATION, controllerResponseType);

        Assert.assertEquals(500, accounts.get(1).getBalance(), 0.0001);
        Assert.assertEquals(400, ((RecurringDepositAccount) accounts.get(1)).getMaturationBalance(), 0.0001);
    }

    @Test
    public void getExistAccountById() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        LocalDateTime now = LocalDateTime.now().minusDays(4);

        controller.createRecurringDepositAccount(RecurringDepositAccount.builder()
                .id("2")
                .name("recurring deposit account")
                .maturationStartDate(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build());

        Account account = controller.getAccountById("2");

        Assert.assertEquals("2", account.getId());
    }

    @Test
    public void getNotExistAccountById() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        Account account = controller.getAccountById("2");

        Assert.assertNull(account);
    }

    @Test
    public void getCustomerAccounts() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("2")
                .name("checking account")
                .customerId("124")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        Assert.assertEquals(1, controller.getAccountListByCustomer("123").size());
    }

    @Test
    public void getCheckingAccountsByCustomer() {
        accounts = new ArrayList<>();
        AccountController controller = new AccountController();

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("1")
                .name("checking account")
                .customerId("123")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        controller.createCheckingAccount(CheckingAccount.builder()
                .id("2")
                .name("checking account")
                .customerId("124")
                .balance(500)
                .dailyWithdrawLimit(750)
                .build());

        controller.createRecurringDepositAccount(RecurringDepositAccount.builder()
                .id("3")
                .name("recurring deposit account")
                .maturationStartDate(new Date())
                .isPaymentToCheckingAccount(true)
                .customerId("123")
                .balance(500)
                .maturationBalance(400)
                .maturationType(MaturationType.MONTH)
                .build());

        Account account = controller.getAccountById("2");

        Assert.assertEquals(1, controller.getCheckingAccountListByCustomer("123").size());
    }
}
