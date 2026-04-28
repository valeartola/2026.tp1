package com.bibliotech.exception;

public class RecursoNoDisponibleException extends RecursoException {

    public RecursoNoDisponibleException(String titulo) {
        super("El recurso no está disponible: " + titulo);
    }
}
