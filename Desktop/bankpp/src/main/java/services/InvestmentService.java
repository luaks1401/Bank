package services;

import entity.Investment;

import java.math.BigDecimal;

public interface InvestmentService {

    void addInvestment();

    void payment(BigDecimal amount, int duration, double interestInvestment);

    BigDecimal calculateInterest();

    Investment getInvestment();

    BigDecimal breakInvestment();

    BigDecimal closeInvestment();
}
