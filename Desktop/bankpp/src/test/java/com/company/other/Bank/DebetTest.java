package com.company.other.Bank;

import com.company.services.BankAccountService;
import com.company.services.Impl.BankAccountImpl;
import com.company.services.Impl.DebetImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class DebetTest {

    private BigDecimal zero = BigDecimal.valueOf(0);
    private BigDecimal fifty = BigDecimal.valueOf(50);
    private BigDecimal hundredFifty = BigDecimal.valueOf(150);
    private BigDecimal hundred = BigDecimal.valueOf(100);
    private BankAccountService basicAccount;
    private DebetImpl debitDecorator;

    @Before
    public void setUp() {
        basicAccount = new BankAccountImpl();
        debitDecorator = new DebetImpl(basicAccount);
    }

    @Test
    public void debetShouldBeCreated() {
        assertNotNull(debitDecorator);
    }

    @Test
    public void debetShouldBeZero() {
        assertEquals(debitDecorator.checkBalance(), zero);
    }

    @Test
    public void maxBalanceShouldBeHundredFifty() {
        basicAccount.updateBalance(fifty);
        assertEquals(debitDecorator.checkMaxBalance(), hundredFifty);
    }

    @Test
    public void debetShouldBeOn() {
        assertTrue(debitDecorator.debetIsOn());
    }

    @Test
    public void debetShouldBeOff() {
        debitDecorator.switchDebet();
        assertFalse(debitDecorator.debetIsOn());
    }

    @Test
    public void maxDebetAmountShouldBeHundredAfterCreatedAccount() {
        assertEquals(debitDecorator.checkMaxDebetAmount(), hundred);
    }

    @Test
    public void maxDebetAmountShouldBe500() {
        debitDecorator.updateMaxDebetAmount(new BigDecimal(500));
        assertEquals(debitDecorator.checkMaxDebetAmount(), BigDecimal.valueOf(500));
    }

    @Test
    public void allBalanceShouldBeMinusFifty() {
        debitDecorator.updateDebet(fifty);
        assertEquals(debitDecorator.checkBalance(), BigDecimal.valueOf(-50));
    }

    @Test
    public void debedShouldBeStillOn() {
        debitDecorator.updateDebet(fifty);
        debitDecorator.switchDebet();
        assertTrue(debitDecorator.debetIsOn());
    }

    @Test
    public void maxDebedShouldBeStillHundred() {
        debitDecorator.updateDebet(fifty);
        debitDecorator.switchDebet();
        assertEquals(debitDecorator.checkMaxDebetAmount(), hundred);
    }

    @Test
    public void maxDebetAmountShouldBeZeroAfterTurnOffDebet() {
        debitDecorator.switchDebet();
        assertEquals(debitDecorator.checkMaxDebetAmount(), zero);
        assertFalse(debitDecorator.debetIsOn());
    }
}
