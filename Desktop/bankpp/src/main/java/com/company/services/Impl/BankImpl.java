package com.company.services.Impl;

import com.company.entity.Bank;
import com.company.services.BankAccountService;
import com.company.services.BankService;

import java.util.List;

public class BankImpl implements BankService {
    private Bank bank;

    public BankImpl(int id) {
        bank = new Bank(id);
    }

    @Override
    public BankAccountService getBankAccountAboutNumber(int accountNumber) {
        for (BankAccountService account : bank.getBankAccountList()) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    @Override
    public List<BankAccountService> checkBankAccountList() {
        return bank.getBankAccountList();
    }

    @Override
    public void addToBankAccountList(BankAccountService account) {
        bank.getBankAccountList().add(account);
    }

    @Override
    public int checkBankId() {
        return bank.getBankId();
    }
}
