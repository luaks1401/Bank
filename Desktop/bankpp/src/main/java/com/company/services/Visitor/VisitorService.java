package com.company.services.Visitor;

import com.company.services.Impl.BankAccountImpl;
import com.company.services.Impl.CreditImpl;
import com.company.services.Impl.DebetImpl;
import com.company.services.transaction.TransactionInterbankTransfer;
import com.company.services.transaction.TransactionPayment;
import com.company.services.transaction.TransactionTransfer;
import com.company.services.transaction.TransactionWithdraw;

public interface VisitorService {
    public void visit(TransactionPayment transactionPayment);
    public void visit(TransactionWithdraw transactionWithdraw);
    public void visit(TransactionTransfer transactionTransfer);
    public void visit(TransactionInterbankTransfer transactionInterbankTransfer);
    public void visit(BankAccountImpl bankAccount);
    public void visit(DebetImpl debet);
    public void visit(CreditImpl credit);
}
