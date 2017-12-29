package services.transaction;

import entity.Transaction;
import services.BankAccountService;
import services.Impl.DebetImpl;
import services.TransactionService;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionReturnTransfer extends Transaction implements TransactionService {

    private BankAccountService bankAccountTo;
    private DebetImpl debitDecorator;

    public TransactionReturnTransfer(BankAccountService bankAccountTo, BigDecimal amountToWithdraw) {
        this.setDate(new Date());
        this.setFromAccountNumber(bankAccountTo.getAccountNumber());
        this.bankAccountTo = bankAccountTo;
        this.setAmount(amountToWithdraw);
        this.setType("TRANSFER INTERBANK");
    }

    public TransactionReturnTransfer(DebetImpl debetImpl, BigDecimal amountToWithdraw) {
        this.setDate(new Date());
        this.setFromAccountNumber(debetImpl.getAccountNumber());
        this.bankAccountTo = debetImpl.bankAccountService;
        this.setAmount(amountToWithdraw);
        debitDecorator = debetImpl;
        this.setType("TRANSFER INTERBANK");
    }

    public void execute() {
        TransactionPayment transactionPayment;
        if (this.getCanDoTransaction()) {
            try {
                if (debitDecorator == null) {
                    transactionPayment = new TransactionPayment(bankAccountTo, this.getAmount());
                } else {
                    transactionPayment = new TransactionPayment(debitDecorator, this.getAmount());
                }
                transactionPayment.execute();
                this.setTransactionStatus("SUCCESS");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                this.setTransactionStatus(e.getMessage());
            }
        }
    }

    public TransactionReturnTransfer getTransaction() {
        return this;
    }
}

