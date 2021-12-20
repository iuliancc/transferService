package com.example.transferservice.cntrl;

import com.example.transferservice.ApplicationService;
import com.example.transferservice.dtos.TransactionDTO;
import com.example.transferservice.infrasctructure.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/transactions")
@RestController
public class MainCntrl {


    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/transferMoney")
    public void transferMoney(@RequestBody TransactionDTO transactionDTO) {
        try {
            applicationService.transferMoney(transactionDTO);
        } catch (ApplicationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
