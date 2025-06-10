/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bankeurope.modelo;

import java.util.ArrayList;

/**
 * Clase que almacena y gestiona la lista de clientes del banco.
 * @author ariel
 */
public class Clientes {
    private ArrayList<Cliente> lista = new ArrayList<>();

    /**
     * Agrega un cliente a la lista.
     */
    public void agregarCliente(Cliente c) {
        lista.add(c);
    }

    /**
     * Busca un cliente por nombre y rut.
     * @return el cliente si existe, si no retorna null.
     */
    public Cliente buscarCliente(String nombre, String rut) {
        for (Cliente c : lista) {
            if (c.getNombre().equalsIgnoreCase(nombre)
                && c.getRut().equals(rut)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Devuelve la lista completa de clientes.
     */
    public ArrayList<Cliente> getLista() {
        return lista;
    }
}
