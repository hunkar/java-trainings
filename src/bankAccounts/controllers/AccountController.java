package bankAccounts.controllers;

import bankAccounts.enums.ControllerResponseType;
import bankAccounts.enums.TransactionLogType;
import bankAccounts.models.Account;
import bankAccounts.models.CheckingAccount;
import bankAccounts.models.RecurringDepositAccount;
import bankAccounts.models.Transaction;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import static bankAccounts.models.DataHolder.accounts;

@Data
public class AccountController {
    public ControllerResponseType createRecurringDepositAccount(RecurringDepositAccount recurringDepositAccount) {
        if (getCheckingAccountListByCustomer(recurringDepositAccount.getCustomerId()).size() == 0) {
            return ControllerResponseType.ERROR_CREATE_CHECKING_FIRST;
        }

        recurringDepositAccount.addTransactionLog(Transaction.builder().date(new Date()).balance(recurringDepositAccount.getBalance()).value(recurringDepositAccount.getBalance()).transactionLogType(TransactionLogType.ACCOUNT_OPEN).build());

        accounts.add(recurringDepositAccount);

        return ControllerResponseType.SUCCESSFUL;
    }

    public ControllerResponseType createCheckingAccount(CheckingAccount checkingAccount) {
        checkingAccount.addTransactionLog(Transaction.builder().date(new Date()).balance(checkingAccount.getBalance()).value(checkingAccount.getBalance()).transactionLogType(TransactionLogType.ACCOUNT_OPEN).build());

        accounts.add(checkingAccount);

        return ControllerResponseType.SUCCESSFUL;
    }

    public ControllerResponseType deleteAccount(String accountId) {
        Account accountResult = accounts.stream().filter(account -> account.isActive() && account.getId().equals(accountId)).findFirst().orElse(null);

        if (accountResult != null) {
            accountResult.close();

            return ControllerResponseType.SUCCESSFUL;
        } else {
            return ControllerResponseType.ACCOUNT_NOT_FOUND;
        }
    }

    public ControllerResponseType checkRecurringDepositAccounts() {
        accounts.stream().filter(account -> account instanceof RecurringDepositAccount).forEach(account -> {
            RecurringDepositAccount recurringDepositAccount = ((RecurringDepositAccount) account);

            Date now = new Date();
            Date maturationFinishDate = recurringDepositAccount.getMaturationFinishDate();

            if (maturationFinishDate.getTime() <= now.getTime()) {
                double earning = recurringDepositAccount.getEarningByMaturationType();

                if (recurringDepositAccount.isPaymentToCheckingAccount()) {
                    CheckingAccount checkingAccount = (CheckingAccount) getCheckingAccountListByCustomer(recurringDepositAccount.getCustomerId()).get(0);

                    if (checkingAccount != null) {
                        depositToAccount(checkingAccount, earning, TransactionLogType.INTEREST_EARNING);

                        recurringDepositAccount.setMaturationBalance(recurringDepositAccount.getBalance());
                        recurringDepositAccount.setMaturationStartDate(now);

                        return;
                    }
                }

                depositToAccount(recurringDepositAccount, earning, TransactionLogType.INTEREST_EARNING);

                recurringDepositAccount.setMaturationBalance(recurringDepositAccount.getBalance());
                recurringDepositAccount.setMaturationStartDate(now);
            }
        });

        return ControllerResponseType.CHECKING_FINISHED;
    }

