package com.unq.edu.li.pdesa.mentiUnq.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "form")
@NoArgsConstructor
@Setter
public class Form extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Expose
    private String code;
    @Expose
    private String codeShare;

    @OneToMany(mappedBy = "form", fetch = FetchType.EAGER)
    @Expose
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "menti_user_id")
    private MentiUser mentiUser;

    public Form(String code, String codeShare) {
        this.code = code;
        this.codeShare = codeShare;
    }
}
