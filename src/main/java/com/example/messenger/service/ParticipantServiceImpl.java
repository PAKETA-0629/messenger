package com.example.messenger.service;

import com.example.messenger.model.Conversation;
import com.example.messenger.model.Participant;
import com.example.messenger.repository.ConversationRepository;
import com.example.messenger.repository.ParticipantsRepository;
import com.example.messenger.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantServiceImpl {

    private final ParticipantsRepository participantsRepository;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    public ParticipantServiceImpl(ParticipantsRepository participantsRepository, UserRepository userRepository, ConversationRepository conversationRepository) {
        this.participantsRepository = participantsRepository;
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
    }

    public List<Participant> pullParticipantByConversation(Conversation conversation) {
        return participantsRepository.findParticipantByConversation(conversation);
    }

    public Participant getParticipantByNickname(String nickname, Long conversationId) {
        Optional<Participant> participant = participantsRepository.findParticipantByUserAndConversation(
                userRepository.findByNickName(nickname).orElse(null), conversationRepository.findById(conversationId).orElse(null));
        return participant.orElse(null);
    }

    public boolean validate(String nickname, Long conversationId) {
        return getParticipantByNickname(nickname, conversationId) != null;
    }
}
