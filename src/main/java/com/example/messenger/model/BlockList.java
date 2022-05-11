package com.example.messenger.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "block_list")
@AllArgsConstructor
@NoArgsConstructor
public class BlockList {


    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "participant_id", referencedColumnName = "id")
    private User participant;
}
