package com.example.messenger.model;

import com.example.messenger.dto.ConversationProfile;
import com.example.messenger.dto.MessageProfile;
import com.example.messenger.dto.ParticipantProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "conversation")
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {

    @Id
    private Long id;
    @Column(name = "title")
    private String title;
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;
    @Column(name = "created_at")
    private Date createdAt;

    @Transient
    private List<Message> messages;
    @Transient
    private List<Participant> participants;

    public ConversationProfile convertToDto() {
        List<MessageProfile> messageProfiles = new ArrayList<>();
        List<ParticipantProfile> participantProfiles = new ArrayList<>();

        messages.forEach(msg -> {
            messageProfiles.add(msg.convertToDto());
        });

        participants.forEach(prt -> {
            participantProfiles.add(prt.convertToDto());
        });


        return ConversationProfile.builder()
                .id(this.id)
                .title(this.title)
                .messages(messageProfiles)
                .participants(participantProfiles)
                .build();

    }
}
