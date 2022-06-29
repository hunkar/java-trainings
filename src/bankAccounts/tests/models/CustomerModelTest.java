package bankAccounts.tests.models;

import bankAccounts.models.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerModelTest {
    @Test
    public void createAccount() {
        Customer customer1 = Customer.builder()
                .name("hunkar")
                .address("ankara")
                .nationalNumber(123)
                .build();

        Customer customer2 = Customer.builder()
                .name("hunkar1")
                .address("ankara")
                .nationalNumber(124)
                .build();

        Assertions.assertEquals("hunkar", customer1.getName());
        Assertions.assertEquals("ankara", customer1.getAddress());
        Assertions.assertEquals(123, customer1.getNationalNumber());
        Assertions.assertNotEquals(customer1.getId(), customer2.getId());
    }


}
