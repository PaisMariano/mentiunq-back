package com.unq.edu.li.pdesa.mentiUnq.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "mail_white_list")
@NoArgsConstructor
public class MailingWhiteList extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public MailingWhiteList(String email) {
        this.email = email;
    }

    @Getter
    @Setter
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-+]+(\\.[A-Za-z0-9_-+]+)*@[^-][A-Za-z0-9-+]+(\\.[A-Za-z0-9-+]+)*(\\.[A-Za-z]{2,})$")
    private String email;
}
