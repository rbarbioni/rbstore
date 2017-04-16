package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by renan on 26/03/2017.
 */
public class ResponseError implements Serializable{

    private static final long serialVersionUID = -4252006764524660205L;

    private final long timestamp;

    private final Integer status;

    private final String error;

    private final String message;

    private final String exception;

    private final String path;


    @JsonCreator
    public ResponseError(
            @JsonProperty("status") Integer status,
            @JsonProperty("exception") String exception,
            @JsonProperty("error") String errror,
            @JsonProperty("message") String message,
            @JsonProperty("path") String path) {
        this.timestamp = new Date().getTime();
        this.status = status;
        this.exception = exception;
        this.message = message;
        this.error = errror;
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public String getException() {
        return exception;
    }
}
