package com.bibliotech.model;

import java.time.LocalDate;

public class Prestamo {
    private final Socio socio;
    private final Recurso recurso;
    private final LocalDate fechaPrestamo;
    private final LocalDate fechaDevolucionEsperada;
    private EstadoPrestamo estado;

    public Prestamo(Socio socio, Recurso recurso, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada) {
        this.socio = socio;
        this.recurso = recurso;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.estado = EstadoPrestamo.ACTIVO; // siempre empieza ACTIVO
    }

    public Socio getSocio() { return socio; }
    public Recurso getRecurso() { return recurso; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucionEsperada() { return fechaDevolucionEsperada; }
    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) { this.estado = estado; }
}