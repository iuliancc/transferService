package com.example.transferservice;

import com.example.transferservice.domain.aggregates.TransferMoneyBetweenAccounts;
import com.example.transferservice.domain.entities.Account;
import com.example.transferservice.dtos.TransactionDTO;
import com.example.transferservice.infrasctructure.ApplicationException;
import com.example.transferservice.repository.AccountRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionSteps {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    ApplicationService applicationService;


    @Test
    public void transferMoneyBetweenSameAccount() {

        when(repository.findById("129654")).thenReturn(Optional.of(new Account("129654", new BigDecimal("10"), "GBP", LocalDateTime.parse("2017-05-05T10:40:53"))));

        var exception = Assertions.assertThrows(ApplicationException.class, () -> {

            applicationService.transferMoney(new TransactionDTO("129654", "129654", "10"));

        });
        Assertions.assertTrue(exception.getMessage().contains("You cannot transfer money in the same account"));
    }


    @Test
    public void transferMoneyBetweenDifferentAccountsWithSuccess() {

         when(repository.findById("129654")).thenReturn(Optional.of(new Account("129654", new BigDecimal("10"), "GBP", LocalDateTime.parse("2017-05-05T10:40:53"))));
         when(repository.findById("4687")).thenReturn(Optional.of(new Account("4687", new BigDecimal("10"), "GBP", LocalDateTime.parse("2017-05-05T10:40:53"))));

        var ownerAccount = repository.findById("129654");
        var recevingAccount = repository.findById("4687");

        applicationService.transferMoney(new TransactionDTO("129654", "4687", "10"));

        Assertions.assertEquals(new BigDecimal("0"), ownerAccount.get().getBalance());
        Assertions.assertEquals(new BigDecimal("20"), recevingAccount.get().getBalance());

    }

    @Test
    public void transferMoneyWithNoRecevingAccount() {
        when(repository.findById("129654")).thenReturn(Optional.of(new Account("129654", new BigDecimal("10"), "GBP", LocalDateTime.parse("2017-05-05T10:40:53"))));
        //receiving account doesn't exist in the repository
        when(repository.findById("4444")).thenReturn(Optional.empty());
        var ownerAccount = repository.findById("129654");

        var exception = Assertions.assertThrows(ApplicationException.class, () -> {

            applicationService.transferMoney(new TransactionDTO("129654", "4444", "5"));
        });

        Assertions.assertTrue(exception.getMessage().contains("The receiving account doesn't exist"));
        Assertions.assertEquals(new BigDecimal("10"), ownerAccount.get().getBalance());
    }


    @Test
    public void transferMoneyWithWrongOwnerAccount() {
        //account doesn't exist in the repository
        when(repository.findById("2223")).thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(ApplicationException.class, () -> {

            applicationService.transferMoney(new TransactionDTO("2223", "4687", "5"));

        });
        Assertions.assertTrue(exception.getMessage().contains("The source account doesn't exist"));
    }

}
