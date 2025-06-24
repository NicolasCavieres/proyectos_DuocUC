/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duocuc.biblioteca.modelo;

/**
 *
 * @author ariel
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import com.duocuc.biblioteca.excepciones.ExtensionExcepciones;
import com.duocuc.biblioteca.excepciones.TipoError;

public class Usuario {
    private String nombre;
    private String apellido;
    private String rut;
    private String telefono;
    private String correo;
    private HashSet<Libro> librosPrestados;

    // Métodos

    public Usuario(String nombre, String apellido, String rut, String telefono, String correo) throws ExtensionExcepciones {
        this.nombre = nombre;
        this.apellido = apellido;
        setRut(rut);         // Valida formato
        setTelefono(telefono); // Valida formato
        setCorreo(correo);     // Valida formato
        this.librosPrestados = new HashSet<>();
    }

    public void agregarLibroPrestado(Libro libro) {
        librosPrestados.add(libro);
    }

    public void devolverLibro(Libro libro) {
        librosPrestados.remove(libro);
    }

    public static Usuario registrarUsuario(Scanner scanner, HashMap<String, Usuario> usuarios) throws ExtensionExcepciones {
        System.out.println("\n=== REGISTRO DE USUARIO ===");
        try {
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();
            String rut = Usuario.leerRut(scanner);

            if (usuarios.containsKey(rut)) {
                throw new ExtensionExcepciones(TipoError.DATO_DUPLICADO, "Ya existe un usuario con ese RUT.");
            }

            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();
            System.out.print("Correo: ");
            String correo = scanner.nextLine();

            Usuario nuevoUsuario = new Usuario(nombre, apellido, rut, telefono, correo);
            usuarios.put(rut, nuevoUsuario);
            com.duocuc.biblioteca.visor.InfoUsuario.mostrarInformacionUsuario(nuevoUsuario);
            System.out.println("✅ Usuario registrado correctamente.");
            return nuevoUsuario;
        } catch (ExtensionExcepciones e) {
            System.err.println(e.toString());
            throw e;
        } catch (Exception e) {
            throw new ExtensionExcepciones(TipoError.ERROR_SISTEMA, "Error inesperado al registrar usuario.", e);
        }
    }

    public static String leerRut(Scanner scanner) throws ExtensionExcepciones {
        System.out.print("Ingrese RUT: ");
        String rut = scanner.nextLine().trim();
        try {
            if (rut == null || rut.isEmpty() || !rut.matches("^[0-9]+-[0-9kK]$")) {
                throw new Exception("Formato de RUT inválido: debe ser XXXXXXXX-X");
            }
            return rut;
        } catch (Exception e) {
            throw new ExtensionExcepciones(TipoError.FORMATO_INVALIDO, e.getMessage(), e);
        }
    }

    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getRut() {
        return rut;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public HashSet<Libro> getLibrosPrestados() {
        return librosPrestados;
    }

    // Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setRut(String rut) throws ExtensionExcepciones {
        try {
            if (rut == null || rut.trim().isEmpty()) {
                throw new Exception("El RUT no puede estar vacío");
            }
            if (!rut.matches("^[0-9]+-[0-9kK]$")) {
                throw new Exception("Formato de RUT inválido: debe ser XXXXXXXX-X");
            }
            this.rut = rut;
        } catch (Exception e) {
            throw new ExtensionExcepciones(TipoError.FORMATO_INVALIDO, e.getMessage(), e);
        }
    }

    public void setTelefono(String telefono) throws ExtensionExcepciones {
        try {
            if (telefono == null || telefono.trim().isEmpty()) {
                throw new Exception("El teléfono no puede estar vacío");
            }
            if (!telefono.matches("^\\+?[0-9]{9,12}$")) {
                throw new Exception("Formato de teléfono inválido: debe ser +56XXXXXXXXX");
            }
            this.telefono = telefono;
        } catch (Exception e) {
            throw new ExtensionExcepciones(TipoError.FORMATO_INVALIDO, e.getMessage(),
                e);
        }
    }

    public void setCorreo(String correo) throws ExtensionExcepciones {
        try {
            if (correo == null || !correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new Exception("Formato de correo inválido");
            }
            this.correo = correo;
        } catch (Exception e) {
            throw new ExtensionExcepciones(TipoError.FORMATO_INVALIDO, e.getMessage(), e);
        }
    }
}
