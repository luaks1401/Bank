package services.transaction;

import entity.Transaction;
import services.BankAccountService;
import services.Impl.DebetImpl;
import services.TransactionService;
import services.Visitor.ElementService;
import services.Visitor.VisitorService;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionTransfer extends Transaction implements ElementService, TransactionService {

    private int toAccountNumber;
    private BankAccountService fromBankAccount;
    private BankAccountService toBankAccount;

    public TransactionTransfer(BankAccountService fromBankAccount, BankAccountService toBankAccount, BigDecimal amountToTransfer) {
        this.setDate(new Date());
        this.setFromAccountNumber(fromBankAccount.getAccountNumber());
        this.toAccountNumber = toBankAccount.getAccountNumber();
        this.fromBankAccount = fromBankAccount;
        this.toBankAccount = toBankAccount;
        this.setAmount(amountToTransfer);
        this.setType("TRANSFER");
    }

    public TransactionTransfer(DebetImpl debetImpl, BankAccountService toBankAccount, BigDecimal amountToTransfer) {
        this.setDate(new Date());
        this.setFromAccountNumber(debetImpl.getAccountNumber());
        this.toAccountNumber = toBankAccount.getAccountNumber();
        this.fromBankAccount = debetImpl;
        this.toBankAccount = toBankAccount;
        this.setAmount(amountToTransfer);
        this.setType("TRANSFER");
    }

    public void execute() {
        if (this.getCanDoTransaction()) {
            try {
                this.setCanDoTransaction(false);
                TransactionPayment transactionPayment = null;
                TransactionWithdraw transactionWithdraw = new TransactionWithdraw(this.fromBankAccount, this.getAmount());
                transactionWithdraw.execute();
                if (transactionWithdraw.getTransactionStatus().equals("SUCCESS")) {
                    transactionPayment = new TransactionPayment(this.toBankAccount, this.getAmount());
                    transactionPayment.execute();
                    this.setTransactionStatus("SUCCESS");
                } else {
                    this.setTransactionStatus("TRANSACTION FAILURE");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void accept(VisitorService v) {
        v.visit(this);
    }
}
