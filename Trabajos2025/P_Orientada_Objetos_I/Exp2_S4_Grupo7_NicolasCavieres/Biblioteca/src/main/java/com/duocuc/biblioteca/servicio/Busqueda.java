/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duocuc.biblioteca.servicio;

import com.duocuc.biblioteca.modelo.Libro;
import com.duocuc.biblioteca.excepciones.ExtensionExcepciones;
import com.duocuc.biblioteca.excepciones.TipoError;
import java.util.ArrayList;

public class Busqueda {

    /**
     * Busca libros por título en una lista de libros.
     * @param libros Lista de libros disponibles.
     * @param titulo Título del libro a buscar.
     * @return Lista de libros que coinciden con el título.
     * @throws ExtensionExcepciones si el título es inválido.
     */
    public static ArrayList<Libro> buscarPorTitulo(ArrayList<Libro> libros, String titulo) throws ExtensionExcepciones {
        ArrayList<Libro> resultados = new ArrayList<>();
        try {
            if (titulo == null || titulo.trim().isEmpty()) {
                throw new ExtensionExcepciones(TipoError.DATO_INVALIDO, "Ingrese un término de búsqueda.");
            }
            for (Libro libro : libros) {
                if (libro.getNombre().toLowerCase().contains(titulo.toLowerCase())) {
                    resultados.add(libro);
                }
            }
        } catch (NullPointerException e) {
            throw new ExtensionExcepciones(TipoError.ERROR_SISTEMA, "Error inesperado en la búsqueda.", e);
        }
        return resultados;
    }
}