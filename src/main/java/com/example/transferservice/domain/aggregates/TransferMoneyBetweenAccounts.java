package com.example.transferservice.domain.aggregates;


import com.example.transferservice.domain.entities.Account;
import com.example.transferservice.infrasctructure.ApplicationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class TransferMoneyBetweenAccounts {

    private  Account sourceAccount;
    private  Account receivingAccount;


   public void transferMoneyBetweenAccounts(@NonNull Account sourceAccount,@NonNull Account receivingAccount,@NonNull BigDecimal transactionAmount) throws ApplicationException {

        this.sourceAccount = sourceAccount;
        this.receivingAccount = receivingAccount;

        var sourceAccountBalance = sourceAccount.getBalance();
        var recevingAccountBalance = receivingAccount.getBalance();

            if (sourceAccountBalance.compareTo(transactionAmount) >= 0) {

                sourceAccountBalance = sourceAccountBalance.subtract(transactionAmount);
                recevingAccountBalance = recevingAccountBalance.add(transactionAmount);

                log.debug("The source account after transfer is " + sourceAccountBalance);
                log.debug("the receiving account after transfer is  " + recevingAccountBalance);
                //update Source Account
                sourceAccount.setBalance(sourceAccountBalance);
                //update Receving Account
                receivingAccount.setBalance(recevingAccountBalance);

            } else throw new ApplicationException("not enough funds");

        }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getReceivingAccount() {
        return receivingAccount;
    }
}



