package com.unq.edu.li.pdesa.mentiUnq.models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Expose
    private Long id;

    @Expose
    private String question;

    @Expose
    private Boolean isCurrent;

    @OneToOne
    @JoinColumn(name = "slide_id")
    @Expose
    private Slide slide;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL})
    @Expose
    private List<MentiOption> mentiOptions;

	public void addAnOption(MentiOption mentiOption)
	{
		this.mentiOptions.add(mentiOption);
	}
}
