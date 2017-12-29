package services;

import java.util.List;

public interface BankService {

    public BankAccountService getBankAccountAboutNumber(int accountNumber);

    public List<BankAccountService> checkBankAccountList();

    public void addToBankAccountList(BankAccountService account);

    public int checkBankId();
}
