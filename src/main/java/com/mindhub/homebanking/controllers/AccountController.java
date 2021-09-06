package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }


    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById(id).map(account -> new AccountDTO(account)).orElse(null);
    }


    public int getRandomNumber(int min, int max ) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<?> createAccount(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());

        if (client.getAccounts().size() >= 3){
            return new ResponseEntity<>("El cliente ya tiene 3 cuentas",HttpStatus.FORBIDDEN);
        }


         accountRepository.save(new Account( "VIN-"+getRandomNumber( 1,  99999999), LocalDateTime.now(),0.0,client));
        return new ResponseEntity<>("Cuenta creada correctamente",HttpStatus.CREATED);
    }


}
