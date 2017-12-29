package entity;

import java.math.BigDecimal;
import java.util.Random;

public class BankAccount {

    private int id;
    private int accountNumber;
    private BigDecimal balance;
    private Random rnd = new Random();

    private BigDecimal interest;
    private Investment investment;

    public BankAccount() {
        this.id = rnd.nextInt(1000) + 1;
        this.balance = new BigDecimal(0);
        this.accountNumber = 11110000 + rnd.nextInt(1000) + 1;
        this.interest = new BigDecimal(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() { return balance; }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getInterest() {
        return this.interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Investment getInvestment() {
        return investment;
    }

    public void setInvestment(Investment investment) {
        this.investment = investment;
    }
}