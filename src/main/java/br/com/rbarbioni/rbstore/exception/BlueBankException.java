package br.com.rbarbioni.rbstore.exception;

/**
 * Created by renan on 11/02/17.
 */
public class BlueBankException extends RuntimeException {

    private final Integer status;

    public BlueBankException(Integer status, String message) {
        super(message);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}