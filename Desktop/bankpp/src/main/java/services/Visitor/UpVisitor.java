package services.Visitor;

import services.Impl.BankAccountImpl;
import services.Impl.CreditImpl;
import services.Impl.DebetImpl;
import services.transaction.TransactionInterbankTransfer;
import services.transaction.TransactionPayment;
import services.transaction.TransactionTransfer;
import services.transaction.TransactionWithdraw;

public class UpVisitor implements VisitorService {

    @Override
    public void visit(TransactionPayment transaction) {
        if (transaction.getTransactionStatus().contains("SUCCESS")) {
            System.out.println("ACCOUNT NUMBER  " + transaction.getFromAccountNumber() + " TYPE: " +
                    transaction.getType() + " AMOUNT: " + transaction.getAmount());
        }
    }

    @Override
    public void visit(TransactionWithdraw transaction) {
        if (transaction.getTransactionStatus().contains("SUCCESS")) {
            System.out.println("ACCOUNT NUMBER  " + transaction.getFromAccountNumber() + " TYPE: " +
                    transaction.getType() + " AMOUNT: " + transaction.getAmount());
        }
    }

    @Override
    public void visit(TransactionTransfer transaction) {
        if (transaction.getTransactionStatus().contains("SUCCESS")) {
            System.out.println("ACCOUNT NUMBER  " + transaction.getFromAccountNumber() + " TYPE: " +
                    transaction.getType() + " AMOUNT: " + transaction.getAmount());
        }
    }

    @Override
    public void visit(TransactionInterbankTransfer transaction) {
        if (transaction.getTransactionStatus().contains("SUCCESS")) {
            System.out.println("ACCOUNT NUMBER  " + transaction.getFromAccountNumber() + " TYPE: " +
                    transaction.getType() + " AMOUNT: " + transaction.getAmount());
        }
    }

    @Override
    public void visit(BankAccountImpl bankAccount) {}

    @Override
    public void visit(DebetImpl debet) {}

    @Override
    public void visit(CreditImpl credit) {}
}
