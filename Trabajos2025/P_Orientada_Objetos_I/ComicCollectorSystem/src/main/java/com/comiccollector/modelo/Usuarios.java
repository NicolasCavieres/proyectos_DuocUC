/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.modelo;

/**
 *
 * @author ariel
 */
// src/com/comiccollector/modelo/Usuarios.java
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Representa un usuario y sus compras/reservas.
 */
public class Usuarios {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private int edad;
    private String sexo;
    private String rut;
    private String direccion;

    // Listas de cómics que el usuario ha comprado o reservado
    private final Set<Comics> comicsCompradosUsuario  = new HashSet<>();
    private final Set<Comics> comicsReservadosUsuario = new HashSet<>();

    public Usuarios(int idUsuario,
                    String nombre,
                    String apellido,
                    int edad,
                    String sexo,
                    String rut,
                    String direccion) {
        this.idUsuario = idUsuario;
        this.nombre    = nombre;
        this.apellido  = apellido;
        this.edad      = edad;
        this.sexo      = sexo;
        this.rut       = rut;
        this.direccion = direccion;
    }

    // Campos básicos
    public int    getIdUsuario()  { return idUsuario; }
    public void   setIdUsuario(int id)        { this.idUsuario = id; }
    public String getNombre()     { return nombre; }
    public void   setNombre(String n)         { this.nombre = n; }
    public String getApellido()   { return apellido; }
    public void   setApellido(String a)       { this.apellido = a; }
    public int    getEdad()       { return edad; }
    public void   setEdad(int e)             { this.edad = e; }
    public String getSexo()       { return sexo; }
    public void   setSexo(String s)           { this.sexo = s; }
    public String getRut()        { return rut; }
    public void   setRut(String r)            { this.rut = r; }
    public String getDireccion()  { return direccion; }
    public void   setDireccion(String d)      { this.direccion = d; }

    // Asignación de compras y reservas
    public void asignarCompra(Comics c) {
        comicsReservadosUsuario.remove(c); // No puede estar en ambos
        comicsCompradosUsuario.add(c);
    }
    public void asignarReserva(Comics c) {
        comicsCompradosUsuario.remove(c); // No puede estar en ambos
        comicsReservadosUsuario.add(c);
    }

    // Getters inmutables para otras clases
    public Set<Comics> getComicsCompradosUsuario() {
        return Collections.unmodifiableSet(comicsCompradosUsuario);
    }
    public Set<Comics> getComicsReservadosUsuario() {
        return Collections.unmodifiableSet(comicsReservadosUsuario);
    }

    // Métodos para modificar directamente (para anular reserva, etc)
    public void quitarReserva(Comics c) {
        comicsReservadosUsuario.remove(c);
    }
    public void quitarCompra(Comics c) {
        comicsCompradosUsuario.remove(c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuarios)) return false;
        Usuarios other = (Usuarios) o;
        return idUsuario == other.idUsuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario);
    }

    @Override
    public String toString() {
        return String.format("Usuario[%d] %s %s (%s) RUT: %s", idUsuario, nombre, apellido, sexo, rut);
    }
}