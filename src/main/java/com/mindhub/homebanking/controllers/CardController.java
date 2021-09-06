package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class CardController {

@Autowired
    ClientRepository clientRepository;

@Autowired
    CardRepository cardRepository;

    public int getRandomNumber(int min, int max ) {
        return (int) ((Math.random() * (max - min)) + min);
    }

@PostMapping("/clients/current/cards")
public ResponseEntity<?> createCard(@RequestParam CardColor cardColor, @RequestParam CardType cardType, Authentication authentication){
    Client client = clientRepository.findByEmail(authentication.getName());

    if (client.getCards().size() == 6){
        return new ResponseEntity<>("El cliente ya tiene 6 tarjetas", HttpStatus.FORBIDDEN);
    }
    cardRepository.save(new Card(client.getFirstName()+" " +client.getLastName(),getRandomNumber(1111,9999)+"-"+getRandomNumber(1111,9999)+"-"+getRandomNumber(1111,9999)+"-"+getRandomNumber(1111,9999),getRandomNumber(111,999),LocalDateTime.now(),LocalDateTime.now().plusYears(5),cardType,cardColor,client));
    return new ResponseEntity<>("Tarjeta creada correctamente",HttpStatus.CREATED);}

}

