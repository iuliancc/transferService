package com.example.transferservice.repository;



import com.example.transferservice.domain.entities.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;

public interface AccountRepository extends MongoRepository<Account, String>,AccountRepositoryCustom {

    Account getAccountById(String accountId);
    void updateBalanceById(String id, BigDecimal balance);

}




