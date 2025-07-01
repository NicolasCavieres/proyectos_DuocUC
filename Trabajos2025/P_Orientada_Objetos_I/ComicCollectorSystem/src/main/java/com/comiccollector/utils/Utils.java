/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.utils;

/**
 * Clase utilitaria con métodos estáticos de ayuda.
 */
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {
    private static AtomicInteger seqUser  = new AtomicInteger(100);
    private static AtomicInteger seqCompra = new AtomicInteger(500);

    public static int generarIDUsuario() {
        return seqUser.getAndIncrement();
    }

    public static int generarIDCompra() {
        return seqCompra.getAndIncrement();
    }

    public static boolean validarRut(String rut) {
        // placeholder básico: debe contener guión
        return rut != null && rut.contains("-");
    }

    public static void setearIDUsuarioInicial(int id) {
        seqUser.set(id);
    }
}