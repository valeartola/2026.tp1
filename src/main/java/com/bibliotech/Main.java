package com.bibliotech;

import com.bibliotech.exception.*;
import com.bibliotech.model.*;
import com.bibliotech.repository.*;
import com.bibliotech.service.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private final RecursoService recursoService;
    private final SocioService socioService;
    private final PrestamoService prestamoService;
    private final Scanner scanner;

    public Main(RecursoService recursoService, SocioService socioService, PrestamoService prestamoService) {
        this.recursoService = recursoService;
        this.socioService = socioService;
        this.prestamoService = prestamoService;
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        RecursoRepository recursoRepo = new RecursoRepositoryImpl();
        SocioRepository socioRepo = new SocioRepositoryImpl();
        PrestamoRepository prestamoRepo = new PrestamoRepositoryImpl();

        RecursoService recursoService = new RecursoServiceImpl(recursoRepo);
        SocioService socioService = new SocioServiceImpl(socioRepo);
        PrestamoService prestamoService = new PrestamoServiceImpl(prestamoRepo);

        new Main(recursoService, socioService, prestamoService).iniciar();
    }

    public void iniciar() {
        boolean corriendo = true;
        while (corriendo) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1 -> menuRecursos();
                case 2 -> menuSocios();
                case 3 -> menuPrestamos();
                case 0 -> corriendo = false;
                default -> System.out.println("Opción no válida.");
            }
        }
        System.out.println("¡Hasta luego!");
        scanner.close();
    }

    // ── Menús ────────────────────────────────────────────────────────────────

    private void mostrarMenuPrincipal() {
        System.out.println("\n╔══════════════════════════╗");
        System.out.println("║       BIBLIOTECH          ║");
        System.out.println("╠══════════════════════════╣");
        System.out.println("║  1. Gestión de recursos   ║");
        System.out.println("║  2. Gestión de socios     ║");
        System.out.println("║  3. Gestión de préstamos  ║");
        System.out.println("║  0. Salir                 ║");
        System.out.println("╚══════════════════════════╝");
    }

    private void menuRecursos() {
        System.out.println("\n--- Recursos ---");
        System.out.println("1. Registrar libro");
        System.out.println("2. Registrar eBook");
        System.out.println("3. Listar todos");
        System.out.println("4. Buscar por ISBN");
        System.out.println("0. Volver");

        int opcion = leerEntero("Opción: ");
        switch (opcion) {
            case 1 -> registrarLibro();
            case 2 -> registrarEBook();
            case 3 -> listarRecursos();
            case 4 -> buscarRecursoPorIsbn();
            case 0 -> {}
            default -> System.out.println("Opción no válida.");
        }
    }

    private void menuSocios() {
        System.out.println("\n--- Socios ---");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Registrar docente");
        System.out.println("3. Listar todos");
        System.out.println("4. Buscar por DNI");
        System.out.println("0. Volver");

        int opcion = leerEntero("Opción: ");
        switch (opcion) {
            case 1 -> registrarEstudiante();
            case 2 -> registrarDocente();
            case 3 -> listarSocios();
            case 4 -> buscarSocioPorDni();
            case 0 -> {}
            default -> System.out.println("Opción no válida.");
        }
    }

    private void menuPrestamos() {
        System.out.println("\n--- Préstamos ---");
        System.out.println("1. Realizar préstamo");
        System.out.println("2. Registrar devolución");
        System.out.println("3. Ver historial por socio");
        System.out.println("4. Listar todos");
        System.out.println("0. Volver");

        int opcion = leerEntero("Opción: ");
        switch (opcion) {
            case 1 -> realizarPrestamo();
            case 2 -> realizarDevolucion();
            case 3 -> verHistorialSocio();
            case 4 -> listarPrestamos();
            case 0 -> {}
            default -> System.out.println("Opción no válida.");
        }
    }

    // ── Recursos ─────────────────────────────────────────────────────────────

    private void registrarLibro() {
        System.out.println("\n--- Registrar Libro ---");
        String isbn = leerTexto("ISBN: ");
        String titulo = leerTexto("Título: ");
        String autor = leerTexto("Autor: ");
        int anio = leerEntero("Año: ");
        Categoria categoria = leerCategoria();

        try {
            recursoService.registrarRecurso(new Libro(isbn, titulo, autor, anio, categoria));
            System.out.println("Libro registrado: " + titulo);
        } catch (RecursoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarEBook() {
        System.out.println("\n--- Registrar EBook ---");
        String isbn = leerTexto("ISBN: ");
        String titulo = leerTexto("Título: ");
        String autor = leerTexto("Autor: ");
        int anio = leerEntero("Año: ");
        Categoria categoria = leerCategoria();
        String formato = leerTexto("Formato digital (PDF/EPUB/MOBI): ");

        try {
            recursoService.registrarRecurso(new EBook(isbn, titulo, autor, anio, categoria, formato));
            System.out.println("EBook registrado: " + titulo);
        } catch (RecursoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarRecursos() {
        List<Recurso> recursos = recursoService.listarTodos();
        if (recursos.isEmpty()) {
            System.out.println("No hay recursos registrados.");
            return;
        }
        System.out.println("\n--- Recursos (" + recursos.size() + ") ---");
        for (Recurso r : recursos) {
            String tipo = (r instanceof EBook) ? "[EBook]" : "[Libro]";
            System.out.printf("  %s ISBN: %-12s | %-30s | %s | %d | %s%n",
                    tipo, r.isbn(), r.titulo(), r.autor(), r.anio(), r.categoria());
        }
    }

    private void buscarRecursoPorIsbn() {
        String isbn = leerTexto("ISBN: ");
        recursoService.buscarPorIsbn(isbn).ifPresentOrElse(
                r -> System.out.printf("Encontrado: %s - %s%n", r.titulo(), r.autor()),
                () -> System.out.println("No se encontró ningún recurso con ISBN: " + isbn)
        );
    }

    // ── Socios ────────────────────────────────────────────────────────────────

    private void registrarEstudiante() {
        System.out.println("\n--- Registrar Estudiante ---");
        int dni = leerEntero("DNI: ");
        String nombre = leerTexto("Nombre: ");
        String email = leerTexto("Email: ");

        try {
            socioService.registrarSocio(new Estudiante(dni, nombre, email));
            System.out.println("Estudiante registrado: " + nombre);
        } catch (SocioDuplicadoException | EmailInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SocioException e) {
            System.out.println("Error al registrar socio: " + e.getMessage());
        }
    }

    private void registrarDocente() {
        System.out.println("\n--- Registrar Docente ---");
        int dni = leerEntero("DNI: ");
        String nombre = leerTexto("Nombre: ");
        String email = leerTexto("Email: ");

        try {
            socioService.registrarSocio(new Docente(dni, nombre, email));
            System.out.println("Docente registrado: " + nombre);
        } catch (SocioDuplicadoException | EmailInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SocioException e) {
            System.out.println("Error al registrar socio: " + e.getMessage());
        }
    }

    private void listarSocios() {
        List<Socio> socios = socioService.listarTodos();
        if (socios.isEmpty()) {
            System.out.println("No hay socios registrados.");
            return;
        }
        System.out.println("\n--- Socios (" + socios.size() + ") ---");
        for (Socio s : socios) {
            String tipo = (s instanceof Docente) ? "[Docente]   " : "[Estudiante]";
            System.out.printf("  %s DNI: %-10d | %-25s | %-30s | Límite: %d%n",
                    tipo, s.getDni(), s.getNombre(), s.getEmail(), s.getLimiteLibros());
        }
    }

    private void buscarSocioPorDni() {
        int dni = leerEntero("DNI: ");
        socioService.buscarPorDni(dni).ifPresentOrElse(
                s -> System.out.printf("Encontrado: %s (%s)%n", s.getNombre(), s.getEmail()),
                () -> System.out.println("No se encontró socio con DNI: " + dni)
        );
    }

    // ── Préstamos ─────────────────────────────────────────────────────────────

    private void realizarPrestamo() {
        System.out.println("\n--- Realizar Préstamo ---");
        int dni = leerEntero("DNI del socio: ");
        String isbn = leerTexto("ISBN del recurso: ");

        Optional<Socio> socioOpt = socioService.buscarPorDni(dni);
        if (socioOpt.isEmpty()) {
            System.out.println("Socio no encontrado con DNI: " + dni);
            return;
        }

        Optional<Recurso> recursoOpt = recursoService.buscarPorIsbn(isbn);
        if (recursoOpt.isEmpty()) {
            System.out.println("Recurso no encontrado con ISBN: " + isbn);
            return;
        }

        try {
            prestamoService.realizarPrestamo(socioOpt.get(), recursoOpt.get());
            System.out.println("Préstamo registrado exitosamente.");
        } catch (RecursoNoDisponibleException | LimitePrestamosAlcanzadoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (PrestamoException e) {
            System.out.println("Error en préstamo: " + e.getMessage());
        }
    }

    private void realizarDevolucion() {
        System.out.println("\n--- Registrar Devolución ---");
        int dni = leerEntero("DNI del socio: ");
        String isbn = leerTexto("ISBN del recurso: ");

        Optional<Socio> socioOpt = socioService.buscarPorDni(dni);
        if (socioOpt.isEmpty()) {
            System.out.println("Socio no encontrado con DNI: " + dni);
            return;
        }

        Optional<Recurso> recursoOpt = recursoService.buscarPorIsbn(isbn);
        if (recursoOpt.isEmpty()) {
            System.out.println("Recurso no encontrado con ISBN: " + isbn);
            return;
        }

        try {
            prestamoService.realizarDevolucion(socioOpt.get(), recursoOpt.get());
        } catch (PrestamoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (PrestamoException e) {
            System.out.println("Error en devolución: " + e.getMessage());
        }
    }

    private void verHistorialSocio() {
        int dni = leerEntero("DNI del socio: ");

        Optional<Socio> socioOpt = socioService.buscarPorDni(dni);
        if (socioOpt.isEmpty()) {
            System.out.println("Socio no encontrado con DNI: " + dni);
            return;
        }

        List<Prestamo> historial = prestamoService.obtenerHistorialPorSocio(socioOpt.get());
        if (historial.isEmpty()) {
            System.out.println("No hay préstamos para " + socioOpt.get().getNombre() + ".");
            return;
        }

        System.out.println("\n--- Historial de " + socioOpt.get().getNombre() + " ---");
        for (Prestamo p : historial) {
            System.out.printf("  %-30s | Desde: %s | Hasta: %s | Estado: %s%n",
                    p.getRecurso().titulo(),
                    p.getFechaPrestamo(),
                    p.getFechaDevolucionEsperada(),
                    p.getEstado());
        }
    }

    private void listarPrestamos() {
        List<Prestamo> prestamos = prestamoService.listarTodos();
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos registrados.");
            return;
        }
        System.out.println("\n--- Todos los Préstamos (" + prestamos.size() + ") ---");
        for (Prestamo p : prestamos) {
            System.out.printf("  %-20s | %-30s | Desde: %s | Hasta: %s | Estado: %s%n",
                    p.getSocio().getNombre(),
                    p.getRecurso().titulo(),
                    p.getFechaPrestamo(),
                    p.getFechaDevolucionEsperada(),
                    p.getEstado());
        }
    }

    // ── Utilidades ────────────────────────────────────────────────────────────

    private String leerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingresá un número válido.");
            }
        }
    }

    private Categoria leerCategoria() {
        Categoria[] categorias = Categoria.values();
        System.out.println("Categorías:");
        for (int i = 0; i < categorias.length; i++) {
            System.out.println("  " + (i + 1) + ". " + categorias[i]);
        }
        while (true) {
            int opcion = leerEntero("Categoría (número): ");
            if (opcion >= 1 && opcion <= categorias.length) {
                return categorias[opcion - 1];
            }
            System.out.println("Opción inválida, elegí entre 1 y " + categorias.length + ".");
        }
    }
}
