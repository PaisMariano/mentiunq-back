package com.unq.edu.li.pdesa.mentiUnq.models;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "form")
@NoArgsConstructor
@Getter
@Setter
public class Form extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Expose
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
    @Expose
    private Boolean ended;

    @OneToMany(mappedBy = "form", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST , CascadeType.REMOVE, CascadeType.MERGE })
    @Expose
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "menti_user_id")
    private MentiUser mentiUser;

}
