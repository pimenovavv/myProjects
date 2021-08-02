public class Main {

  public static void main(String[] args) throws InterruptedException {
    Bank bank = new Bank();

    Account accountFrom = new Account("40703", 150000);
    Account accountTo = new Account("40705", 17);
    Account account3 = new Account("40614", 160000);
    Account account4 = new Account("40702", 23400);
    Account account5 = new Account("40702", 23500);
    Account account6 = new Account();
    Account account7 = new Account();
    Account account8 = new Account();
    Account account9 = new Account();


    bank.setNameAccount(account6);
    bank.setNameAccount(account7);
    bank.setNameAccount(account8);
    bank.setNameAccount(account9);

    bank.addAccountOnSystemInBank(account3);
    bank.addAccountOnSystemInBank(accountFrom);
    bank.addAccountOnSystemInBank(accountTo);
    bank.addAccountOnSystemInBank(account4);
    bank.addAccountOnSystemInBank(account5);
    bank.addAccountOnSystemInBank(account6);
    bank.addAccountOnSystemInBank(account7);
    bank.addAccountOnSystemInBank(account8);
    bank.addAccountOnSystemInBank(account9);


    bank.transfer("40703", "40705", 100_000);
    bank.transfer("40703", "40614", 10000);
    bank.transfer(accountFrom.getAccNumber(), account6.getAccNumber(), 500);
    bank.transfer(accountFrom.getAccNumber(), account7.getAccNumber(), 556);
    bank.transfer(accountFrom.getAccNumber(), account8.getAccNumber(), 600);


    System.out.println(accountFrom.getMoney());
    System.out.println(bank.getBalance(accountTo.getAccNumber()));


    Bank bank2 = new Bank();
    Account acc1 = bank2.createNewAccount();
    Account acc2 = bank2.createNewAccount();
    Account acc3 = bank2.createNewAccount();
    Account acc4 = bank2.createNewAccount();

    acc1.setMoney(50000);
    bank2.transfer(acc1.getAccNumber(), acc2.getAccNumber(), 200);
    bank2.transfer(acc1.getAccNumber(), acc3.getAccNumber(), 300);
    bank2.transfer(acc1.getAccNumber(), acc4.getAccNumber(), 400);

    for (Account acc : bank2.getAllAccounts()) {
      System.out.println(acc.getAccNumber() + " : " + acc.getMoney());
    }
  }
}
