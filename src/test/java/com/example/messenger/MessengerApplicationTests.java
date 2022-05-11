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
import java.util.List;

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
    void contextLoads() {
        Message message = new Message();
        Conversation conversation = new Conversation();
        conversation.setId(1L);
        User user = new User();
        user.setId(1L);
        message.setConversation(conversation);
        message.setSender(user);
        message.setMessageType(MessageType.TEXT);
        message.setMessageStatus(MessageStatus.DELIVERED);
        message.setText("SQL DATABASE TEST");
        message.setCreateAt(new Date(new java.util.Date().getTime()));

        messageService.pullMessageByConversation(conversation);
        messageService.saveMessage(message);
    }

    @Test
    void test() {
        Conversation conversation = new Conversation();
        conversation.setId(1L);
        List<Message> message = messageService.pullMessageByConversation(conversation);
        System.out.println(message);
    }

}
