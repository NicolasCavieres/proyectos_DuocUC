/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.modelo;

/**
 *
 * @author ariel
 */
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Cada objeto Reservas representa una reserva única.
 * La colección estática 'reservados' mantiene todas las reservas
 * ordenadas por fecha y hora (Comparable).
 */
public class Reservas implements Comparable<Reservas> {
    private LocalDate fecha;
    private LocalTime hora;
    private String titulo;
    private String autor;
    private Usuarios usuarioReservador;

    /** TreeSet que agrupa todas las reservas, ordenadas */
    private static final SortedSet<Reservas> reservados = new TreeSet<>();

    public Reservas(LocalDate f, LocalTime h, String t, String a, Usuarios u) {
        this.fecha             = f;
        this.hora              = h;
        this.titulo            = t;
        this.autor             = a;
        this.usuarioReservador = u;
    }

    // getters y setters de atributos...
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public Usuarios getUsuarioReservador() { return usuarioReservador; }

    @Override
    public int compareTo(Reservas o) {
        int cmp = fecha.compareTo(o.fecha);
        return (cmp != 0) ? cmp : hora.compareTo(o.hora);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservas)) return false;
        Reservas other = (Reservas) o;
        return Objects.equals(fecha, other.fecha) &&
               Objects.equals(hora, other.hora) &&
               titulo.equalsIgnoreCase(other.titulo) &&
               autor.equalsIgnoreCase(other.autor) &&
               usuarioReservador.getIdUsuario() == other.usuarioReservador.getIdUsuario();
    }

    @Override
    public int hashCode() {
        return Objects.hash(fecha, hora,
                            titulo.toLowerCase(),
                            autor.toLowerCase(),
                            usuarioReservador.getIdUsuario());
    }

    @Override
    public String toString() {
        return String.format("Reserva %s %s → '%s' de %s (Usuario %d)",
            fecha, hora, titulo, autor, usuarioReservador.getIdUsuario());
    }

    /**  
     * Registra una nueva reserva en el TreeSet.  
     * @return true si se agregó; false si ya existía una reserva idéntica.  
     */
    public static boolean registrarReserva(Reservas r) {
        return reservados.add(r);
    }

    /**  
     * Devuelve vista inmutable de todas las reservas registradas.  
     */
    public static SortedSet<Reservas> getReservados() {
        return Collections.unmodifiableSortedSet(reservados);
    }
}
