package jm.controller;

import jm.ChannelService;
import jm.dto.SlashCommandDto;
import jm.model.Channel;
import jm.model.SlashCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/app/bot/slackbot")
public class SlackBotController {
    private Logger logger = LoggerFactory.getLogger(SlackBotController.class);
    @Autowired
    private ChannelService channelService;
    @PostMapping
    public ResponseEntity<?> getCommand(@RequestBody SlashCommandDto command) {
        if (command.getCommand().startsWith("/topic")) {
            setTopic(command.getChannel_id(), command.getCommand().substring(7));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    private void setTopic(Long id, String topic) {
        Channel channel = channelService.getChannelById(id);
        channel.setTopic(topic);
        channelService.updateChannel(channel);
    }
}
