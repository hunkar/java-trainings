package bankAccounts.tests.models;

import bankAccounts.models.Customer;
import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertEquals("hunkar", customer1.getName());
        Assert.assertEquals("ankara", customer1.getAddress());
        Assert.assertEquals(123, customer1.getNationalNumber());
        Assert.assertNotEquals(customer1.getId(), customer2.getId());
    }


}
