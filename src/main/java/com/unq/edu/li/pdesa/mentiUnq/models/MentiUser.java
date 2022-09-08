package com.unq.edu.li.pdesa.mentiUnq.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "menti_user")
@NoArgsConstructor
public class MentiUser extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
