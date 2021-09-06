package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

@SpringBootTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class RepositoriesTest {



    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CardRepository cardRepository;



    @Test

    public void existLoans(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));

    }



    @Test

    public void existPersonalLoan(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

    }

    @Test

    public void existAccount(){
        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts,is(not(empty())));
    }

    @Test

    public void existIdAccount(){
        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts, hasItem(hasProperty("id", is(not(empty())))));

    }

    @Test
    public void existClient(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    public void existIdClient(){
        List<Client> clients = clientRepository.findAll();

        assertThat(clients,hasItem(hasProperty("id",is(not(empty())))));
    }

    @Test

    public void existCard(){
        List<Card> cards = cardRepository.findAll();

        assertThat(cards,is(not(empty())));
    }
    @Test
    public void existIdCard(){
        List<Card> cards = cardRepository.findAll();

        assertThat(cards,hasItem(hasProperty("id", is(not(empty())))));
    }

    @Test
    public void existTransaction(){
        List<Transaction>  transactions = transactionRepository.findAll();

        assertThat(transactions,is(not(empty())));
    }

    @Test
    public void existIdTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();

        assertThat(transactions, hasItem(hasProperty("id",is(not(empty())))));
    }

}