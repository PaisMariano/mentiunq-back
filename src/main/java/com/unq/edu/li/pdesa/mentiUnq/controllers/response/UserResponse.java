package com.unq.edu.li.pdesa.mentiUnq.controllers.response;

import com.google.gson.annotations.Expose;
import com.unq.edu.li.pdesa.mentiUnq.models.BaseModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends BaseModel {
    @Expose
    private Long id;
    @Expose
    private String accessToken;

}
