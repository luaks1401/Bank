package services.Visitor;

import services.Impl.BankAccountImpl;
import services.Impl.CreditImpl;
import services.Impl.DebetImpl;
import services.transaction.TransactionInterbankTransfer;
import services.transaction.TransactionPayment;
import services.transaction.TransactionTransfer;
import services.transaction.TransactionWithdraw;

import java.math.BigDecimal;

public class RaportAccountVisitor implements VisitorService {
    private int suma = 0;
    public int getSuma() {
        return suma;
    }

    @Override
    public void visit(TransactionPayment transaction) {}

    @Override
    public void visit(TransactionWithdraw transaction) {}

    @Override
    public void visit(TransactionTransfer transaction) {}

    @Override
    public void visit(TransactionInterbankTransfer transaction) {}

    @Override
    public void visit(BankAccountImpl bankAccount) {
        if (bankAccount.checkBalance().compareTo(BigDecimal.valueOf(1000)) > -1) {
            suma++;
        }
    }

    @Override
    public void visit(DebetImpl debet) {
        if (debet.checkBalance().compareTo(BigDecimal.valueOf(1000)) > -1) {
            suma++;
        }
    }
    @Override
    public void visit(CreditImpl credit) {}
}
