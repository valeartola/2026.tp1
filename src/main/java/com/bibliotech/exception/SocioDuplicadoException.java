package com.bibliotech.exception;

public class SocioDuplicadoException extends SocioException {

    public SocioDuplicadoException(int dni) {
        super("Ya existe un socio con el DNI: " + dni);
    }
}
