package test;

import services.BankAccountService;
import services.Impl.BankAccountImpl;
import services.Impl.CreditImpl;
import services.Impl.DebetImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BankAccountTest {

    private BigDecimal zero = BigDecimal.valueOf(0);
    private BigDecimal fifty = BigDecimal.valueOf(50);
    private BigDecimal fourThousand = BigDecimal.valueOf(4000);
    private BigDecimal sevenThousand = BigDecimal.valueOf(7000);
    private BankAccountService basicAccount;
    private DebetImpl debitDecorator = null ;
    private CreditImpl creditDecorator = null;

    @Before
    public void setUp() {
        basicAccount = new BankAccountImpl();
        debitDecorator = new DebetImpl(basicAccount);
    }

    @Test
    public void bankAccountShouldBeCreated() {
        assertNotNull(basicAccount);
    }

    @Test
    public void balanceShouldBeZero() {
        assertEquals(basicAccount.checkBalance(), zero);
    }

    @Test
    public void balanceShouldBeUpdatedToFifty() {
        basicAccount.updateBalance(fifty);
        assertEquals(basicAccount.checkMaxBalance(), fifty);
    }

    @Test
    public void balanceShouldBeUpdatedToFifty2() {
        basicAccount.updateBalance(BigDecimal.valueOf(-50));
        assertEquals(basicAccount.checkBalance(), zero);
    }

    // INTEREST MECHANISM
    @Test
    public void interestShouldBeZero() { //odsetki beda 0 bo na koncie 0
        assertEquals(basicAccount.checkBalance(), zero);
        assertEquals(basicAccount.updateInterestMechanism(), scale(zero));
    }

    @Test
    public void interestShouldBeZeroCommaTwentyOne() { //odsetki beda 0,21 bo na koncie 50
        basicAccount.updateBalance(fifty);
        assertEquals(basicAccount.checkBalance(), fifty);
        assertEquals(basicAccount.updateInterestMechanism(), BigDecimal.valueOf(0.21));
    }

    @Test
    public void interestShouldBeThirtyFour() { //odsetki beda 34 bo na koncie 4000
        basicAccount.updateBalance(fourThousand);
        assertEquals(basicAccount.checkBalance(), fourThousand);
        assertEquals(basicAccount.updateInterestMechanism(), scale(BigDecimal.valueOf(34)));
    }

    @Test
    public void interestShouldBeEightySevenAndHalf() { //odsetki beda 87,5 bo na koncie 7000
        basicAccount.updateBalance(sevenThousand);
        assertEquals(basicAccount.checkBalance(), sevenThousand);
        assertEquals(basicAccount.updateInterestMechanism(), scale(BigDecimal.valueOf(87.5)));
    }

    //CREDIT
    @Test
    public void amountOfCreditShouldBe10000() {
        creditDecorator = new CreditImpl(basicAccount, BigDecimal.valueOf(10000), BigDecimal.valueOf(1.17), 24);
        assertEquals(creditDecorator.checkBalance(), BigDecimal.valueOf(10000));
    }

    @Test
    public void installmentCreditEquals487() {
        creditDecorator = new CreditImpl(basicAccount, BigDecimal.valueOf(10000), BigDecimal.valueOf(1.17), 24);
        assertEquals(creditDecorator.checkInstallment(), scale(BigDecimal.valueOf(487.50)));
    }

    private BigDecimal scale(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
