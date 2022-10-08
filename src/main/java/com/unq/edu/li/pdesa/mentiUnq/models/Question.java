package com.unq.edu.li.pdesa.mentiUnq.models;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "question")
@Setter
@Getter
public class Question extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Expose
    private String question;

    @OneToOne
    @JoinColumn(name = "slide_id")
    private Slide slide;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

}
