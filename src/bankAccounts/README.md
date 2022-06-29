
# Bank Accounts

Create a bank management software with customers and accounts.
Keep track of customer details i.e. name, address, national number etc. etc.
Customers can open new accounts or close current ones. A customer can have multiple accounts.
There can be multiple types of accounts:
- checking account: Think of a checking account as your “everyday account.” It's a place to keep the money you use to pay your bills or cover everyday expenses.
- recurring deposit account: a savings account where there is an interest rate and a maturation date. After the maturation date the interest is gained.
  Customers can specify maturation dates in the following intervals: 1 month, 6 months, 1 year.
  The customer can choose between automatically adding an the amount on top of the current balance or transfer it to their checking account.
  No withdrawals possible from the recurring deposit account before the matuarion date. The customer can choose to close the account before the maturation date but then the interest is lost.
  To create a recurring deposit account the customer has to have a checking account first.



A customer should be able to deposit or withdraw money from a particular checking account that he/she desires.
There should be validations in place such that,
1) There is a max amount of cash that can be withdrawn in a day per customer. There is a bank default which is 1000 euros. The customers can specify their own limit, which cannot exceed 1000 euros.
2) Allow withdraws only if the balance is sufficient, there cannot be any negative balances.



A customer should be able to request account activites document for a given time period.
A customer should be able to display all account details.



Please also provide unit tests when applicable.

All animals eat. I want to see what they like to eat. For instance for a cat it can print "I like to eat tuna fish".



Some of those animals can be pets by nature. Pets have names, the other animals don't. I also want to know if a pet is trained.
Pets can play with their owners. I also want to see how a pet plays. For example: it can print out "Fluffy likes to play with a toy mouse", Fluffy being the animal's name.




Please create a class hierarchy and add instance variables, methods where necessary.
Write a class that instantiates animals and demonstrates how they move, play and what they eat, what sounds they make.
Introduce some filtering possibilities like list all animals that can walk and print out how they walk. etc. etc.