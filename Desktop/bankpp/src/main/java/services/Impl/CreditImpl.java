package services.Impl;

import entity.Credit;
import services.BankAccountService;
import services.Visitor.ElementService;
import services.Visitor.VisitorService;

import java.math.BigDecimal;

public class CreditImpl implements BankAccountService, ElementService {
    public BankAccountService bankAccountService;
    private Credit credit;

    public CreditImpl(BankAccountService bankAccount, BigDecimal amountCredit, BigDecimal interestCredit, int durationInMonth) {
        credit = new Credit(bankAccount, amountCredit, interestCredit, durationInMonth);
        bankAccountService = credit.getBankAccount();
        calculateInstallmentCredit();
    }

    @Override
    public BankAccountImpl addBankAccount() {
        return bankAccountService.addBankAccount();
    }

    @Override
    public void updateBalance(BigDecimal newBalance) {
        credit.setAmountCredit(newBalance);
    }

    @Override
    public BigDecimal checkBalance() { // zmieniona przez ..
        return credit.getAmountCredit();
    }

    @Override
    public BigDecimal checkMaxBalance() { // zmieniona przez ..
        return BigDecimal.valueOf(0);
    }

    @Override
    public void updateMaxDebetAmount(BigDecimal maxDebetAmount) {
    }

    @Override
    public void updateDebet(BigDecimal amount) {
    }

    @Override
    public Boolean debetIsOn() {
        return false;
    }

    @Override
    public BigDecimal getDebet() {
        return BigDecimal.valueOf(0);
    }

    @Override
    public int getAccountNumber() {
        return bankAccountService.getAccountNumber();
    }

    @Override
    public void setAccountNumber(int account) { }

    @Override
    public BigDecimal checkMaxDebetAmount() {
        return null;
    }

    @Override
    public BigDecimal updateInterestMechanism() { return null; }

    @Override
    public Boolean checkWithdrawAccess(BigDecimal amountToWithdraw) {
        return false;
    }

    @Override
    public void accept(VisitorService v) {
        v.visit(this);
    }

    @Override
    public void switchDebet() {}

    @Override
    public BigDecimal checkInstallment() {
        return credit.getInstallmentCredit();
    }

    private void calculateInstallmentCredit() {
        BigDecimal durationInMonth = BigDecimal.valueOf(credit.getDurationInMonth());
        BigDecimal wynik;
        wynik = credit.getAmountCredit().multiply(credit.getInterestCredit());
        wynik = wynik.divide((durationInMonth), 2);
        credit.setInstallmentCredit(scale(wynik));
    }

    private BigDecimal scale(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}