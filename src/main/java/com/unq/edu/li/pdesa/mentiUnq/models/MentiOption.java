package com.unq.edu.li.pdesa.mentiUnq.models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menti_option")
public class MentiOption extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private Integer score;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "question_id")
    private Question question;
}
