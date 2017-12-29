package com.company.services;

import com.company.services.Impl.BankAccountImpl;
import com.company.services.Visitor.ElementService;
import com.company.services.Visitor.VisitorService;

import java.math.BigDecimal;

public interface BankAccountService{

    public BankAccountImpl addBankAccount();

    public void updateDebet(BigDecimal debet);

    public void updateBalance(BigDecimal newBalance);

    public void updateMaxDebetAmount(BigDecimal maxDebetAmount);

    public BigDecimal updateInterestMechanism();

    public BigDecimal checkBalance();

    public BigDecimal checkInstallment();

    public BigDecimal checkMaxBalance();

    public int getAccountNumber();

    public void setAccountNumber(int account);

    public BigDecimal checkMaxDebetAmount();

    public Boolean checkWithdrawAccess(BigDecimal amountToWithdraw);

    public void switchDebet();

    public Boolean debetIsOn();

    public BigDecimal getDebet();

    public void accept(VisitorService v);
}
