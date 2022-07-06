package bankAccounts;

import bankAccounts.controllers.AccountController;
import bankAccounts.controllers.CustomerController;
import bankAccounts.enums.MaturationType;
import bankAccounts.models.RecurringDepositAccount;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import static bankAccounts.views.MainView.appScreen;

public class Main {
    public static void setSchedular(AccountController accountController, CustomerController customerController) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        long initialDelay = cal.getTimeInMillis() - System.currentTimeMillis();

        // Once a day
        int period = 24 * 60 * 60 * 1000;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                accountController.checkRecurringDepositAccounts();
            }
        }, initialDelay, period);
    }

    public static void main(String[] args) {
        AccountController accountController = new AccountController();
        CustomerController customerController = new CustomerController();

        setSchedular(accountController, customerController);

        appScreen(accountController, customerController);
    }
}
