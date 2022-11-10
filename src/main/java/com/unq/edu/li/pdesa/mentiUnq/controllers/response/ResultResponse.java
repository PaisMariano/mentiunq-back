package com.unq.edu.li.pdesa.mentiUnq.controllers.response;

import com.google.gson.annotations.Expose;
import com.unq.edu.li.pdesa.mentiUnq.models.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultResponse extends BaseModel {
    @Expose
    private Integer votes;
    @Expose
    private Integer slides;
    @Expose
    private Integer contentSlides;
    @Expose
    private Integer openSlides;
    @Expose
    private Integer closeSlides;

    public ResultResponse() {
        this.votes = 0;
        this.closeSlides = 0;
        this.openSlides = 0;
        this.contentSlides = 0;
        this.slides = 0;
    }
}
