package com.bibliotech.repository;

import com.bibliotech.model.Categoria;
import com.bibliotech.model.EBook;
import com.bibliotech.model.Libro;
import com.bibliotech.model.Recurso;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecursoRepositoryImpl implements RecursoRepository {

    private final List<Recurso> recursos = new ArrayList<>();

    @Override
    public void guardar(Recurso entidad) {
        recursos.add(entidad);
    }

    @Override
    public Optional<Recurso> buscarPorId(String isbn) {
        return recursos.stream()
                .filter(r -> r.isbn().equals(isbn))
                .findFirst();
    }

    @Override
    public List<Recurso> buscarTodos() {
        return new ArrayList<>(recursos);
    }

    @Override
    public List<Recurso> buscarPorTitulo(String titulo) {
        return recursos.stream()
                .filter(r -> r.titulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    @Override
    public List<Recurso> buscarPorAutor(String autor) {
        return recursos.stream()
                .filter(r -> {
                    if (r instanceof Libro l) return l.autor().toLowerCase().contains(autor.toLowerCase());
                    if (r instanceof EBook e) return e.autor().toLowerCase().contains(autor.toLowerCase());
                    return false;
                })
                .toList();
    }

    @Override
    public List<Recurso> buscarPorCategoria(Categoria categoria) {
        return recursos.stream()
                .filter(r -> r.categoria().equals(categoria))
                .toList();
    }
}