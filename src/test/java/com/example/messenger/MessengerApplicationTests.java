package com.example.messenger;

import com.example.messenger.model.Conversation;
import com.example.messenger.model.Message;
import com.example.messenger.model.User;
import com.example.messenger.repository.ConversationRepository;
import com.example.messenger.repository.MessageRepository;
import com.example.messenger.service.ConversationServiceImpl;
import com.example.messenger.service.MessageServiceImpl;
import com.example.messenger.util.MessageStatus;
import com.example.messenger.util.MessageType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MessengerApplicationTests {

    @Autowired
    private MessageServiceImpl messageService;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ConversationServiceImpl conversationService;



    @Test
    void test() {

    }

}
