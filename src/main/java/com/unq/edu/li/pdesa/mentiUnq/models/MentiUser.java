package com.unq.edu.li.pdesa.mentiUnq.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "menti_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MentiUser extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "form_fk", referencedColumnName = "id")
    private Form form;

    private String userName;
}
