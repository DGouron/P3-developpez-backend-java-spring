package com.oc.chatop.services;

import com.oc.chatop.entities.Message;
import com.oc.chatop.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        
        return messageRepository.save(message);
    }
}