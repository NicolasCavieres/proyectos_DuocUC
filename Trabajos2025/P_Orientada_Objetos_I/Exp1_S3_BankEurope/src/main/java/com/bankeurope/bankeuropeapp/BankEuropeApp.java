/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.bankeurope.bankeuropeapp;

import com.bankeurope.modelo.Clientes;
import com.bankeurope.utils.Menus;

/**
 * Clase principal que inicia la aplicación del banco.
 * Aquí parte todo el programa.
 * @author ariel
 */
public class BankEuropeApp {

    /**
     * Método main: punto de entrada del programa.
     * Crea el registro de clientes y muestra el menú principal.
     */
    public static void main(String[] args) {
        Clientes registro = new Clientes(); // Lista de todos los clientes
        Menus.menuPrincipal(registro);      // Llama al menú principal
    }
}
