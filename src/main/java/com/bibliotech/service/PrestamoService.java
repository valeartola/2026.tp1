package com.bibliotech.service;

import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Recurso;
import com.bibliotech.model.Socio;
import java.util.List;

public interface PrestamoService {
    void realizarPrestamo(Socio socio, Recurso recurso);
    void realizarDevolucion(Socio socio, Recurso recurso);
    List<Prestamo> obtenerHistorialPorSocio(Socio socio);
    List<Prestamo> listarTodos();
}