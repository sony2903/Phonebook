package com.phonebook.services.model;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMdlPagination {
    // @JsonIgnore
    public static String SUCCESS = "Success";

    private int code;
    private String message = SUCCESS;
    private Page<Mst_Phonebook> data;

}
