package com.unq.edu.li.pdesa.mentiUnq.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "form")
@NoArgsConstructor
public class Form extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;
    private String codeShare;

    @OneToMany(mappedBy = "form", fetch = FetchType.EAGER)
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "menti_user_id")
    private MentiUser mentiUser;

    public Form(String code, String codeShare) {
        this.code = code;
        this.codeShare = codeShare;
    }
}
