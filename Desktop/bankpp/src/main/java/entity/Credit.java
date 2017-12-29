package entity;

import services.BankAccountService;

import java.math.BigDecimal;
import java.util.Date;

public class Credit {
    private BigDecimal amountCredit;    //wartosc do splaty
    private BigDecimal installmentCredit; //rata kredytu
    private BigDecimal interestCredit;  //oprocentowanie kredytu
    private Date startDate;
    private int durationInMonth;   //czas do splaty w miesiacach
    private BankAccountService bankAccount;


    public Credit(BankAccountService bankAccount, BigDecimal amountCredit, BigDecimal interestCredit, int durationInMonth) {
        this.amountCredit = amountCredit;
        this.bankAccount = bankAccount;
        this.interestCredit = interestCredit;
        this.durationInMonth = durationInMonth;
    }

    public int getDurationInMonth() {
        return durationInMonth;
    }

    public void setDurationInMonth(int durationInMonth) {
        this.durationInMonth = durationInMonth;
    }

    public BigDecimal getInterestCredit() {
        return interestCredit;
    }

    public void setInterestCredit(BigDecimal interestCredit){
        this.interestCredit = interestCredit;
    }

    public BigDecimal getAmountCredit() {
        return this.amountCredit;
    }

    public void setAmountCredit(BigDecimal amountCredit) {
        this.amountCredit = amountCredit;
    }

    public BankAccountService getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountService bankAccount) {
        this.bankAccount = bankAccount;
    }

    public BigDecimal getInstallmentCredit() {
        return this.installmentCredit;
    }

    public void setInstallmentCredit(BigDecimal installmentCredit) {
        this.installmentCredit = installmentCredit;
    }
}
