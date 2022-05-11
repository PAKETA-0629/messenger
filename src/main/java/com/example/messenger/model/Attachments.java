package com.example.messenger.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "attachments")
@AllArgsConstructor
@NoArgsConstructor
public class Attachments {


    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    private Message message;
    @Column(name = "file_url")
    private String url;

}
