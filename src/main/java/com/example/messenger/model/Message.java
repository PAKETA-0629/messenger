package com.example.messenger.model;

import com.example.messenger.dto.MessageProfile;
import com.example.messenger.util.MessageStatus;
import com.example.messenger.util.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    private Long id;
    @Column(name = "conversation_id")
    private Long conversation;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private Participant sender;
    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    @Column(name = "message_status")
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;
    @Column(name = "message")
    private String text;
    @Column(name = "created_at")
    private Timestamp createAt;

    public MessageProfile convertToDto() {
        return MessageProfile.builder()
                .id(this.id)
                .conversation(this.conversation)
                .sender(this.sender.convertToDto())
                .messageType(this.messageType)
                .messageStatus(this.messageStatus)
                .text(this.text)
                .createAt(this.createAt)
                .build();
    }

}
