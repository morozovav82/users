package ru.morozov.users.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.morozov.messages.UserRegisteredMsg;
import ru.morozov.users.service.MessageService;

@Service
@Slf4j
public class UserProducer {

    @Autowired
    private MessageService messageService;

    @Value("${mq.UserRegistered-exchange}")
    private String userRegisteredExchange;

    public void sendUserRegisteredMessage(UserRegisteredMsg message){
        messageService.scheduleSentMessage(userRegisteredExchange, "default", message, UserRegisteredMsg.class);
    }
}