package com.company.entity;

import com.company.services.BankAccountService;
import com.company.services.Impl.BankAccountImpl;

import java.math.BigDecimal;

public class Debet {
    private Boolean isDebet = false;
    private BigDecimal maxDebetAmount;
    private BigDecimal debet;
    private BankAccountService bankAccount;

    public Debet(BankAccountService bankAccount) {
        this.isDebet = true;
        this.maxDebetAmount = new BigDecimal(100);
        this.debet = new BigDecimal(0);
        this.bankAccount = bankAccount;
    }

    public Boolean getIsDebet() {
        return isDebet;
    }

    public void setIsDebet(Boolean debet) {
        isDebet = debet;
    }

    public BigDecimal getDebet() {
        return debet;
    }

    public void setDebet(BigDecimal debet) {
        this.debet = debet;
    }

    public BigDecimal getMaxDebetAmount() {
        return maxDebetAmount;
    }

    public void setMaxDebetAmount(BigDecimal maxDebetAmount) {
        this.maxDebetAmount = maxDebetAmount;
    }

    public BankAccountService getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountService bankAccount) {
        this.bankAccount = bankAccount;
    }
}