    public ControllerResponseType depositToAccount(Account account, double value, TransactionLogType transactionLogType) {
        int indexOfAccount = accounts.indexOf(account);
        if (value <= 0) {
            return ControllerResponseType.INVALID_DEPOSIT_VALUE;
        }

        if (indexOfAccount > -1) {
            if (accounts.get(indexOfAccount) instanceof RecurringDepositAccount) {
                RecurringDepositAccount recurringDepositAccount = (RecurringDepositAccount) accounts.get(indexOfAccount);

                LocalDateTime dayStart = LocalDateTime.ofInstant(recurringDepositAccount.getMaturationStartDate().toInstant(), ZoneId.systemDefault()).withHour(0).withMinute(0).withSecond(0);

                LocalDateTime dayEnd = LocalDateTime.ofInstant(recurringDepositAccount.getMaturationStartDate().toInstant(), ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59);

                if (dayStart.isBefore(LocalDateTime.now()) && dayEnd.isAfter(LocalDateTime.now())) {
                    recurringDepositAccount.depositMoney(value);
                    recurringDepositAccount.setMaturationBalance(recurringDepositAccount.getMaturationBalance() + value);
                } else {
                    recurringDepositAccount.depositMoney(value);
                }

                recurringDepositAccount.addTransactionLog(Transaction.builder().date(new Date()).value(value).balance(recurringDepositAccount.getBalance()).transactionLogType(transactionLogType).build());

            } else if (accounts.get(indexOfAccount) instanceof CheckingAccount) {
                CheckingAccount checkingAccount = (CheckingAccount) accounts.get(indexOfAccount);

                checkingAccount.depositMoney(value);
                checkingAccount.addTransactionLog(Transaction.builder().date(new Date()).value(value).balance(checkingAccount.getBalance()).transactionLogType(transactionLogType).build());
            } else {
                return ControllerResponseType.UNKNOWN_ERROR;
            }
        } else {
            return ControllerResponseType.ACCOUNT_NOT_FOUND;
        }

        return ControllerResponseType.SUCCESSFUL;
    }

    public ControllerResponseType withdrawFromAccount(Account account, double value) {
        int indexOfAccount = accounts.indexOf(account);

        if (indexOfAccount > -1) {
            if (value <= 0 || value > accounts.get(indexOfAccount).getBalance()) {
                return ControllerResponseType.INVALID_WITHDRAW_VALUE;
            }

            if (accounts.get(indexOfAccount) instanceof RecurringDepositAccount) {
                RecurringDepositAccount recurringDepositAccount = (RecurringDepositAccount) accounts.get(indexOfAccount);

                LocalDateTime rangeStartTime = LocalDateTime.ofInstant(recurringDepositAccount.getMaturationStartDate().toInstant(), ZoneId.systemDefault()).withHour(0).withMinute(0).withSecond(0);

                LocalDateTime rangeEndTime = LocalDateTime.ofInstant(recurringDepositAccount.getMaturationStartDate().toInstant(), ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59);

                if (rangeStartTime.isBefore(LocalDateTime.now()) && rangeEndTime.isAfter(LocalDateTime.now())) {
                    recurringDepositAccount.withdrawMoney(value);
                } else {
                    return ControllerResponseType.NO_WITHDRAW_IN_MATURATION;
                }
            } else if (accounts.get(indexOfAccount) instanceof CheckingAccount) {
                CheckingAccount checkingAccount = (CheckingAccount) accounts.get(indexOfAccount);
                Calendar now = Calendar.getInstance();
                now.setTime(new Date());

                String dayStamp = now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.DAY_OF_MONTH);

                if (checkingAccount.getDailyWithdrawValue(dayStamp) + value <= checkingAccount.getDailyWithdrawLimit()) {
                    checkingAccount.withdrawMoney(value);
                } else {
                    return ControllerResponseType.DAILY_LIMIT_EXCEEDED;
                }
            } else {
                return ControllerResponseType.UNKNOWN_ERROR;
            }
        } else {
            return ControllerResponseType.ACCOUNT_NOT_FOUND;
        }

        return ControllerResponseType.SUCCESSFUL;
    }

    public Account getAccountById(String accountId) {
        return accounts.stream().filter(account -> account.getId().equals(accountId)).findFirst().orElse(null);
    }

    public ArrayList<Account> getAccountListByCustomer(String customerId) {
        return (ArrayList<Account>) accounts.stream().filter(account -> account.getCustomerId().equals(customerId)).collect(Collectors.toList());
    }

    public ArrayList<Account> getCheckingAccountListByCustomer(String customerId) {
        return (ArrayList<Account>) accounts.stream().filter(account -> account.getCustomerId().equals(customerId) && account instanceof CheckingAccount).collect(Collectors.toList());
    }
}
