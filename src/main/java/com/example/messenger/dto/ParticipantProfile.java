package com.example.messenger.dto;

import com.example.messenger.util.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipantProfile {

    private UserProfile user;
    private Role role;
    private long conversation;
}
