package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.Arrays.*;
import static java.util.Arrays.asList;

@SpringBootApplication
public class HomebankingApplication {



@Autowired
PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository
							, TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
		return (args) -> {

			Client client1 = new Client( "Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba"));
			Client client2 = new Client("Rafael", "Cuvillier", "rafael@gmail.com",passwordEncoder.encode("rafael"));
			clientRepository.save(client1);
			clientRepository.save(client2);

			Account account1= new Account("VIN-001", LocalDateTime.now(), 5000.00, client1);

		 	Account account2 = new Account("VIN-002", LocalDateTime.now().plusDays(1), 7500.00, client1);

			Account account3 = new Account("VIN-003", LocalDateTime.now(), 10000.00, client2);

		 	accountRepository.save(account1);
		 	accountRepository.save(account2);
		 	accountRepository.save(account3);

			Transaction transaction1 = new Transaction(TransactionType.debito, -5000, "Debito de ...", LocalDateTime.now(), account1);
			Transaction transaction2 = new Transaction(TransactionType.credito, 5000, "Credito de ...", LocalDateTime.now(), account2);
			Transaction transaction3 = new Transaction(TransactionType.credito, 9000, "Credito de ...", LocalDateTime.now(), account1);
			Transaction transaction4 = new Transaction(TransactionType.credito, 4000, "Credito de ...", LocalDateTime.now(), account1);
			Transaction transaction5 = new Transaction(TransactionType.debito, -7000, "Debito de ...", LocalDateTime.now(), account1);



			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);


			Loan loan1 = new Loan("Hipotecario", 500000,  Arrays.asList(12,24,36,48,60),20);
			Loan loan2 = new Loan("Personal", 100000,  Arrays.asList(6,12,24),20);
			Loan loan3 = new Loan("Automotriz", 300000,  Arrays.asList(6,12,24,36),20);

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000,60,client1 ,loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000,12,client1 ,loan2);

			ClientLoan clientLoan3 = new ClientLoan(100000,24,client2 ,loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000,36,client2 ,loan3);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);


			Card card1 = new Card(client1.getFirstName()+ " " + client1.getLastName(),"4162-2245-2451-7523",754, LocalDateTime.now(), LocalDateTime.now().plusYears(5), CardType.DEBIT, CardColor.GOLD, client1);
			Card card2 = new Card(client1.getFirstName()+ " " + client1.getLastName(), "4162-3764-2125-0142", 566, LocalDateTime.now(), LocalDateTime.now().plusYears(5), CardType.CREDIT, CardColor.PLATINUM, client1);
			Card card3 = new Card(client2.getFirstName()+ " " + client2.getLastName(), "4162-7123-1256-0952", 579, LocalDateTime.now(), LocalDateTime.now().plusYears(5), CardType.CREDIT, CardColor.GOLD, client2);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);



		};
	}
}
