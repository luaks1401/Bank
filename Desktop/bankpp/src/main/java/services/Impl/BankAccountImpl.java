package services.Impl;

import entity.BankAccount;
import services.BankAccountService;
import services.Visitor.ElementService;
import services.Visitor.VisitorService;
import services.investment.InterestMechanism1;
import services.investment.InterestMechanism2;
import services.investment.InterestMechanism3;
import services.investment.InterestmentMechanismService;

import java.math.BigDecimal;

public class BankAccountImpl implements BankAccountService, ElementService {

    public BankAccount bankAccount;
    private InterestmentMechanismService interestmentMechanism;

    public BankAccountImpl() {
        this.addBankAccount();
    }

    @Override
    public BankAccountImpl addBankAccount() {
        bankAccount = new BankAccount();
        interestmentMechanism = new InterestMechanism1();
        return this;
    }

    @Override
    public void updateBalance(BigDecimal newBalance) {
        if (newBalance.compareTo(BigDecimal.valueOf(0)) > -1) {
            bankAccount.setBalance(newBalance);
            updateInterestMechanism();
        }
    }

    @Override
    public BigDecimal updateInterestMechanism() {
        BigDecimal balance = checkBalance();
        if (balance.compareTo(BigDecimal.valueOf(5000)) == 1) {
            interestmentMechanism = new InterestMechanism3();
        } else if (balance.compareTo(BigDecimal.valueOf(3000)) == 1) {
            interestmentMechanism = new InterestMechanism2();
        } else {
            interestmentMechanism = new InterestMechanism1();
        }
        return interestmentMechanism.calculateInterest(this);
    }

    @Override
    public BigDecimal checkBalance() {
        return bankAccount.getBalance();
    }

    @Override
    public BigDecimal checkInstallment() {
        return null;
    }

    @Override
    public BigDecimal checkMaxBalance() {
        return bankAccount.getBalance();
    }

    @Override
    public void updateMaxDebetAmount(BigDecimal maxDebetAmount) {
    }

    @Override
    public int getAccountNumber() {
        return bankAccount.getAccountNumber();
    }

    @Override
    public void setAccountNumber(int account) {
        bankAccount.setAccountNumber(account);
    }

    @Override
    public BigDecimal checkMaxDebetAmount() {
        return null;
    }

    @Override
    public Boolean checkWithdrawAccess(BigDecimal amountToWithdraw) {
        Boolean canNotWithdraw = amountToWithdraw.compareTo(BigDecimal.valueOf(0)) <= 0;
        if (canNotWithdraw) {
            return false;
        }
        BigDecimal balance = bankAccount.getBalance();
        return amountToWithdraw.compareTo(balance) <= 0;
    }

    @Override
    public void updateDebet(BigDecimal debet) {
    }

    @Override
    public void accept(VisitorService v) {
        v.visit(this);
    }

    @Override
    public void switchDebet() {}

    @Override
    public Boolean debetIsOn() {
        return false;
    }

    @Override
    public BigDecimal getDebet() {
        return null;
    }
}
