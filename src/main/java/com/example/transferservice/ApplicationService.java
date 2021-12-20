package com.example.transferservice;


import com.example.transferservice.domain.aggregates.TransferMoneyBetweenAccounts;
import com.example.transferservice.dtos.TransactionDTO;
import com.example.transferservice.infrasctructure.ApplicationException;
import com.example.transferservice.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ApplicationService {

    private final AccountRepository accountRepository;


    public ApplicationService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void transferMoney(TransactionDTO transactionDTO) throws ApplicationException {

        log.debug("transactionDTO : " + transactionDTO.toString());

            var ownerAccount = accountRepository.findById(transactionDTO.getSourceAccountId());
            var recevingAccount = accountRepository.findById(transactionDTO.getAccountsIDToBeCredit());

            if (ownerAccount.isEmpty()) {
                throw new ApplicationException("The source account doesn't exist");
            }

            if (transactionDTO.getSourceAccountId().equals(transactionDTO.getAccountsIDToBeCredit()))
                throw new ApplicationException("You cannot transfer money in the same account");

            if (recevingAccount.isEmpty()) {
                throw new ApplicationException("The receiving account doesn't exist");
            }


            new TransferMoneyBetweenAccounts(ownerAccount.get(),recevingAccount.get(), new BigDecimal(transactionDTO.getTransactionAmount()));

        }
    }


