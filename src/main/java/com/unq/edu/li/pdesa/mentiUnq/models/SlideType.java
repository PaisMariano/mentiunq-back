package com.unq.edu.li.pdesa.mentiUnq.models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "slide_type")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SlideType extends BaseModel {

    @Expose
    @Id
    private Long id;

    @Expose
    private String name;
}
