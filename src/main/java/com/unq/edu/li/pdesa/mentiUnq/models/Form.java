package com.unq.edu.li.pdesa.mentiUnq.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "form")
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

}
