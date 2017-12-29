package com.company.entity;

import com.company.services.BankAccountService;

import java.math.BigDecimal;
import java.util.Date;

public class Investment extends BankAccount {
    private BigDecimal balance2;
    private double interestInvestment;
    private Date startDate;
    private int duration;
    private BankAccountService bankAccount;

    public Investment() {
    }

    public Investment(BankAccountService bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double getInterestInvestment() {
        return interestInvestment;
    }

    public void setInterestInvestment(double interest2) {
        this.interestInvestment = interest2;
    }

    public BigDecimal getBalance2() {
        return balance2;
    }

    public void setBalance2(BigDecimal balance2) {
        this.balance2 = balance2;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public BankAccountService getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountService bankAccount) {
        this.bankAccount = bankAccount;
    }
}
