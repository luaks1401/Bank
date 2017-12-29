package com.company.entity;

import com.company.services.BankAccountService;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<BankAccountService> bankAccountList = new ArrayList<BankAccountService>();
    private int bankId;

    public Bank(int id) {
        this.bankId = id;
    }

    public List<BankAccountService> getBankAccountList() { return bankAccountList; }

    public void setBankAccountList(List<BankAccountService> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }
}
