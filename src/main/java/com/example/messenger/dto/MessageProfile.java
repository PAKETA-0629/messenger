package com.example.messenger.dto;

import com.example.messenger.util.MessageStatus;
import com.example.messenger.util.MessageType;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class MessageProfile {

    private long id;
    private long conversation;
    private ParticipantProfile sender;
    private MessageType messageType;
    private MessageStatus messageStatus;
    private String text;
    private Timestamp createAt;

}
