package com.unq.edu.li.pdesa.mentiUnq.models;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "slide")
@Getter
@Setter
@NoArgsConstructor
public class Slide extends BaseModel {

    @Id
    @Expose
    private Long id;
    @Expose
    private String nombre;

    public Slide(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
