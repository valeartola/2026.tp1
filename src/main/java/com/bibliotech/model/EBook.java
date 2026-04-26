package com.bibliotech.model;

public record EBook(String isbn,
                    String titulo,
                    String autor,
                    int anio,
                    Categoria categoria,
                    String formato
) implements Recurso {}
