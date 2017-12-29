package com.company.other.Bank;

import com.company.services.BankAccountService;
import com.company.services.BankService;
import com.company.services.Impl.BankAccountImpl;
import com.company.services.Impl.BankImpl;
import com.company.services.Impl.CreditImpl;
import com.company.services.Impl.DebetImpl;
import com.company.services.TransactionService;
import com.company.services.transaction.TransactionInterbankTransfer;
import com.company.services.transaction.TransactionPayment;
import com.company.services.transaction.TransactionWithdraw;
import com.company.services.transaction.TransactionTransfer;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TransactionTest {
    private BankAccountService bankAccountService;
    private BigDecimal zero = BigDecimal.valueOf(0);
    private BigDecimal fifty = BigDecimal.valueOf(50);
    private BigDecimal minusFifty = BigDecimal.valueOf(-50);
    private BigDecimal hundredFifty = BigDecimal.valueOf(150);
    private BigDecimal hundred = BigDecimal.valueOf(100);
    private BigDecimal fouHundred = BigDecimal.valueOf(400);
    private BankAccountService basicAccount;
    private BankAccountService onlyBasicAccount;
    private BankAccountService basicAccount2;
    private BankAccountService basicAccount3;
    private DebetImpl debitDecorator;
    private CreditImpl creditDecorator;

    @Before
    public void setUp() throws Exception {
        bankAccountService = new BankAccountImpl();
        basicAccount = new BankAccountImpl();
        onlyBasicAccount = new BankAccountImpl();
        basicAccount2 = new BankAccountImpl();
        basicAccount3 = new BankAccountImpl();
        debitDecorator = new DebetImpl(basicAccount);
    }

    @Test
    public void updateAccountProperties() {
        basicAccount.updateBalance(fifty);
        debitDecorator.updateMaxDebetAmount(hundred);
        debitDecorator.updateDebet(fifty);
        assertEquals(debitDecorator.checkMaxDebetAmount(), hundred);
        assertEquals(debitDecorator.getDebet(), fifty);
        assertEquals(basicAccount.checkBalance(), fifty);
    }

    //CAN WITHDRAW
    @Test
    public void canWithdraw1() {
        basicAccount.updateBalance(fifty);
        debitDecorator.updateMaxDebetAmount(hundred);
        debitDecorator.updateDebet(fifty);
        Boolean canWithdraw = debitDecorator.checkWithdrawAccess(hundred);
        assertTrue(canWithdraw);
    }

    @Test
    public void canNotWithdraw2() {
        basicAccount.updateBalance(zero);

        debitDecorator.updateMaxDebetAmount(hundred);
        debitDecorator.updateDebet(fifty);
        Boolean canWithdraw = debitDecorator.checkWithdrawAccess(hundred);
        assertFalse(canWithdraw);
    }

    //DEBET
    @Test
    public void debetShouldBeOff() {
        debitDecorator.switchDebet();
        Boolean canWithdraw = debitDecorator.debetIsOn();
        assertFalse(canWithdraw);
    }

    @Test
    public void debetShouldBeOn() {
        debitDecorator.updateDebet(fifty);
        debitDecorator.switchDebet();
        Boolean canWithdraw = debitDecorator.debetIsOn();
        assertTrue(canWithdraw);
    }

    @Test
    public void debetShouldBeOn2() {
        debitDecorator.updateMaxDebetAmount(fifty);
        basicAccount.updateBalance(hundred);
        assertEquals(debitDecorator.checkMaxBalance(), hundredFifty);
    }

    // WITHDRAW
    @Test
    public void shouldWithdrawFromBankAccount() {
        basicAccount.updateBalance(hundredFifty);
        TransactionService transactionWithdraw = new TransactionWithdraw(basicAccount, hundred);
        transactionWithdraw.execute();
        assertEquals(basicAccount.checkMaxBalance(), fifty);
    }

    @Test
    public void shouldNotWithdrawFromBankAccount() {
        basicAccount.updateBalance(fifty);
        TransactionService transactionWithdraw = new TransactionWithdraw(basicAccount, hundred);
        transactionWithdraw.execute();
        assertEquals(basicAccount.checkMaxBalance(), fifty);
    }

    @Test
    public void shouldNotWithdrawFromDebetAccount() {
        basicAccount.updateBalance(fifty);
        TransactionService transactionWithdraw = new TransactionWithdraw(debitDecorator, fouHundred);
        transactionWithdraw.execute();
        assertEquals(basicAccount.checkMaxBalance(), fifty);
    }

    @Test
    public void shouldNotWithdrawFromDebetAccount2() {
        basicAccount.updateBalance(hundred);
        TransactionService transactionWithdraw = new TransactionWithdraw(debitDecorator, fifty);
        transactionWithdraw.execute();
        assertEquals(debitDecorator.getDebet(), zero);
        assertEquals(debitDecorator.checkBalance(), fifty);
    }

    @Test
    public void shouldNotWithdrawFromDebetAccount3() {
        basicAccount.updateBalance(fifty);
        TransactionService transactionWithdraw = new TransactionWithdraw(debitDecorator, hundred);
        transactionWithdraw.execute();
        assertEquals(debitDecorator.getDebet(), fifty);
        assertEquals(debitDecorator.checkBalance(), minusFifty);
    }

    @Test
    public void shouldNotWithdrawFromDebetAccount4() {
        TransactionService transactionWithdraw = new TransactionWithdraw(debitDecorator, hundredFifty);
        transactionWithdraw.execute();
        assertEquals(debitDecorator.getDebet(), zero);
        assertEquals(debitDecorator.checkBalance(), zero);
    }

    //INTERBANK TRANSFER - BANK TEST
    void addAccountToBank(BankService bankService) {
        BankAccountService newAccount;
        for (int i = 0; i < 5; i++) {
            newAccount = new BankAccountImpl();
            newAccount.setAccountNumber(bankService.checkBankId() * 100 + i);
            bankService.addToBankAccountList(newAccount);
        }
    }

    @Test
    public void t2() {
        BankService bankService = new BankImpl(100);
        addAccountToBank(bankService);
        TransactionService transactionInterbankTransfer =
                new TransactionInterbankTransfer(basicAccount, bankService, 10003, fifty);
        transactionInterbankTransfer.execute();
        assertEquals(bankService.getBankAccountAboutNumber(10003).checkBalance(), fifty);
    }

    @Test
    public void t3() {
        BankService bankService = new BankImpl(100);
        addAccountToBank(bankService);

        basicAccount.updateBalance(fifty);
        TransactionService transactionInterbankTransfer =
                new TransactionInterbankTransfer(basicAccount, bankService, 10009, fifty);
        transactionInterbankTransfer.execute();
        assertEquals(basicAccount.checkBalance(), fifty);
    }

    // PAYMENT
    @Test
    public void shouldDoPayemntOnBasicAccount() {
        TransactionService transactionPaymand = new TransactionPayment(basicAccount, hundred);
        transactionPaymand.execute();
        assertEquals(basicAccount.checkBalance(), hundred);
    }

    @Test
    public void shouldDoPayemntOnDebitAccount() {
        debitDecorator.updateDebet(fifty);
        TransactionService transactionPaymand = new TransactionPayment(debitDecorator, hundred);
        transactionPaymand.execute();
        assertEquals(debitDecorator.checkBalance(), fifty);
    }

    @Test
    public void shouldDoNotPayemntOnBasicAccount() {
        basicAccount.updateBalance(fifty);
        TransactionService transactionPaymand = new TransactionPayment(basicAccount, minusFifty);
        transactionPaymand.execute();
        assertEquals(basicAccount.checkBalance(), fifty);
    }

    @Test
    public void shouldDoNotPayemntOnDebitAccount() {
        debitDecorator.updateBalance(fifty);
        TransactionService transactionPaymand = new TransactionPayment(debitDecorator, zero);
        transactionPaymand.execute();
        assertEquals(debitDecorator.checkBalance(), fifty);
    }

    @Test
    public void shouldPaymentToCreditBankAccount() {
        creditDecorator = new CreditImpl(basicAccount3, BigDecimal.valueOf(10000), BigDecimal.valueOf(1.17), 24);
        TransactionService transactionPayment = new TransactionPayment(creditDecorator, hundred);
        transactionPayment.execute();
        assertEquals(creditDecorator.checkBalance(), BigDecimal.valueOf(9900));
    }

    // TRANSFER
    @Test
    public void transfer1() {
        debitDecorator.updateBalance(hundredFifty);
        TransactionService transactionTransfer = new TransactionTransfer(debitDecorator, basicAccount2, fifty);
        transactionTransfer.execute();
        assertEquals(debitDecorator.checkBalance(), hundred);
        assertEquals(basicAccount2.checkBalance(), fifty);
    }

    @Test
    public void transfer2() {
        TransactionService transactionTransfer = new TransactionTransfer(debitDecorator, basicAccount2, hundredFifty);
        transactionTransfer.execute();
        assertEquals(debitDecorator.checkBalance(), zero);
        assertEquals(basicAccount2.checkBalance(), zero);
    }

    @Test
    public void transfer3() {
        TransactionService transactionTransfer = new TransactionTransfer(onlyBasicAccount, basicAccount2, fifty);
        transactionTransfer.execute();
        assertEquals(onlyBasicAccount.checkBalance(), zero);
        assertEquals(basicAccount2.checkBalance(), zero);
    }

    @Test
    public void transfer4() {
        onlyBasicAccount.updateBalance(hundredFifty);
        TransactionService transactionTransfer = new TransactionTransfer(onlyBasicAccount, basicAccount2, fifty);
        transactionTransfer.execute();
        assertEquals(onlyBasicAccount.checkBalance(), hundred);
        assertEquals(basicAccount2.checkBalance(), fifty);
    }
}