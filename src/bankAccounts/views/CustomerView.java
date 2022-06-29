package bankAccounts.views;

import bankAccounts.controllers.CustomerController;
import bankAccounts.enums.ControllerResponseType;
import bankAccounts.models.Customer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

import static bankAccounts.views.MainView.getListSelection;
import static bankAccounts.views.MainView.responseParser;

public class CustomerView {
    public static void createCustomer(CustomerController customerController) {
        Scanner scanner = new Scanner(System.in);

        //Name
        System.out.print("Please enter name of customer: ");
        String name = scanner.nextLine();

        //Address
        System.out.print("Please enter address of customer: ");
        String address = scanner.nextLine();

        //National Number
        System.out.print("Please enter national number of customer: ");
        int nationalNumber = scanner.nextInt();

        ControllerResponseType controllerResponse = customerController.createCustomer(Customer
                .builder()
                .name(name)
                .nationalNumber(nationalNumber)
                .address(address)
                .build());

        responseParser(controllerResponse);
    }

    public static Customer getCustomer(ArrayList<Customer> customers) {
        Scanner scanner = new Scanner(System.in);

        if (customers.size() > 0) {
            IntStream.range(0, customers.size())
                    .forEach(index -> System.out.printf("%d - %s\n", index, customers.get(index).getName()));
        } else {
            notExistCustomer();
            return null;
        }

        System.out.print("Enter your choice: ");
        int result = scanner.nextInt();

        if (result < customers.size()) {
            return customers.get(result);
        } else {
            notExistCustomer();
            return null;
        }
    }

    public static void showCustomerDetail(CustomerController customerController, String customerId) {
        Customer customer = customerController.getCustomer(customerId);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("Customer id: " + customer.getId());
        System.out.println("Customer name: " + customer.getName());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("National Number: " + customer.getNationalNumber());
    }

    public static void notExistCustomer() {
        System.out.println("There is no customer.");
    }

    public static void deleteCustomer(CustomerController customerController, Customer customer) {
        System.out.println("Customer id: " + customer.getId());
        System.out.println("Customer name: " + customer.getName());
        System.out.println("Customer national id: " + customer.getNationalNumber());

        System.out.println("Customer will be deleted.");

        System.out.println("Are you sure?");

        ArrayList<String> processOptions = new ArrayList<>();
        processOptions.add("Yes");
        processOptions.add("No");

        int selection = getListSelection(processOptions);

        if (selection == 0) {
            ControllerResponseType controllerResponse = customerController.deleteCustomer(customer.getId());
            responseParser(controllerResponse);
        }
    }
}
