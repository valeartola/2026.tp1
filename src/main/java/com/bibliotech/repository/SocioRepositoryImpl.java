package com.bibliotech.repository;

import com.bibliotech.model.Socio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SocioRepositoryImpl implements SocioRepository {

    private final List<Socio> socios = new ArrayList<>();

    @Override
    public void guardar(Socio entidad) {
        socios.add(entidad);
    }

    @Override
    public Optional<Socio> buscarPorId(Integer dni) {
        return buscarPorDni(dni);
    }

    @Override
    public List<Socio> buscarTodos() {
        return new ArrayList<>(socios);
    }

    @Override
    public Optional<Socio> buscarPorDni(int dni) {
        return socios.stream()
                .filter(s -> s.getDni() == dni)
                .findFirst();
    }

    @Override
    public Optional<Socio> buscarPorEmail(String email) {
        return socios.stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}