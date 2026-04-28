package com.bibliotech.model;

public class Docente extends Socio {

    public Docente(int dni, String nombre, String email) {
        super(dni, nombre, email);
    }

    @Override
    public int getLimiteLibros() {
        return 5;
    }
}
