package bankAccounts.tests.views;

import bankAccounts.views.MainView;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class MainViewTest {
    @Test
    public void getListSelection() {
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);

        ArrayList<String> customers = new ArrayList<>();
        customers.add("c-1");
        customers.add("c-2");
        customers.add("c-3");

        int result = MainView.getListSelection(customers);

        Assert.assertEquals(result, 1);

        System.setIn(sysInBackup);
    }
}
