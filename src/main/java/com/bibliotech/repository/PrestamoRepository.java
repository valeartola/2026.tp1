package com.bibliotech.repository;

import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Recurso;
import com.bibliotech.model.Socio;
import java.util.List;

public interface PrestamoRepository extends Repository<Prestamo, Integer> {
    List<Prestamo> buscarPorSocio(Socio socio);
    List<Prestamo> buscarPorRecurso(Recurso recurso);
}