package com.bibliotech.service;

import com.bibliotech.model.Socio;
import java.util.List;
import java.util.Optional;

public interface SocioService {
    void registrarSocio(Socio socio);
    Optional<Socio> buscarPorDni(int dni);
    Optional<Socio> buscarPorEmail(String email);
    List<Socio> listarTodos();
}