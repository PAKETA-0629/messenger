package com.example.messenger.service;

import com.example.messenger.dto.ConversationProfile;
import com.example.messenger.model.Conversation;
import com.example.messenger.repository.ConversationRepository;
import com.example.messenger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationServiceImpl {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final MessageServiceImpl messageService;
    private final ParticipantServiceImpl participantService;

    public ConversationServiceImpl(ConversationRepository conversationRepository, UserRepository userRepository, MessageServiceImpl messageService, ParticipantServiceImpl participantService) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.messageService = messageService;
        this.participantService = participantService;
    }

    public List<ConversationProfile> pullConversations(String nickname) {

        List<Conversation> conversations = conversationRepository.findConversationsByUserId(userRepository.findByNickName(nickname).get().getId());
        for (Conversation conversation : conversations) {
            conversation.setMessages(messageService.pullMessageByConversation(conversation));
            conversation.setParticipants(participantService.pullParticipantByConversation(conversation));
        }

        List<ConversationProfile> conversationProfiles = new ArrayList<>();

        conversations.forEach(conv -> {
            conversationProfiles.add(conv.convertToDto());
        });

        return conversationProfiles;
    }

    public Conversation findById(Long id) {
        return conversationRepository.findConversationById(id);
    }
}
