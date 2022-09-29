package com.unq.edu.li.pdesa.mentiUnq.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Entity
@Table(name = "form")
@NoArgsConstructor
@AllArgsConstructor
public class Form extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;
    private String codeShare;

    @OneToMany(mappedBy = "form", fetch = FetchType.EAGER)
    private List<Question> questions;
}
