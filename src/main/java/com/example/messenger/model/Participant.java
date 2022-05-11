package com.example.messenger.model;


import com.example.messenger.dto.ParticipantProfile;
import com.example.messenger.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "participants")
@AllArgsConstructor
@NoArgsConstructor
public class Participant {

    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    private Conversation conversation;
    @Enumerated(EnumType.STRING)
    private Role role;

    public ParticipantProfile convertToDto() {
        return ParticipantProfile.builder()
                .conversation(this.conversation.getId())
                .role(this.role)
                .user(user.convertToDto())
                .build();
    }

}
