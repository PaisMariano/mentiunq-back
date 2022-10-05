package com.unq.edu.li.pdesa.mentiUnq.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
    @Column(length = 1500)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "mentiUser", fetch = FetchType.EAGER)
    @Getter
    private List<Form> forms;
}
