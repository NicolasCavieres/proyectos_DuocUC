/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.excepciones;

/**
 *
 * @author ariel
 */
public class ExtensionExcepciones extends Exception {
    private final TipoError tipoError;

    public ExtensionExcepciones(String mensaje) {
        super(mensaje);
        this.tipoError = TipoError.ERROR_SISTEMA;
    }

    public ExtensionExcepciones(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public TipoError getTipoError() {
        return tipoError;
    }

    @Override
    public String toString() {
        return "ExtensionExcepciones{" +
                "tipoError=" + tipoError +
                ", mensaje=" + getMessage() +
                '}';
    }
}