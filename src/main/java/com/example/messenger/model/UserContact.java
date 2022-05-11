package com.example.messenger.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_contacts")
@AllArgsConstructor
@NoArgsConstructor
public class UserContact {


    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;
    @Column(name = "phone")
    private String phone;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
}
