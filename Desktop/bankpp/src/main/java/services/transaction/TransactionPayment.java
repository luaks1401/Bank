package services.transaction;

import Exceptions.MyException;
import entity.Transaction;
import services.BankAccountService;
import services.Impl.CreditImpl;
import services.Impl.DebetImpl;
import services.TransactionService;
import services.Visitor.ElementService;
import services.Visitor.VisitorService;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionPayment extends Transaction implements ElementService, TransactionService {

    private BankAccountService bankAccountToPayment;
    private DebetImpl debitDecorator;
    private CreditImpl creditDecorator;
    private BigDecimal zero = new BigDecimal("0");

    public TransactionPayment(BankAccountService bankAccountToPayment, BigDecimal amountToPayment) {
        this.setDate(new Date());
        this.setFromAccountNumber(bankAccountToPayment.getAccountNumber());
        this.bankAccountToPayment = bankAccountToPayment;
        this.setAmount(amountToPayment);
        this.setType("PAYMENT");
    }

    public TransactionPayment(CreditImpl creditAccount, BigDecimal amountToPayment) {
        this.setDate(new Date());
        this.setFromAccountNumber(creditAccount.getAccountNumber());
        this.bankAccountToPayment = creditAccount.bankAccountService;
        this.setAmount(amountToPayment);
        creditDecorator = creditAccount;
        this.setType("PAYMENT");
    }

    public TransactionPayment(DebetImpl debetImpl, BigDecimal amountToPayment) {
        this.setDate(new Date());
        this.setFromAccountNumber(debetImpl.getAccountNumber());
        this.bankAccountToPayment = debetImpl.bankAccountService;
        this.setAmount(amountToPayment);
        debitDecorator = debetImpl;
        this.setType("PAYMENT");
    }

    public void execute() {
        if (this.getCanDoTransaction()) {
            try {
                this.setCanDoTransaction(false);
                Boolean amountAboveZero = this.getAmount().compareTo(zero) > 0;
                if (amountAboveZero) {
                    if (debitDecorator != null) {
                        updateBalanceOnDebetAccount();
                    } else if (creditDecorator != null) {
                        updateBalanceOnCreditAccount();
                    } else {
                        updateBalanceOnBankAccount();
                    }
                } else {
                    this.setTransactionStatus("WRONG AMOUNT TO PAYMENT");
                    throw new MyException("WRONG AMOUNT TO WITHDRAW");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void updateBalanceOnDebetAccount() {
        BigDecimal minusOne = new BigDecimal("-1");
        BigDecimal actuallyBalance = debitDecorator.checkBalance().add(this.getAmount());
        Boolean balanceBelowZero = actuallyBalance.compareTo(zero) < 0;
        Boolean balanceEqualZero = actuallyBalance.compareTo(zero) == 0;

        if (balanceBelowZero) {
            debitDecorator.updateBalance(zero);
            debitDecorator.updateDebet(actuallyBalance.multiply(minusOne));
        } else if (balanceEqualZero) {
            debitDecorator.updateBalance(zero);
            debitDecorator.updateDebet(zero);
        } else {
            debitDecorator.updateBalance(actuallyBalance);
            debitDecorator.updateDebet(zero);
        }
        this.setTransactionStatus("SUCCESS");
    }

    private void updateBalanceOnCreditAccount() {
        BigDecimal actuallyBalance = creditDecorator.checkBalance();
        actuallyBalance = actuallyBalance.subtract(this.getAmount());
        creditDecorator.updateBalance(actuallyBalance);
        this.setTransactionStatus("SUCCESS");
    }

    private void updateBalanceOnBankAccount() {
        BigDecimal actuallyBalance = bankAccountToPayment.checkBalance();
        actuallyBalance = actuallyBalance.add(this.getAmount());
        bankAccountToPayment.updateBalance(actuallyBalance);
        this.setTransactionStatus("SUCCESS");
    }

    public TransactionPayment getTransaction() {
        return this;
    }

    @Override
    public void accept(VisitorService v) {
        v.visit(this);
    }
}
