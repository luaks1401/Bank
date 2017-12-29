package com.company.services.investment;

import com.company.services.Impl.BankAccountImpl;

import java.math.BigDecimal;

public interface InterestmentMechanismService {

    BigDecimal calculateInterest (BankAccountImpl basicBankAccount);
}
