/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.menu;

import com.comiccollector.control.TransaccionesComic;
import com.comiccollector.control.TransaccionesUsuario;
import com.comiccollector.modelo.Comics;
import com.comiccollector.modelo.Usuarios;
import com.comiccollector.excepciones.ExtensionExcepciones;
import com.comiccollector.excepciones.TipoError;

import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final TransaccionesComic txComic;
    private final TransaccionesUsuario txUser;

    public Menu(TransaccionesComic tc, TransaccionesUsuario tu) {
        this.txComic = tc;
        this.txUser  = tu;
    }

    public void mostrar() {
        int opcion = -1;
        do {
            System.out.println("\n==== MENÚ PRINCIPAL ====\n1. Ingreso Usuario\n2. Nuevo Usuario\n3. Libros\n4. Salir");
            System.out.print("Seleccione: ");
            try {
                opcion = Integer.parseInt(sc.nextLine());
                if (opcion < 1 || opcion > 4) {
                    throw new ExtensionExcepciones("Opción de menú no disponible", TipoError.OPCION_NO_DISPONIBLE);
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
                continue;
            } catch (ExtensionExcepciones e) {
                System.out.println(e.getMessage() + " (" + e.getTipoError() + ")");
                continue;
            }
            try {
                switch (opcion) {
                    case 1 -> ingresoUsuario();
                    case 2 -> txUser.crearUsuario();
                    case 3 -> menuLibros();
                    case 4 -> System.out.println("Hasta luego.");
                }
            } catch (ExtensionExcepciones e) {
                System.out.println(e.getMessage() + " (" + e.getTipoError() + ")");
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        } while (opcion != 4);
    }

    private void ingresoUsuario() throws ExtensionExcepciones {
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            if (nombre.trim().isEmpty()) {
                throw new ExtensionExcepciones("El nombre no puede estar vacío", TipoError.DATOS_INCOMPLETOS);
            }
            System.out.print("RUT: ");
            String rut = sc.nextLine();
            if (rut.trim().isEmpty()) {
                throw new ExtensionExcepciones("El RUT no puede estar vacío", TipoError.DATOS_INCOMPLETOS);
            }
            Usuarios u = txUser.buscarUsuario(rut, nombre);
            if (u == null) {
                throw new ExtensionExcepciones("Usuario no encontrado", TipoError.SIN_EXISTENCIAS);
            }
            menuUsuario(u);
        } catch (ExtensionExcepciones e) {
            System.out.println(e.getMessage() + " (" + e.getTipoError() + ")");
            throw e;
        } catch (Exception e) {
            System.out.println("Error en ingreso de usuario: " + e.getMessage());
        }
    }

    private void menuUsuario(Usuarios u) throws ExtensionExcepciones {
        int op = -1;
        do {
            System.out.println("\n=== MENÚ USUARIO: " + u.getNombre() + " ===\n1. Buscar Libro\n2. Ver Catálogo\n3. Anular Reserva\n4. Salir");
            System.out.print("Seleccione: ");
            try {
                op = Integer.parseInt(sc.nextLine());
                if (op < 1 || op > 4) {
                    throw new ExtensionExcepciones("Opción de menú no disponible", TipoError.OPCION_NO_DISPONIBLE);
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
                continue;
            } catch (ExtensionExcepciones e) {
                System.out.println(e.getMessage() + " (" + e.getTipoError() + ")");
                continue;
            }
            try {
                switch (op) {
                    case 1 -> buscarLibro(u);
                    case 2 -> txComic.verCatalogo();
                    case 3 -> {
                        System.out.print("Título del cómic a anular reserva: ");
                        String titulo = sc.nextLine();
                        if (titulo.trim().isEmpty()) {
                            throw new ExtensionExcepciones("El título no puede estar vacío", TipoError.DATOS_INCOMPLETOS);
                        }
                        if (confirmar("¿Confirma anular reserva? (S/N): ")) {
                            txComic.anularReservaComic();
                            System.out.println("Se ha anulado la reserva del libro '" + titulo + "'.");
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    }
                    case 4 -> System.out.println("Volviendo al menú principal.");
                }
            } catch (ExtensionExcepciones e) {
                System.out.println(e.getMessage() + " (" + e.getTipoError() + ")");
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        } while (op != 4);
    }

    private void buscarLibro(Usuarios u) throws ExtensionExcepciones {
        try {
            System.out.print("Título del cómic: ");
            String titulo = sc.nextLine();
            if (titulo.trim().isEmpty()) {
                throw new ExtensionExcepciones("El título no puede estar vacío", TipoError.DATOS_INCOMPLETOS);
            }
            Comics c = txComic.buscarComicPorTitulo(titulo);
            if (c == null) {
                throw new ExtensionExcepciones("Cómic no encontrado", TipoError.SIN_EXISTENCIAS);
            }
            if (!c.estaDisponible()) {
                System.out.println("Cómic no disponible");
                return;
            }
            menuLibro(u, c);
        } catch (ExtensionExcepciones e) {
            System.out.println(e.getMessage() + " (" + e.getTipoError() + ")");
            throw e;
        } catch (Exception e) {
            System.out.println("Error al buscar libro: " + e.getMessage());
        }
    }

    private void menuLibro(Usuarios u, Comics c) throws ExtensionExcepciones {
        int op = -1;
        do {
            System.out.println("\n--- MENÚ LIBRO: " + c.getTitulo() + " ---\n1. Comprar\n2. Reservar\n3. Salir");
            System.out.print("Seleccione: ");
            try {
                op = Integer.parseInt(sc.nextLine());
                if (op < 1 || op > 3) {
                    throw new ExtensionExcepciones("Opción de menú no disponible", TipoError.OPCION_NO_DISPONIBLE);
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
                continue;
            } catch (ExtensionExcepciones e) {
                System.out.println(e.getMessage() + " (" + e.getTipoError() + ")");
                continue;
            }
            try {
                switch (op) {
                    case 1 -> {
                        if (confirmar("Confirma compra? (S/N): ")) {
                            txComic.comprarComic(u, c);
                            System.out.println("Se ha realizado la compra del libro '" + c.getTitulo() + "'.");
                        }
                    }
                    case 2 -> {
                        if (confirmar("Confirma reserva? (S/N): ")) {
                            txComic.reservarComic(u, c);
                            System.out.println("Se ha realizado la reserva del libro '" + c.getTitulo() + "'.");
                        }
                    }
                    case 3 -> System.out.println("Regresando al menú usuario.");
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        } while (op != 3);
    }

    private boolean confirmar(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim().equalsIgnoreCase("S");
    }

    private void menuLibros() {
        int op = -1;
        do {
            System.out.println("\n=== MENÚ LIBROS ===\n1. Agregar nuevo libro\n2. Eliminar libro\n3. Salir");
            System.out.print("Seleccione: ");
            try {
                op = Integer.parseInt(sc.nextLine());
                if (op < 1 || op > 3) {
                    throw new ExtensionExcepciones("Opción de menú no disponible", TipoError.OPCION_NO_DISPONIBLE);
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
                continue;
            } catch (ExtensionExcepciones e) {
                System.out.println(e.getMessage() + " (" + e.getTipoError() + ")");
                continue;
            }

            switch (op) {
                case 1 -> agregarLibro();
                case 2 -> eliminarLibro();
                case 3 -> System.out.println("Volviendo al menú principal.");
            }
        } while (op != 3);
    }

    private void agregarLibro() {
        try {
            System.out.print("Título: ");
            String titulo = sc.nextLine().trim();
            if (titulo.isEmpty()) throw new ExtensionExcepciones("El título no puede estar vacío", TipoError.DATOS_INCOMPLETOS);

            System.out.print("Autor: ");
            String autor = sc.nextLine().trim();
            if (autor.isEmpty()) throw new ExtensionExcepciones("El autor no puede estar vacío", TipoError.DATOS_INCOMPLETOS);

            System.out.print("Género: ");
            String genero = sc.nextLine().trim();
            if (genero.isEmpty()) throw new ExtensionExcepciones("El género no puede estar vacío", TipoError.DATOS_INCOMPLETOS);

            int anio;
            while (true) {
                try {
                    System.out.print("Año: ");
                    anio = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido para el año.");
                }
            }

            double precio;
            while (true) {
                try {
                    System.out.print("Precio: ");
                    precio = Double.parseDouble(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido para el precio.");
                }
            }

            Comics nuevo = new Comics(titulo, autor, genero, anio, true, precio);
            txComic.agregarNuevoComic(nuevo);
        } catch (ExtensionExcepciones e) {
            System.out.println(e.getMessage() + " (" + e.getTipoError() + ")");
        } catch (Exception e) {
            System.out.println("Error al agregar libro: " + e.getMessage());
        }
    }

    private void eliminarLibro() {
        System.out.print("Título del cómic a eliminar: ");
        String titulo = sc.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }
        txComic.eliminarComicPorTitulo(titulo);
    }
}
