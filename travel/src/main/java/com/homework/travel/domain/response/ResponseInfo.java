package com.homework.travel.domain.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseInfo {

    private Integer code;

    private String status;

    private String message;

    private List<Object> result;

}
