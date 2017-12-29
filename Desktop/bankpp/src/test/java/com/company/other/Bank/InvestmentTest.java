package com.company.other.Bank;

import com.company.entity.Investment;
import com.company.services.Impl.InvestmentImpl;
import com.company.services.InvestmentService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.any;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class InvestmentTest {
    InvestmentService investmentService;
    Investment investment;

    @Before
    public void setUp() {
        investmentService = new InvestmentImpl();
        investmentService.addInvestment();
        investmentService.payment(new BigDecimal(1000), 3, 2.0);
        investment = investmentService.getInvestment();
    }

    @Test
    public void shouldCreateInvestment() {
        assertNotNull(investmentService);
    }

    @Test
    public void BalanceShouldBe1000() {
        assertEquals(investment.getBalance2(), new BigDecimal(1000));
    }

    @Test
    public void shouldBeToday() {
        assertEquals(investment.getStartDate(), new Date());
    }

    @Test
    public void durationShouldBe3() {
        assertEquals(investment.getDuration(), 3);
    }

    @Test
    public void interestShouldBe2() {
        assertEquals(investment.getInterestInvestment(), 2.0, 0.1);
    }

    @Test
    public void shouldBe4_93AfterCalculateInterest() {
        assertEquals(investmentService.calculateInterest(), scale(new BigDecimal(4.93)));
    }

    @Test
    public void  whenBreakInvestmentShouldBeBalance() {
        assertEquals(investmentService.breakInvestment(), new BigDecimal(1000));
    }

    @Test
    public void  whenCloseInvestmentShouldBeBalanceAndInterest() {
        assertEquals(investmentService.closeInvestment(), scale(new BigDecimal(1004.93)));
    }

    private BigDecimal scale(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
