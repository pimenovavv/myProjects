import java.util.Objects;

public class Account {

  private long money;
  private String accNumber;
  private volatile boolean blocked = false;

  public Account(String accNumber, long money) {
    this.accNumber = accNumber;
    this.money = money;
  }

  public Account() {
  }

  public long getMoney() {
    return money;
  }

  public void setMoney(long money) {
    this.money = money;
  }

  public boolean isBlocked() {
    return blocked;
  }

  public void setBlocked(boolean blocked) {
    this.blocked = blocked;
  }

  public String getAccNumber() {
    return accNumber;
  }

  public void setAccNumber(String accNumber) {
    this.accNumber = accNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return accNumber.equals(account.accNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accNumber);
  }
}
