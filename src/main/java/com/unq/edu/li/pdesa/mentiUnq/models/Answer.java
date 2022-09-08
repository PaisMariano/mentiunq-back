package com.unq.edu.li.pdesa.mentiUnq.models;

import javax.persistence.*;

@Entity
@Table(name = "answer")
public class Answer extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String answer;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "question_id")
    private Question question;
}
