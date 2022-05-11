package com.example.messenger.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ConversationProfile {

    private long id;
    private String title;
    private List<MessageProfile> messages;
    private List<ParticipantProfile> participants;
}
