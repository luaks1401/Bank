package services.transaction;

import entity.Transaction;
import services.BankAccountService;
import services.BankService;
import services.Impl.DebetImpl;
import services.TransactionService;
import services.Visitor.ElementService;
import services.Visitor.VisitorService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TransactionInterbankTransfer extends Transaction implements ElementService, TransactionService {

    private BankAccountService bankAccountFrom;
    private BankAccountService bankAccountTo;
    private BankService bank;
    private int bankNumber;

    public TransactionInterbankTransfer(BankAccountService bankAccountFrom, BankService bankService, int toBankAccountNumber, BigDecimal amountToWithdraw) {
        this.setDate(new Date());
        this.setFromAccountNumber(bankAccountFrom.getAccountNumber());
        this.bankAccountFrom = bankAccountFrom;
        this.bank = bankService;
        this.bankNumber = toBankAccountNumber;
        this.setAmount(amountToWithdraw);
        this.setType("TRANSFET INTERBANK");
    }

    public TransactionInterbankTransfer(DebetImpl bankAccountFrom, BankService bankService, int toBankAccountNumber, BigDecimal amountToWithdraw) {
        this.setDate(new Date());
        this.setFromAccountNumber(bankAccountFrom.getAccountNumber());
        this.bankAccountFrom = bankAccountFrom;
        this.bank = bankService;
        this.bankNumber = toBankAccountNumber;
        this.setAmount(amountToWithdraw);
        this.setType("TRANSFET INTERBANK");
    }

    public void execute() {
        if (this.getCanDoTransaction()) {
            try {
                findBankAccoundToTransfer();
                TransactionWithdraw transactionWithdraw = new TransactionWithdraw(this.bankAccountFrom, this.getAmount());
                transactionWithdraw.execute();
                if (this.bankAccountTo != null) {
                    TransactionPayment transactionPayment = new TransactionPayment(this.bankAccountTo, this.getAmount());
                    transactionPayment.execute();
                    this.setTransactionStatus("SUCCESS - TRANSFER MONAY TO ANOTHER BANKU");
                } else {
                    TransactionReturnTransfer transactionReturnTransfer = new TransactionReturnTransfer(this.bankAccountFrom, this.getAmount());
                    transactionReturnTransfer.execute();
                    this.setTransactionStatus("BANK ACCOUNT TO TRANSFER NOT EXIST");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private void findBankAccoundToTransfer(){
        int accountNumber;
        List<BankAccountService> list = this.bank.checkBankAccountList();
        for (BankAccountService accountService : list) {
            accountNumber = accountService.getAccountNumber();
            if (accountNumber == this.bankNumber) {
                this.bankAccountTo = accountService;
                break;
            }
        }
    }

    @Override
    public void accept(VisitorService v) {
        v.visit(this);
    }

    public TransactionInterbankTransfer getTransaction() {
        return this;
    }

}
