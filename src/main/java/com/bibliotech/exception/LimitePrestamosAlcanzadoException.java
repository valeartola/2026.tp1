package com.bibliotech.exception;

public class LimitePrestamosAlcanzadoException extends PrestamoException {

    public LimitePrestamosAlcanzadoException(int limite) {
        super("El socio alcanzó su límite de préstamos: " + limite);
    }
}
