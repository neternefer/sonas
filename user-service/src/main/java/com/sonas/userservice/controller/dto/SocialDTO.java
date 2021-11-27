package com.sonas.userservice.controller.dto;

import com.sonas.userservice.dao.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialDTO {

    private String linkType;

    private String link;

    private long contactId;
}

