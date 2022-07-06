package bankAccounts.tests.controllers;

import bankAccounts.controllers.CustomerController;
import bankAccounts.enums.ControllerResponseType;
import bankAccounts.models.Customer;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static bankAccounts.models.DataHolder.customers;

public class CustomerControllerTest {
    @Test
    public void createCustomer() {
        customers = new ArrayList<>();
        CustomerController controller = new CustomerController();
        ControllerResponseType responseType = controller.createCustomer(Customer.builder()
                .nationalNumber(1)
                .name("hunkar")
                .address("ankara")
                .build());

        Assert.assertEquals(customers.size(), 1);
        Assert.assertEquals(responseType, ControllerResponseType.SUCCESSFUL);
    }

    @Test
    public void createCustomerSameNationalNumberNotAllowed() {
        customers = new ArrayList<>();
        CustomerController controller = new CustomerController();
        controller.createCustomer(Customer.builder()
                .nationalNumber(1)
                .name("hunkar")
                .address("ankara")
                .build());

        ControllerResponseType responseType = controller.createCustomer(Customer.builder()
                .nationalNumber(1)
                .name("testUser")
                .address("istanbul")
                .build());

        Assert.assertEquals(customers.size(), 1);
        Assert.assertEquals(responseType, ControllerResponseType.CUSTOMER_EXIST);
    }

    @Test
    public void deleteExistCustomer() {
        customers = new ArrayList<>();
        CustomerController controller = new CustomerController();
        controller.createCustomer(Customer.builder()
                .id("1")
                .nationalNumber(1)
                .name("hunkar")
                .address("ankara")
                .build());

        controller.createCustomer(Customer.builder()
                .id("2")
                .nationalNumber(2)
                .name("testUser")
                .address("istanbul")
                .build());

        ControllerResponseType responseType = controller.deleteCustomer("1");

        Assert.assertEquals(customers.size(), 2);
        Assert.assertFalse(customers.get(0).isActive());
        Assert.assertEquals(responseType, ControllerResponseType.SUCCESSFUL);
    }

    @Test
    public void deleteNotExistCustomer() {
        customers = new ArrayList<>();
        CustomerController controller = new CustomerController();
        controller.createCustomer(Customer.builder()
                .id("1")
                .nationalNumber(1)
                .name("hunkar")
                .address("ankara")
                .build());

        controller.createCustomer(Customer.builder()
                .id("2")
                .nationalNumber(2)
                .name("testUser")
                .address("istanbul")
                .build());

        ControllerResponseType responseType = controller.deleteCustomer("12");

        Assert.assertEquals(responseType, ControllerResponseType.CUSTOMER_NOT_FOUND);
    }

    @Test
    public void listActiveCustomers() {
        customers = new ArrayList<>();
        CustomerController controller = new CustomerController();
        controller.createCustomer(Customer.builder()
                .id("1")
                .nationalNumber(1)
                .name("hunkar")
                .address("ankara")
                .build());

        controller.createCustomer(Customer.builder()
                .id("2")
                .nationalNumber(2)
                .name("testUser")
                .address("istanbul")
                .build());


        Assert.assertEquals(controller.getCustomerList().size(), 2);

        controller.deleteCustomer("1");

        Assert.assertEquals(controller.getCustomerList().size(), 1);
    }

    @Test
    public void getExistUserById() {
        customers = new ArrayList<>();
        CustomerController controller = new CustomerController();
        controller.createCustomer(Customer.builder()
                .id("1")
                .nationalNumber(1)
                .name("hunkar")
                .address("ankara")
                .build());

        controller.createCustomer(Customer.builder()
                .id("2")
                .nationalNumber(2)
                .name("testUser")
                .address("istanbul")
                .build());

        Customer customer = controller.getCustomer("2");
        Assert.assertEquals(2, customer.getNationalNumber());
    }

    @Test
    public void getNotExistUserById() {
        customers = new ArrayList<>();
        CustomerController controller = new CustomerController();
        controller.createCustomer(Customer.builder()
                .id("1")
                .nationalNumber(1)
                .name("hunkar")
                .address("ankara")
                .build());

        controller.createCustomer(Customer.builder()
                .id("2")
                .nationalNumber(2)
                .name("testUser")
                .address("istanbul")
                .build());

        Customer customer = controller.getCustomer("15");
        Assert.assertNull(customer);
    }
}
