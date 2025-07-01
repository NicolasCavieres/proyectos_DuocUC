/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.manager;

/**
 *
 * @author ariel
 */
import com.comiccollector.modelo.Usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GestorUsuario {
    private Map<Integer, Usuarios> lUsuarios = new HashMap<>();

    public void cargar(Map<Integer,Usuarios> map) {
        lUsuarios.clear();
        lUsuarios.putAll(map);
    }

    public Usuarios buscarUsuario(String rut, String nombre) {
        return lUsuarios.values().stream()
            .filter(u -> u.getRut().equalsIgnoreCase(rut.trim()) &&
                        u.getNombre().equalsIgnoreCase(nombre.trim()))
            .findFirst()
            .orElse(null);
    }

    public Usuarios buscarUsuarioPorId(int id) {
        return lUsuarios.get(id);
    }

    public void verComprasUsuario() {
        System.out.println("=== Compras por usuario ===");
        for (Usuarios u : lUsuarios.values()) {
            System.out.printf("Usuario %d: %s%n", 
                u.getIdUsuario(), u.getComicsCompradosUsuario());
        }
    }

    public void verReservasUsuario() {
        System.out.println("=== Reservas por usuario ===");
        for (Usuarios u : lUsuarios.values()) {
            System.out.printf("Usuario %d: %s%n", 
                u.getIdUsuario(), u.getComicsReservadosUsuario());
        }
    }

    public void verListadoDeUsuario() {
        lUsuarios.values().forEach(System.out::println);
    }

    public void agregarUsuario(Usuarios u) {
        lUsuarios.put(u.getIdUsuario(), u);
    }

    public void eliminarUsuario(int idUsuario) {
        lUsuarios.remove(idUsuario);
    }

    public List<Usuarios> getUsuarios() {
        return new ArrayList<>(lUsuarios.values());
    }
}