package com.bibliotech.service;

import com.bibliotech.model.EstadoPrestamo;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Recurso;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.PrestamoRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private static final int DIAS_PRESTAMO = 7;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public void realizarPrestamo(Socio socio, Recurso recurso) {
        // Validar disponibilidad del recurso
        boolean estaDisponible = prestamoRepository.buscarPorRecurso(recurso)
                .stream()
                .noneMatch(p -> p.getEstado() == EstadoPrestamo.ACTIVO);

        if (!estaDisponible) {
            throw new RuntimeException("El recurso no está disponible: " + recurso.titulo());
        }

        // Validar límite de préstamos del socio
        long prestamosActivos = prestamoRepository.buscarPorSocio(socio)
                .stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.ACTIVO)
                .count();

        if (prestamosActivos >= socio.getLimiteLibros()) {
            throw new RuntimeException("El socio alcanzó su límite de préstamos: " + socio.getLimiteLibros());
        }

        // Registrar el préstamo
        Prestamo prestamo = new Prestamo(
                socio,
                recurso,
                LocalDate.now(),
                LocalDate.now().plusDays(DIAS_PRESTAMO)
        );
        prestamoRepository.guardar(prestamo);
    }

    @Override
    public void realizarDevolucion(Socio socio, Recurso recurso) {
        // Buscar el préstamo activo
        Prestamo prestamo = prestamoRepository.buscarPorRecurso(recurso)
                .stream()
                .filter(p -> p.getEstado() == EstadoPrestamo.ACTIVO)
                .filter(p -> p.getSocio().getDni() == socio.getDni())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró un préstamo activo para este socio y recurso"));

        // Calcular días de retraso
        LocalDate hoy = LocalDate.now();
        long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaDevolucionEsperada(), hoy);

        if (diasRetraso > 0) {
            System.out.println("Devolución con retraso de " + diasRetraso + " días.");
        } else {
            System.out.println("Devolución a tiempo!");
        }

        // Actualizar estado
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
    }

    @Override
    public List<Prestamo> obtenerHistorialPorSocio(Socio socio) {
        return prestamoRepository.buscarPorSocio(socio);
    }

    @Override
    public List<Prestamo> listarTodos() {
        return prestamoRepository.buscarTodos();
    }
}