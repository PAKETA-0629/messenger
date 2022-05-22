package com.example.messenger.service;

import com.example.messenger.dto.MessageProfile;
import com.example.messenger.model.Conversation;
import com.example.messenger.model.Message;
import com.example.messenger.repository.MessageRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class MessageServiceImpl {

    private final MessageRepository messageRepository;
    private final ParticipantServiceImpl participantService;

    public MessageServiceImpl(MessageRepository messageRepository, ParticipantServiceImpl participantService) {
        this.messageRepository = messageRepository;
        this.participantService = participantService;
    }

    public List<Message> pullMessageByConversation(Conversation conversation) {
        return messageRepository.findMessageByConversation(conversation.getId());
    }

    public void saveMessage(Message message) {
        messageRepository.saveMessage(
                message.getConversation(), message.getSender().getId(), message.getMessageType().toString(),
                message.getText(), message.getCreateAt(), message.getMessageStatus().toString());
    }

    public void updateMessage(Message message) {
        messageRepository.updateMessage(message.getText(), message.getCreateAt(), message.getMessageStatus().toString());
    }

    public void deleteMessage(Message message) {
        messageRepository.deleteMessageByTextAndCreateAtAndConversation(message.getText(), message.getCreateAt(), message.getConversation());
    }


    public Message findMessage(Message message) {
        return messageRepository.findMessage(message.getConversation(), message.getSender().getId(), message.getMessageType().toString(),
                message.getText(), message.getCreateAt(), message.getMessageStatus().toString()).orElse(null);
    }

    @SneakyThrows
    public Message convertDtoToMessage(MessageProfile profile, String nickname) {

        if (participantService.validate(nickname, profile.getConversation())) {
            return Message.builder()
                    .id(profile.getId())
                    .text(profile.getText())
                    .messageType(profile.getMessageType())
                    .messageStatus(profile.getMessageStatus())
                    .conversation(profile.getConversation())
                    .sender(participantService.getParticipantByNickname(nickname, profile.getConversation()))
                    .createAt(profile.getCreateAt())
                    .build();
        } else {
            throw new Exception();
        }
    }
}
