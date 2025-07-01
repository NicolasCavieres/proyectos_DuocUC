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
 * Cada objeto Compras representa una venta única.
 * La colección estática 'comprados' almacena todas las compras realizadas.
 */
public class Compras {
    private int idCompra;
    private LocalDate fecha;
    private LocalTime hora;
    private double monto;
    private Usuarios usuarioComprador;

    /** HashSet que agrupa todas las compras */
    private static final Set<Compras> comprados = new HashSet<>();

    public Compras(int id, LocalDate f, LocalTime h, double m, Usuarios u) {
        this.idCompra         = id;
        this.fecha            = f;
        this.hora             = h;
        this.monto            = m;
        this.usuarioComprador = u;
    }

    // getters y setters de atributos...
    public int getIdCompra() { return idCompra; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHora() { return hora; }
    public double getMonto() { return monto; }
    public Usuarios getUsuarioComprador() { return usuarioComprador; }

    // equals/hashCode basados en idCompra
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compras)) return false;
        Compras other = (Compras) o;
        return this.idCompra == other.idCompra;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompra);
    }

    @Override
    public String toString() {
        return String.format("Compra[%d] %s %s Monto: %.2f (Usuario %d)",
            idCompra, fecha, hora, monto, usuarioComprador.getIdUsuario());
    }

    /**  
     * Registra una nueva compra en el HashSet.  
     * @return true si se agregó; false si ya existía una compra con idéntico idCompra.  
     */
    public static boolean registrarCompra(Compras c) {
        return comprados.add(c);
    }

    /**  
     * Devuelve vista inmutable de todas las compras registradas.  
     */
    public static Set<Compras> getComprados() {
        return Collections.unmodifiableSet(comprados);
    }
}
