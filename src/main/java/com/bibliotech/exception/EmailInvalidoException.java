package com.bibliotech.exception;

public class EmailInvalidoException extends SocioException {

    public EmailInvalidoException(String email) {
        super("El email no tiene un formato válido: " + email);
    }
}
