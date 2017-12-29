package com.company.services;

import com.company.entity.Investment;

import java.math.BigDecimal;
import java.util.Date;

public interface InvestmentService {

    void addInvestment();

    void payment(BigDecimal amount, int duration, double interestInvestment);

    BigDecimal calculateInterest();

    Investment getInvestment();

    BigDecimal breakInvestment();

    BigDecimal closeInvestment();
}
