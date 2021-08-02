import java.util.ArrayList;
import java.util.Random;
import junit.framework.TestCase;

public class TransactionTestNew extends TestCase {

  private Random random = new Random();
  private Bank bank = new Bank();
  private ArrayList<Account> accounts = new ArrayList<>();
  private int accountsCount = 10;
  private int threadsCount = 1000;
  private int transferCount = 1000;
  private ArrayList<Thread> threads = new ArrayList<>();


  @Override
  protected void setUp() {

    for (int i = 0; i < accountsCount; i++) {
      String nameAccount = "account " + (i + 1);
      accounts.add(new Account(nameAccount, random.nextInt(100_000)));
    }

    for (Account acc : accounts) {
      bank.addAccountOnSystemInBank(acc);
    }

    for (int i = 0; i < threadsCount; i++) {
      threads.add(new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            for (int i = 0; i < transferCount; i++) {
              bank.transfer(
                  accounts.get(random.nextInt(accounts.size())).getAccNumber(),
                  accounts.get(random.nextInt(accounts.size())).getAccNumber(),
                  Math.abs(random.nextInt(53000)));
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }));
    }
  }

  public void testTransfer() throws InterruptedException {
    long actual = bank.allMoneyInBank();

    threads.forEach(Thread::start);
    for (Thread thread : threads) {
      thread.join();
    }

    long expected = bank.allMoneyInBank();

    System.out.println(actual);
    System.out.println(expected);

    assertEquals(actual, expected);
  }
}

