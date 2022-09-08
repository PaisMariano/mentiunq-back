package com.unq.edu.li.pdesa.mentiUnq.models;

import javax.persistence.*;

@Entity
@Table(name = "question")
public class Question extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String question;

    @OneToOne
    @JoinColumn(name = "slide_id")
    private Slide slide;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

}
