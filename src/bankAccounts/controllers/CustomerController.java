package bankAccounts.controllers;

import bankAccounts.enums.ControllerResponseType;
import bankAccounts.models.Account;
import bankAccounts.models.Customer;
import lombok.Data;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static bankAccounts.models.DataHolder.accounts;
import static bankAccounts.models.DataHolder.customers;

@Data
public class CustomerController {
    public ControllerResponseType createCustomer(Customer customer) {
        Customer existCustomer = customers.stream()
                .filter(customer1 -> customer1.getNationalNumber() == customer.getNationalNumber())
                .findFirst()
                .orElse(null);

        if (existCustomer == null) {
            customers.add(customer);

            return ControllerResponseType.SUCCESSFUL;
        } else {
            return ControllerResponseType.CUSTOMER_EXIST;
        }
    }

    public ControllerResponseType deleteCustomer(String customerId) {
        Customer customerResult = customers.stream()
                .filter(customer -> customer.isActive() && customer.getId().equals(customerId))
                .findFirst()
                .orElse(null);

        if (customerResult != null) {
            customerResult.setActive(false);

            accounts.stream()
                    .filter(account -> account.isActive() && account.getCustomerId().equals(customerId))
                    .forEach(Account::close);

            return ControllerResponseType.SUCCESSFUL;
        } else {
            return ControllerResponseType.CUSTOMER_NOT_FOUND;
        }
    }

    public ArrayList<Customer> getCustomerList() {
        return (ArrayList<Customer>) customers.stream().filter(Customer::isActive).collect(Collectors.toList());
    }

    public Customer getCustomer(String customerId) {
        return customers.stream().filter(customer -> customer.getId().equals(customerId)).findFirst().orElse(null);
    }
}
