package com.unq.edu.li.pdesa.mentiUnq.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "menti_user")
@NoArgsConstructor
public class MentiUser extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private LoginProvider provider;
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private Boolean enabled;
    @Getter
    @Setter
    private String password;
}
