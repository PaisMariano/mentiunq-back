package com.unq.edu.li.pdesa.mentiUnq.models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "slide")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Slide extends BaseModel {

    @Id
    @Expose
    private Long id;
    @Expose
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "slide_type_id")
    @Expose
    private SlideType slideType;

    public Slide(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
