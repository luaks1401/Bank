package services.investment;

import services.Impl.BankAccountImpl;

import java.math.BigDecimal;

public interface InterestmentMechanismService {

    BigDecimal calculateInterest (BankAccountImpl basicBankAccount);
}
