package jm.controller.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jm.ThreadChannelMessageService;
import jm.ThreadChannelService;
import jm.dto.*;
import jm.model.Message;
import jm.model.ThreadChannel;
import jm.model.message.ThreadChannelMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rest/api/threads")
@Tag(name = "thread", description = "Thread Channel API")
public class ThreadChannelRestController {

    private static final Logger logger = LoggerFactory.getLogger(ThreadChannelRestController.class);

    private ThreadChannelService threadChannelService;
    private ThreadChannelMessageService threadChannelMessageService;
    private ThreadMessageDtoService threadMessageDtoService;
    private ThreadDtoService threadDtoService;

    @Autowired
    public void setThreadDtoService(ThreadDtoService threadDtoService) {
        this.threadDtoService = threadDtoService;
    }

    @Autowired
    public void setThreadMessageDtoService(ThreadMessageDtoService threadMessageDtoService) {
        this.threadMessageDtoService = threadMessageDtoService;
    }

    @Autowired
    public void setThreadService(ThreadChannelService threadChannelService) {
        this.threadChannelService = threadChannelService;
    }

    @Autowired
    public void setThreadChannelMessageService(ThreadChannelMessageService threadChannelMessageService) {
        this.threadChannelMessageService = threadChannelMessageService;
    }

    @PostMapping("/create")
    public ResponseEntity<ThreadChannel> createThreadChannel(@RequestBody Message message) {
        ThreadChannel threadChannel = new ThreadChannel(message);
        threadChannelService.createThreadChannel(threadChannel);
        logger.info("Созданный тред : {}", threadChannel);
        return new ResponseEntity<>(threadChannel, HttpStatus.CREATED);
    }

    @PostMapping("/messages/create")
    public ResponseEntity<ThreadMessageDTO> createThreadChannelMessage(@RequestBody ThreadMessageDTO threadMessageDTO) {
        System.out.println("CREATE!!! - " + threadMessageDTO);
        ThreadChannelMessage threadChannelMessage = threadMessageDtoService.toEntity(threadMessageDTO);
        threadChannelMessageService.createThreadChannelMessage(threadChannelMessage);
        return new ResponseEntity<>(threadMessageDtoService.toDto(threadChannelMessage), HttpStatus.CREATED);
    }

    @GetMapping("/{message_id}")
    public ResponseEntity<ThreadDTO> findThreadChannelByChannelMessageId(@PathVariable("message_id") Long id) {
        ThreadChannel temp = threadChannelService.findByChannelMessageId(id);
        System.out.println("GET-THREADCHANNEL - " + temp);
        return new ResponseEntity<>(threadDtoService.toDto(temp), HttpStatus.OK);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<List<ThreadMessageDTO>> findAllThreadChannelMessagesByThreadChannelId(@PathVariable Long id) {
        List<ThreadChannelMessage> list = threadChannelMessageService.findAllThreadChannelMessagesByThreadChannelId(id);
        System.out.println("LIST - " + list.toString());
        return new ResponseEntity<>(threadMessageDtoService.toDto(list), HttpStatus.OK);
    }
}