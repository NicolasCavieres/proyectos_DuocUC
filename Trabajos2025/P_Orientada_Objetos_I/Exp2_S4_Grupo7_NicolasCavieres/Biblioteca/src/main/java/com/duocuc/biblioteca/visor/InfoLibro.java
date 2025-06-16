/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.duocuc.biblioteca.visor;

import com.duocuc.biblioteca.modelo.Libro;

public class InfoLibro {
    public static void mostrarInformacionLibro(Libro libro) {
        System.out.println("----- Información del Libro -----");
        System.out.println("Nombre: " + libro.getNombre());
        System.out.println("Autor: " + libro.getAutor());
        System.out.println("Año: " + libro.getAnio());
        System.out.println("Disponible: " + (libro.isDisponible() ? "Sí" : "No"));
        System.out.println("Ubicación: " + libro.getUbicacion());
        System.out.println("---------------------------------");
    }
}

