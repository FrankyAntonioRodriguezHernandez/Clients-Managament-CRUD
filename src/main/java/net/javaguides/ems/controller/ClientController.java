package net.javaguides.ems.controller;

import net.javaguides.ems.exceptions.ResourceNotFoundException;
import net.javaguides.ems.model.Client;
import net.javaguides.ems.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public List<Client> ListClients(){
        return clientRepository.findAll();
    }

    @PostMapping("/clients")
    public Client saveClient(@RequestBody Client client){
        return clientRepository.save(client);
    }


    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> ListClientById(@PathVariable Long id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no client with the ID: "+id ));
        return ResponseEntity.ok(client);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id,@RequestBody Client clientRequest){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no client with the ID: "+id ));

        client.setName(clientRequest.getName());
        client.setLast_name(clientRequest.getLast_name());
        client.setEmail(clientRequest.getEmail());

        Client actualizedClient = clientRepository.save(client);

        return ResponseEntity.ok(actualizedClient);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteClient(@PathVariable Long id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no client with the ID: "+id ));

        clientRepository.delete(client);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }

}
