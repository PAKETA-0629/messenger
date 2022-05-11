package com.example.messenger.repository;

import com.example.messenger.model.Conversation;
import com.example.messenger.model.Participant;
import com.example.messenger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantsRepository extends JpaRepository<Participant, Long> {

    List<Participant> findParticipantByConversation(Conversation conversation);

    Optional<Participant> findParticipantByUserAndConversation(User user, Conversation conversation);
}
