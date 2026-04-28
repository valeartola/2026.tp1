package com.bibliotech.service;

import com.bibliotech.model.Socio;
import com.bibliotech.repository.SocioRepository;
import java.util.List;
import java.util.Optional;

public class SocioServiceImpl implements SocioService {

    private final SocioRepository socioRepository;

    public SocioServiceImpl(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    @Override
    public void registrarSocio(Socio socio) {
        // Validar DNI único
        if (socioRepository.buscarPorDni(socio.getDni()).isPresent()) {
            throw new RuntimeException("Ya existe un socio con el DNI: " + socio.getDni());
        }
        // Validar formato de email
        if (!validarEmail(socio.getEmail())) {
            throw new RuntimeException("El email no tiene un formato válido: " + socio.getEmail());
        }
        socioRepository.guardar(socio);
    }

    @Override
    public Optional<Socio> buscarPorDni(int dni) {
        return socioRepository.buscarPorDni(dni);
    }

    @Override
    public Optional<Socio> buscarPorEmail(String email) {
        if (email == null || email.isBlank()) return Optional.empty();
        return socioRepository.buscarPorEmail(email);
    }

    @Override
    public List<Socio> listarTodos() {
        return socioRepository.buscarTodos();
    }

    private boolean validarEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return email.contains("@") && email.contains(".");
    }
}