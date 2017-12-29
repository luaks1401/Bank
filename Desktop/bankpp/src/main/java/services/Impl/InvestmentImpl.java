package services.Impl;

import entity.Investment;
import services.InvestmentService;

import java.math.BigDecimal;
import java.util.Date;

public class InvestmentImpl implements InvestmentService {

    private Investment investment;

    @Override
    public void addInvestment() {
        investment = new Investment();
    }

    @Override
    public void payment(BigDecimal amount, int duration, double interestInvestment) {
        investment.setBalance2(amount);
        investment.setStartDate(new Date());
        investment.setDuration(duration);
        investment.setInterestInvestment(interestInvestment);
    }

    @Override
    public BigDecimal calculateInterest() {
        double interestInvestment = investment.getInterestInvestment()/100;
        double day = (double)investment.getDuration()*30/365;
        return scale(investment.getBalance2().multiply(new BigDecimal(interestInvestment)).multiply(new BigDecimal(day)));
    }

    @Override
    public Investment getInvestment() {
        return investment;
    }

    @Override
    public BigDecimal breakInvestment() {
        return investment.getBalance2();
    }

    @Override
    public BigDecimal closeInvestment() {
        return investment.getBalance2().add(calculateInterest());
    }

    private BigDecimal scale(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}