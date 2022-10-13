package com.unq.edu.li.pdesa.mentiUnq.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menti_user")
@Getter
@Setter
@NoArgsConstructor
public class MentiUser extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoginProvider provider;
    private String userName;
    private Boolean enabled;

    @Column(length = 1500)
    private String password;

    @OneToMany(mappedBy = "mentiUser", fetch = FetchType.EAGER)
    private List<Form> forms;
}
