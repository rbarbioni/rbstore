package br.com.rbarbioni.bluebank.model.dto;

import java.io.Serializable;

/**
 * Created by renan on 11/02/17.
 */
public class ResponseErrorDto implements Serializable {

    private String message;

    private Integer status;

    public ResponseErrorDto(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }
}
