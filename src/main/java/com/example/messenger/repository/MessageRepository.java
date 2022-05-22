package com.example.messenger.repository;

import com.example.messenger.model.Conversation;
import com.example.messenger.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {


    List<Message> findMessageByConversation(Long conversationId);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
            "INSERT INTO messages(conversation_id, sender_id, message_type, message, created_at, message_status) " +
            "VALUES (:conversationId, :senderId, :messageType, :text, :createdAt, :status) ")
    void saveMessage(@Param("conversationId") Long conversationId, @Param("senderId") Long senderId,
                     @Param("messageType") String messageType, @Param("text") String text, @Param("createdAt") Timestamp createdAt, @Param("status") String status);

    @Query(nativeQuery = true, value = "SELECT * FROM messages " +
            "WHERE conversation_id = :conversationId " +
            "AND sender_id = :senderId " +
            "AND message_type = :messageType " +
            "AND message = :text " +
            "AND created_at = :createdAt " +
            "AND message_status = :status")
    Optional<Message> findMessage(
            @Param("conversationId") Long conversationId, @Param("senderId") Long senderId,
            @Param("messageType") String messageType, @Param("text") String text,
            @Param("createdAt") Timestamp createdAt, @Param("status") String status);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
                    "UPDATE messages " +
                    "SET message = :text, created_at = :createdAt, message_status = :status " +
                    "WHERE message = :text AND created_at = :createdAt")
    void updateMessage(@Param("text") String text, @Param("createdAt") Timestamp createdAt, @Param("status") String status);

    @Modifying
    @Transactional
    void deleteMessageByTextAndCreateAtAndConversation(String text, Timestamp createdAt, Long conversationId);
}
