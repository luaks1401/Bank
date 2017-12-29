package com.company.services.transaction;

import com.company.Exceptions.MyException;
import com.company.entity.Transaction;
import com.company.services.BankAccountService;
import com.company.services.Impl.DebetImpl;
import com.company.services.TransactionService;
import com.company.services.Visitor.ElementService;
import com.company.services.Visitor.VisitorService;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionWithdraw extends Transaction implements ElementService, TransactionService {

    private BankAccountService bankAccountToWithdraw;
    private DebetImpl debitDecorator;
    private BigDecimal zero = BigDecimal.valueOf(0);

    public TransactionWithdraw(BankAccountService bankAccountToWithdraw, BigDecimal amountToWithdraw) {
        this.setDate(new Date());
        this.setFromAccountNumber(bankAccountToWithdraw.getAccountNumber());
        this.bankAccountToWithdraw = bankAccountToWithdraw;
        this.setAmount(amountToWithdraw);
        this.setType("WITHDRAW");
    }

    public TransactionWithdraw(DebetImpl debetImpl, BigDecimal amountToWithdraw) {
        this.setDate(new Date());
        this.setFromAccountNumber(debetImpl.getAccountNumber());
        this.bankAccountToWithdraw = debetImpl.bankAccountService;
        this.setAmount(amountToWithdraw);
        debitDecorator = debetImpl;
        this.setType("WITHDRAW");
    }

    public void execute() {
        try {
            Boolean amountToWithdrawIsZero = this.getAmount().compareTo(BigDecimal.valueOf(0)) == 0;
            if (this.getCanDoTransaction() && !amountToWithdrawIsZero) {
                if (debitDecorator != null) {
                    updateBalanceOnDebetAccount();
                } else {
                    updateBalanceOnBankAccount();
                }
            } else {
                this.setTransactionStatus("WRONG AMOUNT TO WITHDRAW");
                throw new MyException("WRONG AMOUNT TO WITHDRAW");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateBalanceOnDebetAccount() {
        BigDecimal actuallyBalance = debitDecorator.checkBalance();
        BigDecimal maxAmountToWithdraw = debitDecorator.checkMaxBalance();
        Boolean canWithdraw = maxAmountToWithdraw.compareTo(this.getAmount()) > -1;
        if (canWithdraw) {
            BigDecimal newBalance = actuallyBalance.subtract(this.getAmount());
            int checkValue = newBalance.compareTo(zero);
            switch (checkValue) {
                case 1:
                    bankAccountToWithdraw.updateBalance(newBalance);
                    debitDecorator.updateDebet(zero);
                    break;
                case 0:
                    bankAccountToWithdraw.updateBalance(zero);
                    debitDecorator.updateDebet(zero);
                    break;
                case -1:
                    bankAccountToWithdraw.updateBalance(zero);
                    debitDecorator.updateDebet(zero.subtract(newBalance));
                    break;
            }
            this.setTransactionStatus("SUCCESS");
        } else {
            this.setTransactionStatus("NOT ENOUGH FUNDS ON YOUR ACCOUNT");
        }
    }

    private void updateBalanceOnBankAccount() {
        BigDecimal actuallyBalance = bankAccountToWithdraw.checkBalance();
        BigDecimal maxAmountToWithdraw = actuallyBalance;
        Boolean canWithdraw = maxAmountToWithdraw.compareTo(this.getAmount()) > -1;
        if (canWithdraw) {
            this.setTransactionStatus("SUCCESS");
            bankAccountToWithdraw.updateBalance(bankAccountToWithdraw.checkBalance().subtract(this.getAmount()));
        } else {
            this.setTransactionStatus("NOT ENOUGH FUNDS ON YOUR ACCOUNT");
        }
    }
    public TransactionWithdraw getTransaction() { return this; }

    @Override
    public void accept(VisitorService v) {
        v.visit(this);
    }
}
