package com.bibliotech.service;

import com.bibliotech.model.Categoria;
import com.bibliotech.model.Recurso;
import com.bibliotech.repository.RecursoRepository;

import java.util.List;
import java.util.Optional;

public class RecursoServiceImpl implements RecursoService {

    private final RecursoRepository recursoRepository;

    // Inyección por constructor
    public RecursoServiceImpl(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    @Override
    public void registrarRecurso(Recurso recurso) {
        recursoRepository.guardar(recurso);
    }

    @Override
    public Optional<Recurso> buscarPorIsbn(String isbn) {
        return recursoRepository.buscarPorId(isbn);
    }

    @Override
    public List<Recurso> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) return List.of();
        return recursoRepository.buscarPorTitulo(titulo);
    }

    @Override
    public List<Recurso> buscarPorAutor(String autor) {
        if (autor == null || autor.isBlank()) return List.of();
        return recursoRepository.buscarPorAutor(autor);
    }

    @Override
    public List<Recurso> buscarPorCategoria(Categoria categoria) {
        if (categoria == null) return List.of();
        return recursoRepository.buscarPorCategoria(categoria);
    }

    @Override
    public List<Recurso> listarTodos() {
        return recursoRepository.buscarTodos();
    }
}
