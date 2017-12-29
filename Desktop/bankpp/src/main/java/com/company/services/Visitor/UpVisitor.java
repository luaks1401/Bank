package com.company.services.Visitor;

import com.company.services.Impl.BankAccountImpl;
import com.company.services.Impl.CreditImpl;
import com.company.services.Impl.DebetImpl;
import com.company.services.transaction.TransactionInterbankTransfer;
import com.company.services.transaction.TransactionPayment;
import com.company.services.transaction.TransactionTransfer;
import com.company.services.transaction.TransactionWithdraw;

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
