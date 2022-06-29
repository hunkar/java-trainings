package bankAccounts.views;

import bankAccounts.controllers.AccountController;
import bankAccounts.enums.AccountType;
import bankAccounts.enums.ControllerResponseType;
import bankAccounts.enums.MaturationType;
import bankAccounts.enums.TransactionLogType;
import bankAccounts.models.Account;
import bankAccounts.models.CheckingAccount;
import bankAccounts.models.Customer;
import bankAccounts.models.RecurringDepositAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.IntStream;

import static bankAccounts.views.MainView.getListSelection;
import static bankAccounts.views.MainView.responseParser;
import static bankAccounts.views.TransactionView.showAccountTransactions;

public class AccountView {
    public static String getAccountTypeString(Account account) {
        if (account instanceof RecurringDepositAccount) {
            return "Recurring Deposit Account";
        } else if (account instanceof CheckingAccount) {
            return "Checking Deposit Account";
        } else {
            return "Unknown";
        }
    }

    public static AccountType getAccountType() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("0 - Recurring Deposit Account, 1 - Checking Account");
        System.out.print("Enter your choice: ");

        int result = scanner.nextInt();

        if (result == 0) {
            return AccountType.RECURRING_DEPOSIT;
        } else {
            return AccountType.CHECKING;
        }
    }

    public static Account getAccount(ArrayList<Account> accounts) {
        Scanner scanner = new Scanner(System.in);

        if (accounts.size() > 0) {
            IntStream.range(0, accounts.size())
                    .forEach(index -> System.out.printf("%d - %s\n", index, accounts.get(index).getName()));
        } else {
            notExistAccount();
            return null;
        }

        System.out.print("Enter your choice: ");
        int result = scanner.nextInt();

        if (result < accounts.size()) {
            return accounts.get(result);
        } else {
            notExistAccount();
            return null;
        }
    }

    public static Account createAccount(AccountController accountController, Customer customer) {
        AccountType accountType = getAccountType();
        ControllerResponseType response;
        Account account;

        if (accountType == AccountType.RECURRING_DEPOSIT) {
            account = createRecurringAccount(customer.getId());
            response = accountController.createRecurringDepositAccount((RecurringDepositAccount) account);
        } else {
            account = createCheckingAccount(customer.getId());
            response = accountController.createCheckingAccount((CheckingAccount) account);
        }

        responseParser(response);
        return account;
    }

    public static RecurringDepositAccount createRecurringAccount(String customerId) {
        Scanner scanner = new Scanner(System.in);

        //Account name
        System.out.print("Please enter name of account: ");
        String name = scanner.nextLine();

        //Account balance
        System.out.print("Please enter balance: ");
        double balance = scanner.nextDouble();

        //Account type
        System.out.println(MaturationType.MONTH.getNumVal() + " - 1 month");
        System.out.println(MaturationType.SIX_MONTHS.getNumVal() + " - 6 months");
        System.out.println(MaturationType.YEAR.getNumVal() + " - 12 months");

        System.out.print("Enter your choice for maturation type: ");
        int maturationChoice = scanner.nextInt();

        //Payment type
        System.out.println("0 - Get payment to selected checking account.");
        System.out.println("1 - Get payment to recurring account.");
        System.out.print("Enter your choice for payment: ");
        boolean paymentType = scanner.nextInt() == 0;

        return RecurringDepositAccount.builder()
                .maturationBalance(balance)
                .balance(balance)
                .name(name)
                .maturationType(MaturationType.values()[maturationChoice])
                .isPaymentToCheckingAccount(paymentType)
                .customerId(customerId)
                .maturationStartDate(new Date())
                .build();
    }

    public static CheckingAccount createCheckingAccount(String customerId) {
        Scanner scanner = new Scanner(System.in);

        //Account name
        System.out.print("Please enter name of account: ");
        String name = scanner.nextLine();

        //Account balance
        System.out.print("Please enter balance: ");
        double balance = scanner.nextDouble();

        //Account limit
        System.out.print("Please enter limit: ");
        double limit = scanner.nextDouble();

        return CheckingAccount.builder()
                .dailyWithdrawLimit(limit)
                .name(name)
                .balance(balance)
                .customerId(customerId)
                .build();
    }

    public static void depositToAccount(AccountController accountController, Account account) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter value: ");

        double value =  scanner.nextDouble();

        ControllerResponseType controllerResponse = accountController.depositToAccount(account, value, TransactionLogType.DEPOSIT);
        responseParser(controllerResponse);
    }

    public static void witdrawFromAccount(AccountController accountController, Account account) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter value: ");

        double value =  scanner.nextDouble();

        ControllerResponseType controllerResponse = accountController.withdrawFromAccount(account, value);
        responseParser(controllerResponse);
    }

    public static void deleteAccount(AccountController accountController, Account account) {
        System.out.println("Account type: " + getAccountTypeString(account));
        System.out.println("Account id: " + account.getId());
        System.out.println("Account name: " + account.getName());
        System.out.println("Account balance: " + account.getBalance());

        System.out.println("Account is being deleted.");

        System.out.println("Are you sure?");

        ArrayList<String> processOptions = new ArrayList<>();
        processOptions.add("Yes");
        processOptions.add("No");

        int selection = getListSelection(processOptions);

        if (selection == 0) {
            ControllerResponseType controllerResponse = accountController.deleteAccount(account.getId());
            responseParser(controllerResponse);
        }
    }

    public static void showAccountDetail(AccountController accountController, String accountId) {
        Account account = accountController.getAccountById(accountId);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("Account type: " + getAccountTypeString(account));
        System.out.println("Account id: " + account.getId());
        System.out.println("Account name: " + account.getName());
        System.out.println("Account balance: " + account.getBalance());

        showAccountTransactions(account.getTransactions());
    }

    public static void notExistAccount() {
        System.out.println("There is no account.");
    }
}
