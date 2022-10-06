package com.unq.edu.li.pdesa.mentiUnq.models;

import com.google.gson.annotations.Expose;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Expose
    private String name;
    @Expose
    private LocalDateTime creationDate;
    @Expose
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "form", fetch = FetchType.EAGER)
    @Expose
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "menti_user_id")
    private MentiUser mentiUser;

}
