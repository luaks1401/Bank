package services.Visitor;

import services.Impl.BankAccountImpl;
import services.Impl.CreditImpl;
import services.Impl.DebetImpl;
import services.transaction.TransactionInterbankTransfer;
import services.transaction.TransactionPayment;
import services.transaction.TransactionTransfer;
import services.transaction.TransactionWithdraw;

public interface VisitorService {
    public void visit(TransactionPayment transactionPayment);
    public void visit(TransactionWithdraw transactionWithdraw);
    public void visit(TransactionTransfer transactionTransfer);
    public void visit(TransactionInterbankTransfer transactionInterbankTransfer);
    public void visit(BankAccountImpl bankAccount);
    public void visit(DebetImpl debet);
    public void visit(CreditImpl credit);
}
