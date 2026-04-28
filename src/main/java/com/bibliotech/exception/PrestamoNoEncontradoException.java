package com.bibliotech.exception;

public class PrestamoNoEncontradoException extends PrestamoException {

    public PrestamoNoEncontradoException() {
        super("No se encontró un préstamo activo para este socio y recurso");
    }
}
