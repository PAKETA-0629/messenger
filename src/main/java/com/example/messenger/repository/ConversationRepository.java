package com.example.messenger.repository;

import com.example.messenger.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query(nativeQuery = true, value =
            "SELECT * FROM (SELECT * FROM conversation\n" +
            "JOIN participants ON conversation.id = participants.conversation_id\n" +
            "WHERE users_id = :id) as conv")
    List<Conversation> findConversationsByUserId(@Param("id") Long id);

    Conversation findConversationById(Long id);
}
