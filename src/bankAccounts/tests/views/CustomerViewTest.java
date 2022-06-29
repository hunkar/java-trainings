package bankAccounts.tests.views;

import bankAccounts.controllers.CustomerController;
import bankAccounts.models.Customer;
import bankAccounts.views.CustomerView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static bankAccounts.models.DataHolder.customers;


public class CustomerViewTest {
    @Test
    public void getCustomer() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);

        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(Customer.builder().name("ac-1").address("c-1").nationalNumber(1).build());
        customers.add(Customer.builder().name("ac-2").address("c-1").nationalNumber(2).build());
        customers.add(Customer.builder().name("ac-3").address("c-1").nationalNumber(3).build());


        Customer result = CustomerView.getCustomer(customers);

        Assertions.assertEquals(result.getId(), customers.get(1).getId());

        System.setIn(sysInBackup);
    }

    @Test
    public void createCustomer() {
        customers.clear();

        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("customer\nankara\n1".getBytes());
        System.setIn(in);

        CustomerController customerController = new CustomerController();

        CustomerView.createCustomer(customerController);

        Assertions.assertEquals(customerController.getCustomerList().size(), 1);
        Assertions.assertEquals(customerController.getCustomerList().get(0).getName(), "customer");
        Assertions.assertEquals(customerController.getCustomerList().get(0).getAddress(), "ankara");
        Assertions.assertEquals(customerController.getCustomerList().get(0).getNationalNumber(), 1);

        System.setIn(sysInBackup);
    }

    @Test
    public void deleteCustomer() {
        InputStream sysInBackup = System.in;
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);

        CustomerController controller = new CustomerController();

        Customer customer = Customer.builder()
                .id("111")
                .name("customer")
                .address("ankara")
                .nationalNumber(111)
                .build();

        controller.createCustomer(customer);


        CustomerView.deleteCustomer(controller, customer);

        Assertions.assertFalse(customer.isActive());

        System.setIn(sysInBackup);
    }
}
