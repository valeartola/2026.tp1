package com.bibliotech.model;

public class Estudiante extends Socio {

    public Estudiante(int dni, String nombre, String email) {
        super(dni, nombre, email);
    }

    @Override
    public int getLimiteLibros() {
        return 3;
    }
}