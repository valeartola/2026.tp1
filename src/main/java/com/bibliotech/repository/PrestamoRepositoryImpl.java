package com.bibliotech.repository;

import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Recurso;
import com.bibliotech.model.Socio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrestamoRepositoryImpl implements PrestamoRepository {

    private final List<Prestamo> prestamos = new ArrayList<>();
    private int contadorId = 0;

    @Override
    public void guardar(Prestamo entidad) {
        prestamos.add(entidad);
    }

    @Override
    public Optional<Prestamo> buscarPorId(Integer id) {
        return prestamos.stream()
                .filter(p -> prestamos.indexOf(p) == id)
                .findFirst();
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return new ArrayList<>(prestamos);
    }

    @Override
    public List<Prestamo> buscarPorSocio(Socio socio) {
        return prestamos.stream()
                .filter(p -> p.getSocio().getDni() == socio.getDni())
                .toList();
    }

    @Override
    public List<Prestamo> buscarPorRecurso(Recurso recurso) {
        return prestamos.stream()
                .filter(p -> p.getRecurso().isbn().equals(recurso.isbn()))
                .toList();
    }
}