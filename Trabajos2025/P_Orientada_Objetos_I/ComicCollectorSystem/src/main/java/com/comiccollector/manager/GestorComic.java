/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.manager;
/**
 *
 * @author ariel
 */
import com.comiccollector.modelo.Comics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GestorComic {
    private List<Comics> lComics = new ArrayList<>();
    private Set<Comics> todosComicsComprados = new HashSet<>();
    private Set<Comics> todosComicsReservados = new HashSet<>();

    public void cargar(List<Comics> lista) {
        lComics.clear();
        lComics.addAll(lista);
        todosComicsComprados.clear();
        todosComicsReservados.clear();
        // Al cargar, todos los cómics están disponibles
        for (Comics c : lComics) {
            c.setDisponible(true);
        }
    }

    public List<Comics> listar() {
        return lComics;
    }

    public Comics buscarPorTitulo(String titulo) {
        return lComics.stream()
            .filter(c -> c.getTitulo().equalsIgnoreCase(titulo))
            .findFirst().orElse(null);
    }

    public void verCatalogo() {
        System.out.println("Catálogo disponible:");
        for (Comics c : lComics) {
            System.out.println("Título: " + c.getTitulo());
            System.out.println("Autor: " + c.getAutor() + " | Precio: " + String.format("%.2f", c.getPrecio()));
            System.out.println("Género: " + c.getGenero() + " | Año: " + c.getAnio());
            System.out.println("Disponible: " + (c.estaDisponible() ? "Sí" : "No"));
            System.out.println("====================");
        }
    }

    public Set<Comics> getTodosComicsComprados() {
        return todosComicsComprados;
    }
    public Set<Comics> getTodosComicsReservados() {
        return todosComicsReservados;
    }
    public void agregarComicComprado(Comics c) {
        todosComicsComprados.add(c);
        todosComicsReservados.remove(c);
        c.setDisponible(false);
    }
    public void agregarComicReservado(Comics c) {
        todosComicsReservados.add(c);
        todosComicsComprados.remove(c);
        c.setDisponible(false);
    }
    public void liberarComic(Comics c) {
        todosComicsReservados.remove(c);
        todosComicsComprados.remove(c);
        c.setDisponible(true);
    }
    public void sincronizarListas() {
        // Eliminar duplicados y asegurar que un cómic no esté en más de una lista
        for (Comics c : lComics) {
            if (todosComicsComprados.contains(c)) {
                todosComicsReservados.remove(c);
                c.setDisponible(false);
            } else if (todosComicsReservados.contains(c)) {
                todosComicsComprados.remove(c);
                c.setDisponible(false);
            } else {
                c.setDisponible(true);
            }
        }
    }
}