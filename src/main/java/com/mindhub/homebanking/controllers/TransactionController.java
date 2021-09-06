package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

@Transactional
@PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
                                               @RequestParam double amount, @RequestParam String description, Authentication authentication) {
    if (fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()) {
        return new ResponseEntity<>("Origin account or destination account is not provided", HttpStatus.FORBIDDEN);
    }
    if (fromAccountNumber.equals(toAccountNumber)) {
        return new ResponseEntity<>("Transfer to same account is not allowed", HttpStatus.FORBIDDEN);
    }

    if (amount == 0 || description.isEmpty()) {
        return new ResponseEntity<>("Amount or Description is not provided", HttpStatus.FORBIDDEN);
    }

    Account fromAccount = this.accountRepository.findByNumber(fromAccountNumber);

    if (fromAccount == null) {
        return new ResponseEntity<>("Origin account does not exist", HttpStatus.FORBIDDEN);
    }
    Client client = clientRepository.findByEmail(authentication.getName());

    if (client.getAccounts().stream().noneMatch(account -> account.getNumber().equals(fromAccountNumber))) {
        return new ResponseEntity<>("Origin account does not belong to the authentication client", HttpStatus.FORBIDDEN);
    }
    if (fromAccount.getBalance() < amount) {
        return new ResponseEntity<>("Insufficient founds", HttpStatus.FORBIDDEN);
    }

    Account toAccount = this.accountRepository.findByNumber(toAccountNumber);
    if (toAccount == null) {
        return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
    }

    transactionRepository.save(new Transaction(TransactionType.credito, amount , description + " " + fromAccount.getNumber(), LocalDateTime.now(), toAccount));
    transactionRepository.save(new Transaction(TransactionType.debito, amount * -1, description + " " + toAccount.getNumber(), LocalDateTime.now(), fromAccount));
    fromAccount.setBalance(fromAccount.getBalance() - amount);
    toAccount.setBalance(toAccount.getBalance() + amount);

    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);

    return new ResponseEntity<>(HttpStatus.CREATED);
}
}
