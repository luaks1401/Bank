package com.company.services.Impl;

import com.company.entity.Debet;
import com.company.services.BankAccountService;
import com.company.services.Visitor.ElementService;
import com.company.services.Visitor.VisitorService;

import java.math.BigDecimal;

public class DebetImpl implements BankAccountService, ElementService {
    public BankAccountService bankAccountService;
    private Debet debet;

    public DebetImpl(BankAccountService bankAccount) {
        debet = new Debet(bankAccount);
        debet.setBankAccount(bankAccount);
        bankAccountService = debet.getBankAccount();
    }

    @Override
    public BankAccountImpl addBankAccount() {
        return bankAccountService.addBankAccount();
    }

    @Override
    public void updateBalance(BigDecimal newBalance) {
        bankAccountService.updateBalance(newBalance);
    }

    @Override
    public BigDecimal checkBalance() { // zmieniona przez ..
        return bankAccountService.checkBalance().subtract(debet.getDebet());
    }

    @Override
    public BigDecimal checkInstallment() {
        return null;
    }

    @Override
    public BigDecimal checkMaxBalance() { // zmieniona przez ..
        return bankAccountService.checkBalance().add(debet.getMaxDebetAmount());
    }

    @Override
    public void updateDebet(BigDecimal amount) {
        Boolean canUpdate = debet.getMaxDebetAmount().compareTo(amount) >= 0;
        if (this.debetIsOn() && canUpdate) {
            debet.setDebet(amount);
        }
    }

    @Override
    public void switchDebet() {
        if (debet.getDebet().compareTo(BigDecimal.valueOf(0)) == 0) {
            debet.setIsDebet(!debet.getIsDebet());
            if (!debet.getIsDebet()) {
                debet.setMaxDebetAmount(new BigDecimal(0));
            }
        }
    }

    @Override
    public void updateMaxDebetAmount(BigDecimal maxDebetAmount) {
        BigDecimal value = debet.getIsDebet() ? maxDebetAmount : new BigDecimal(0);
        debet.setMaxDebetAmount(value);
    }

    @Override
    public Boolean debetIsOn() {
        return debet.getIsDebet();
    }

    @Override
    public BigDecimal getDebet() {
        return debet.getDebet();
    }

    @Override
    public int getAccountNumber() {
        return bankAccountService.getAccountNumber();
    }

    @Override
    public void setAccountNumber(int account) {
    }

    @Override
    public BigDecimal checkMaxDebetAmount() {
        return debet.getMaxDebetAmount();
    }

    @Override
    public BigDecimal updateInterestMechanism() {
        return null;
    }

    @Override
    public Boolean checkWithdrawAccess(BigDecimal amountToWithdraw) {
        Boolean canNotWithdraw = amountToWithdraw.compareTo(BigDecimal.valueOf(0)) <= 0;
        if (canNotWithdraw) {
            return false;
        }

        BigDecimal maxValueForPayment = this.checkMaxBalance().subtract(debet.getDebet());
        return amountToWithdraw.compareTo(maxValueForPayment) <= 0;
    }

    @Override
    public void accept(VisitorService v) {
        v.visit(this);
    }
}