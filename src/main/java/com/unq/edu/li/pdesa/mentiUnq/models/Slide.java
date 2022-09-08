package com.unq.edu.li.pdesa.mentiUnq.models;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "slide")
public class Slide extends BaseModel {

    @Id
    private Long id;
    private String nombre;

    public Slide() {
    }
    public Slide(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
