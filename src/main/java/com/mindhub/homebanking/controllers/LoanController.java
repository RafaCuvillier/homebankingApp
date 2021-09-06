package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;


    @GetMapping("/loans")
    public ResponseEntity<?> getLoans (){
        return new ResponseEntity<>(this.loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList()), HttpStatus.ACCEPTED) ;
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<?> addLoan(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                        Authentication authentication) {

       Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findByNumber(loanApplicationDTO.getNumAccount());

        Loan loan = loanRepository.getById(loanApplicationDTO.getLoanId());

        if (loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayments() == 0) {
            return new ResponseEntity<>("El monto o los pagos no pueden ser 0",HttpStatus.FORBIDDEN);
        }
        if (loan == null){
            return new ResponseEntity<>("El prestamo no existe",HttpStatus.FORBIDDEN);
        }
        if (loan.getMaxAmount() < loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("El monto solicitado excede el maximo", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Las cuotas solicitadas no se encuentran disponibles para este prestamo", HttpStatus.FORBIDDEN);
        }

        if (account == null){
            return new ResponseEntity<>("La cuenta no es valida", HttpStatus.FORBIDDEN);
        }

        if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("La cuenta de destino no pertenece al cliente",HttpStatus.FORBIDDEN);
        }
        clientLoanRepository.save(new ClientLoan(loanApplicationDTO.getAmount(), loanApplicationDTO.getPayments(), client,loan, account.getNumber() ));
        transactionRepository.save(new Transaction(TransactionType.credito,loanApplicationDTO.getAmount(),"Préstamo - " + loan.getName(), LocalDateTime.now() , account));
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        accountRepository.save(account);
        return new ResponseEntity<>("Préstamo acreditado en la cuenta", HttpStatus.ACCEPTED);


    }


}