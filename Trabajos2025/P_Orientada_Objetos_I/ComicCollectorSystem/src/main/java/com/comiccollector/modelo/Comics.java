/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.modelo;

/**
 *
 * @author ariel
 */
// src/com/comiccollector/model/Comics.java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa un cómic.
 * La lista estática 'lComics' almacena todos los cómics del sistema.
 */
public class Comics {
    private String titulo;
    private String autor;
    private String genero;
    private int anio;
    private boolean disponible;
    private double precio;

    /** ArrayList que agrupa todos los cómics */
    private static final List<Comics> lComics = new ArrayList<>();

    public Comics(String titulo, String autor,
                  String genero, int anio, boolean disponible, double precio) {
        this.titulo      = titulo;
        this.autor       = autor;
        this.genero      = genero;
        this.anio        = anio;
        this.disponible  = disponible;
        this.precio      = precio;
    }

    // getters / setters ...
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public boolean estaDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comics)) return false;
        Comics other = (Comics) o;
        return titulo.equalsIgnoreCase(other.titulo)
            && autor.equalsIgnoreCase(other.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo.toLowerCase(), autor.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("%s by %s | Precio: %.2f | Disponible: %s",
            titulo, autor, precio, disponible ? "Sí" : "No");
    }

    /**  
     * Registra un nuevo cómic en la lista.  
     * @return true si se agregó; false si ya existía uno con mismo título y autor.  
     */
    public static boolean registrarComic(Comics c) {
        if (lComics.contains(c)) return false;
        return lComics.add(c);
    }

    /**  
     * Devuelve vista inmutable de todos los cómics.  
     */
    public static List<Comics> getLComics() {
        return Collections.unmodifiableList(lComics);
    }

    /**  
     * Limpia todos los cómics registrados (por ejemplo al recargar CSV).  
     */
    public static void clearLComics() {
        lComics.clear();
    }
}
