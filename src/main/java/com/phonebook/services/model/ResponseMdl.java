package com.phonebook.services.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMdl {
    public static String SUCCESS = "Success";
    private Integer code;
    private String message = SUCCESS;
    private Mst_Phonebook data;

}
