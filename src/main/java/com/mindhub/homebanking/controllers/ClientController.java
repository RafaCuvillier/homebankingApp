package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;


    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {

        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());

    }

    @RequestMapping("/clients/current")
    public ResponseEntity<?> getUser(Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());
        if (client != null){
            return new ResponseEntity<>(new ClientDTO(client),HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Error al autenticar", HttpStatus.FORBIDDEN);
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
    return clientRepository.findById(id).map(client -> new ClientDTO(client)).orElse(null);
    }

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public int getRandomNumber(int min, int max ) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    @PostMapping("/clients")
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String name, @RequestParam String pwd) {



        if ( firstName.isEmpty() || lastName.isEmpty() || name.isEmpty() || pwd.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }



        if (clientRepository.findByEmail(name) !=  null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }



        Client client = clientRepository.save(new Client(firstName, lastName, name, passwordEncoder.encode(pwd)));



        accountRepository.save(new Account( "VIN-"+getRandomNumber( 1,  99999999), LocalDateTime.now(),0.0,client));

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


}