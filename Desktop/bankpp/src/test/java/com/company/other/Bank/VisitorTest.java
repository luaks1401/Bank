package com.company.other.Bank;

import com.company.services.BankAccountService;
import com.company.services.Impl.BankAccountImpl;
import com.company.services.Impl.DebetImpl;
import com.company.services.Visitor.ElementService;
import com.company.services.Visitor.UpVisitor;
import com.company.services.Visitor.RaportAccountVisitor;
import com.company.services.transaction.TransactionWithdraw;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class VisitorTest {
    private BigDecimal fifty = BigDecimal.valueOf(50);
    private BigDecimal hundredFifty = BigDecimal.valueOf(150);
    private BigDecimal hundred = BigDecimal.valueOf(100);
    private BankAccountService basicAccount;
    private BankAccountService basicAccount2;
    private BankAccountService basicAccount3;
    private BankAccountService basicAccount4;
    private BankAccountService basicAccount5;
    private BankAccountService basicAccount6;
    private DebetImpl debitDecorator;
    private DebetImpl debitDecorator2;
    private DebetImpl debitDecorator3;


    @Before
    public void setUp() throws Exception {
        basicAccount = new BankAccountImpl();
        basicAccount2 = new BankAccountImpl();
        basicAccount3 = new BankAccountImpl();

        basicAccount4 = new BankAccountImpl();
        basicAccount5 = new BankAccountImpl();
        basicAccount6 = new BankAccountImpl();

        debitDecorator = new DebetImpl(basicAccount);
        debitDecorator2 = new DebetImpl(basicAccount2);
        debitDecorator3 = new DebetImpl(basicAccount3);
    }

    @Test
    public void shouldNotWithdrawFromDebetAccount4() {
        basicAccount.updateBalance(hundredFifty);
        TransactionWithdraw transactionWithdraw = new TransactionWithdraw(basicAccount, hundred);
        transactionWithdraw.execute();

        basicAccount2.updateBalance(hundred);
        TransactionWithdraw transactionWithdraw2 = new TransactionWithdraw(basicAccount2, fifty);
        transactionWithdraw2.execute();
        assertEquals(basicAccount.checkMaxBalance(), fifty);

        debitDecorator.updateBalance(hundred);
        TransactionWithdraw transactionWithdraw3 = new TransactionWithdraw(debitDecorator, fifty);
        transactionWithdraw3.execute();

        ElementService[] list = {transactionWithdraw, transactionWithdraw2, transactionWithdraw3};
        UpVisitor up = new UpVisitor();
        for (ElementService element : list) {
            element.accept(up);
        }
    }

    @Test
    public void t() {
        basicAccount4.updateBalance(BigDecimal.valueOf(1000));
        basicAccount5.updateBalance(BigDecimal.valueOf(100));
        basicAccount6.updateBalance(BigDecimal.valueOf(2000));

        basicAccount.updateBalance(BigDecimal.valueOf(100));
        debitDecorator3.updateDebet(BigDecimal.valueOf(50));

        ElementService[] list = {(ElementService) basicAccount4, (ElementService) basicAccount5, (ElementService) basicAccount6, debitDecorator, debitDecorator2, debitDecorator3};
        RaportAccountVisitor up = new RaportAccountVisitor();
        for (ElementService element : list) {
            element.accept(up);
        }
        System.out.println("NUMBER OF ACCOUNTS 999: " + up.getSuma());
    }
}