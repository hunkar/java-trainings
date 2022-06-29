package bankAccounts.views;

import bankAccounts.controllers.AccountController;
import bankAccounts.controllers.CustomerController;
import bankAccounts.enums.ControllerResponseType;
import bankAccounts.models.Account;
import bankAccounts.models.Customer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

import static bankAccounts.views.AccountView.*;
import static bankAccounts.views.CustomerView.*;

public class MainView {
    public static void responseParser(ControllerResponseType response) {
        switch (response) {
            case ERROR_CREATE_CHECKING_FIRST:
                System.out.println("You have to create checking account first.");
                break;
            case SUCCESSFUL:
                System.out.println("Process is successful.");
                break;
            case INVALID_DEPOSIT_VALUE:
                System.out.println("Invalid deposit value. Enter positive value.");
                break;
            case ACCOUNT_NOT_FOUND:
                System.out.println("Account not found.");
                break;
            case INVALID_WITHDRAW_VALUE:
                System.out.println("Invalid withdraw value. Enter positive and less value than your balance.");
                break;
            case NO_WITHDRAW_IN_MATURATION:
                System.out.println("You cannot withdraw in your maturation period.");
                break;
            case DAILY_LIMIT_EXCEEDED:
                System.out.println("Value that your entered exceed your daily limit.");
                break;
            case CUSTOMER_NOT_FOUND:
                System.out.println("Customer not found.");
                break;
            case CUSTOMER_EXIST:
                System.out.println("Customer who has the same national id exists.");
                break;
            default:
                System.out.println("Unknown error.");
                break;
        }
    }

    public static int getListSelection(ArrayList<String> list) {
        Scanner scanner = new Scanner(System.in);

        IntStream.range(0, list.size())
                .forEach(index -> System.out.printf("%d - %s\n", index, list.get(index)));

        System.out.print("Enter your choice: ");
        int result = scanner.nextInt();

        if (result >= 0 && result < list.size()) {
            return result;
        } else {
            return -1;
        }
    }

    public static void appScreen(AccountController accountController, CustomerController customerController){
        while (true) {
            System.out.println("Select Process");
            ArrayList<String> processOptions = new ArrayList<>();
            processOptions.add("Customer List");
            processOptions.add("Create Customer");

            int selection = getListSelection(processOptions);

            switch (selection) {
                case 0: {
                    customerProcesses(accountController, customerController);
                    break;
                }
                case 1: {
                    createCustomer(customerController);
                    break;
                }
            }
        }
    }

    public static void customerProcesses(AccountController accountController, CustomerController customerController) {
        Customer customer = getCustomer(customerController.getCustomerList());
        if (customer != null) {
            while (true) {
                System.out.println("Select Process");
                ArrayList<String> processOptions = new ArrayList<>();
                processOptions.add("Show Accounts");
                processOptions.add("Add New Account");
                processOptions.add("Show User Detail");
                processOptions.add("Delete Customer");
                processOptions.add("Back");

                int selection = getListSelection(processOptions);
                switch (selection) {
                    case 0: {
                        accountProcesses(accountController, customerController, customer);

                        break;
                    }
                    case 1: {
                        createAccount(accountController, customer);
                        break;
                    }
                    case 2: {
                        showCustomerDetail(customerController, customer.getId());
                        break;
                    }
                    case 3: {
                        deleteCustomer(customerController, customer);
                        break;
                    }
                    default: {
                        return;
                    }
                }
            }
        }
    }

    public static void accountProcesses(AccountController accountController, CustomerController customerController, Customer customer) {
        Account account = getAccount(accountController.getAccountListByCustomer(customer.getId()));

        if (account != null) {
            while (true) {
                System.out.println("Select Process");
                ArrayList<String> processOptions = new ArrayList<>();
                processOptions.add("Show Detail");
                processOptions.add("Deposit");
                processOptions.add("Withdraw");
                processOptions.add("Delete Account");
                processOptions.add("Back");

                int selection = getListSelection(processOptions);
                switch (selection) {
                    case 0: {
                        showAccountDetail(accountController, account.getId());
                        break;
                    }
                    case 1: {
                        depositToAccount(accountController, account);

                        break;
                    }
                    case 2: {
                        witdrawFromAccount(accountController, account);
                        break;
                    }
                    case 3: {
                        deleteAccount(accountController, account);
                        break;
                    }
                    default: {
                        return;
                    }
                }
            }
        }
    }
}
