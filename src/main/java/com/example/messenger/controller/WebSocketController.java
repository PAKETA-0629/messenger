package com.example.messenger.controller;

import com.example.messenger.dto.ConversationProfile;
import com.example.messenger.dto.MessageProfile;
import com.example.messenger.model.Message;
import com.example.messenger.model.Participant;
import com.example.messenger.model.User;
import com.example.messenger.service.ConversationServiceImpl;
import com.example.messenger.service.MessageServiceImpl;
import com.example.messenger.service.ParticipantServiceImpl;
import com.example.messenger.service.UserServiceImpl;
import com.example.messenger.util.MessageStatus;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.*;

@Slf4j
@Controller
public class WebSocketController {


    private final UserServiceImpl userService;
    private final MessageServiceImpl messageService;
    private final ConversationServiceImpl conversationService;
    private final ParticipantServiceImpl participantService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebSocketController(UserServiceImpl userService, MessageServiceImpl messageService, SimpMessagingTemplate simpMessagingTemplate, ConversationServiceImpl conversationService, ParticipantServiceImpl participantService) {
        this.userService = userService;
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.conversationService = conversationService;
        this.participantService = participantService;
        this.simpMessagingTemplate.setMessageConverter(new MappingJackson2MessageConverter());

    }


    @MessageMapping("/contacts/pull")
    public void pullMessages(@Payload Object obj, Principal user) {
        List<User> users = userService.pullContacts(user.getName());
        User[] userArray = users.toArray(new User[]{});
        Map<String, Object> map = new HashMap<>();
        map.put("payload-class", "user-list");
        simpMessagingTemplate.convertAndSendToUser(user.getName(),
                "/queue/contacts/pull", userArray, map);
    }


    @MessageMapping("/pull/all")
    public void pullAllData(Principal user) {

        List<ConversationProfile> conversations = conversationService.pullConversations(user.getName());
        ConversationProfile[] conversationArray = conversations.toArray(new ConversationProfile[]{});
        Map<String, Object> map = new HashMap<>();
        map.put("payload-class", "conversation-array");
        simpMessagingTemplate.convertAndSendToUser(user.getName(), "/queue/pull/all", conversationArray, map);
    }

    @MessageMapping("/user/get")
    public void getCurrentUser(Principal principal) {

        User user = userService.findByNickName(principal.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("payload-class", "user");
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/user", user, map);

    }



    @SneakyThrows
    @MessageMapping("/message/send")
    public void sendMessage(@Payload MessageProfile messageProfile, Principal user, org.springframework.messaging.Message<byte[]> msg, StompHeaderAccessor accessor) {

        Message message = messageService.convertDtoToMessage(messageProfile, user.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("payload-class", "message");
        message.setMessageStatus(MessageStatus.DELIVERED);

        messageService.saveMessage(message);
        List<User> users = userService.findAllParticipants(message.getConversation());
        for (User participant: users) {
            simpMessagingTemplate.convertAndSendToUser(participant.getNickname(), "/queue/position-updates", message.convertToDto(), map);
        }

    }
    @MessageMapping("/message/change-status")
    public void changeStatus(@Payload MessageProfile messageProfile, Principal user) {
        Map<String, Object> map = new HashMap<>();
        map.put("payload-class", "message");
        Message message = messageService.convertDtoToMessage(messageProfile, messageProfile.getSender().getUser().getNickname());
        message.setMessageStatus(MessageStatus.READ);
        messageService.updateMessage(message);

        simpMessagingTemplate.convertAndSendToUser(message.getSender().getUser().getNickname(), "/queue/position-updates", message.convertToDto(), map);
    }

    @MessageMapping("/message/delete")
    public void deleteMessage(@Payload MessageProfile messageProfile, Principal user) {
        Map<String, Object> map = new HashMap<>();
        map.put("payload-class", "message");
        map.put("destiny", "delete");
        Message message = messageService.convertDtoToMessage(messageProfile, messageProfile.getSender().getUser().getNickname());
        messageService.deleteMessage(message);
        List<User> users = userService.findAllParticipants(message.getConversation());
        for (User participant: users) {
            simpMessagingTemplate.convertAndSendToUser(participant.getNickname(), "/queue/position-updates", message.convertToDto(), map);
        }

    }

}
