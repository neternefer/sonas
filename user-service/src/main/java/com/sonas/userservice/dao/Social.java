package com.sonas.userservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.net.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_id")
    private long socialId;

    private String linkType;

    private URL link;

    @Column(name = "contact_id")
    private long contactId;

    public Social(String linkType, URL link, long contactId) {
        this.linkType = linkType;
        this.link = link;
        this.contactId = contactId;
    }
}
