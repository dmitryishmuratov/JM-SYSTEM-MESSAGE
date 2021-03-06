package jm.controller.rest;

import jm.CommandsBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jmsm/api")
public class CustomBotRestController {

    @Autowired
    private CommandsBotService commandsBotService;

    @PostMapping("/test.api")
    public ResponseEntity<String> testing() {
        String response = "{\"ok\": true}";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
