/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.duocuc.biblioteca.visor;

/**
 *
 * @author ariel
 */
import com.duocuc.biblioteca.modelo.Usuario;

public class InfoUsuario {

    public static void mostrarInformacionUsuario(Usuario usuario) {
        System.out.println("===================================");
        System.out.println("Nombre: " + usuario.getNombre() + " " + usuario.getApellido());
        System.out.println("RUT: " + usuario.getRut());
        System.out.println("Teléfono: " + usuario.getTelefono());
        System.out.println("Correo: " + usuario.getCorreo());
        System.out.println("===================================");
    }
}