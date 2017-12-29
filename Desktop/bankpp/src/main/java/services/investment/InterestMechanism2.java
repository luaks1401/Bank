package services.investment;

import services.Impl.BankAccountImpl;

import java.math.BigDecimal;

public class InterestMechanism2 implements InterestmentMechanismService {

    @Override
    public BigDecimal calculateInterest(BankAccountImpl basicBankAccount) {
        return scale(basicBankAccount.checkBalance().multiply(new BigDecimal(0.0085)));
    }

    private BigDecimal scale(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
