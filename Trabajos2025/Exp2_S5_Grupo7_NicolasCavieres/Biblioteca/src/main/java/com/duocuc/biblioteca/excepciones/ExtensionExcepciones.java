/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duocuc.biblioteca.excepciones;

/**
 *
 * @author ariel
 */

public class ExtensionExcepciones extends Exception {
    private final TipoError tipo;

    public ExtensionExcepciones(TipoError tipo, String mensaje) {
        super(mensaje);
        this.tipo = tipo;
    }

    public ExtensionExcepciones(TipoError tipo, String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.tipo = tipo;
    }

    public TipoError getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Error [" + tipo + "]: " + getMessage();
    }
}