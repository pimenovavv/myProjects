import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Bank {

  private Collection<Account> allAccounts = Collections.synchronizedCollection(new HashSet<>());
  private final Random random = new Random();
  private HashMap<String, Account> accounts = new HashMap<>();
  private int fraudCheck = 50_000;

  private AtomicInteger atomicInt = new AtomicInteger(40000);


  public void addAccountOnSystemInBank(Account account) {

    allAccounts.add(account);
    accounts.put(account.getAccNumber(), account);
  }

  public void setNameAccount(Account account) {
    Integer nameAccount = atomicInt.incrementAndGet();
    account.setAccNumber(nameAccount.toString());
  }

  public Account createNewAccount() {
    Account account = new Account();
    Integer nameAccount = atomicInt.incrementAndGet();
    account.setAccNumber(nameAccount.toString());
    addAccountOnSystemInBank(account);
    return account;
  }

  public Collection<Account> getAllAccounts() {
    return allAccounts;
  }

  public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
      throws InterruptedException {

    Account accountFrom = accounts.get(fromAccountNum);
    Account accountTo = accounts.get(toAccountNum);

    Thread.sleep(1000);
    boolean blockedStatus = random.nextBoolean();

    if (blockedStatus) {
      blockAccount(accountFrom, accountTo);
    }
    return blockedStatus;
  }


  /**
   * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
   * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
   * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
   * усмотрение)
   */

  public void transfer(String fromAccountNum, String toAccountNum, long amount)
      throws InterruptedException {

    Account accountFrom = accounts.get(fromAccountNum);
    Account accountTo = accounts.get(toAccountNum);

    if (fromAccountNum.compareTo(toAccountNum) < 0) {
      synchronized (accountFrom) {
        synchronized (accountTo) {
          doTransfer(accountFrom, accountTo, amount);
        }
      }
    } else {
      synchronized (accountTo) {
        synchronized (accountFrom) {
          doTransfer(accountFrom, accountTo, amount);
        }
      }
    }
  }

  private void doTransfer(Account accountFrom, Account accountTo, long amount)
      throws InterruptedException {

    if (accountFrom.isBlocked() || accountTo.isBlocked()) {
      System.out.println("Счет заблокирован. Перевод не возможен!");
      return;
    } else if (amount > fraudCheck) {
      boolean resultTransaction = isFraud(accountFrom.getAccNumber(), accountTo.getAccNumber(),
          amount);
      if (resultTransaction) {
        return;
      }
    }

    if (accountFrom.getMoney() < amount) {
      System.out.println("Для перевода необходимо пополнить баланс!");
      return;
    }
    accountFrom.setMoney(accountFrom.getMoney() - amount);
    accountTo.setMoney(accountTo.getMoney() + amount);
  }


  /**
   * TODO: реализовать метод. Возвращает остаток на счёте.
   */
  public long getBalance(String accountNum) throws InterruptedException {

    Account account = accounts.get(accountNum);

    if (account.isBlocked()) {
      System.out.println(
          "Счет заблокирован, запрос баланса не доступен. Обратитесь в службу поддержка банка за более подробной информацией!");
    }
    return account.getMoney();
  }

  public void blockAccount(Account fromAccountNum, Account toAccountNum) {

    fromAccountNum.setBlocked(true);
    toAccountNum.setBlocked(true);
    System.out.println("Операция не возможна! Счет заблокирован!");
  }

  public long allMoneyInBank() {

    long allMoney = 0;

    for (Map.Entry<String, Account> entry : accounts.entrySet()) {
      try {
        allMoney += getBalance(entry.getKey());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return allMoney;
  }
}
