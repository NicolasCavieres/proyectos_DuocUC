/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.control;

/**
 *
 * @author ariel
 */
import com.comiccollector.manager.GestorUsuario;
import com.comiccollector.modelo.Usuarios;
import com.comiccollector.utils.Utils;
import com.comiccollector.io.GestorTXT;

import java.util.Scanner;

public class TransaccionesUsuario {
    private GestorUsuario gestorUsuario;
    private GestorTXT gestorTXT;
    private final Scanner sc = new Scanner(System.in);

    public TransaccionesUsuario(GestorUsuario gu, GestorTXT gestorTXT) {
        this.gestorUsuario = gu;
        this.gestorTXT = gestorTXT;
    }


    public void crearUsuario() {
        String nom = "";
        String ape = "";
        String dir = "";
        String sx = "";
        String rut = "";
        int ed = -1;
        try {
            do {
                System.out.print("Nombre: ");
                nom = sc.nextLine().trim();
                if (nom.isEmpty()) System.out.println("El nombre no puede estar vacío.");
            } while (nom.isEmpty());
            do {
                System.out.print("Apellido: ");
                ape = sc.nextLine().trim();
                if (ape.isEmpty()) System.out.println("El apellido no puede estar vacío.");
            } while (ape.isEmpty());
            do {
                System.out.print("Edad: ");
                String edadStr = sc.nextLine();
                try {
                    ed = Integer.parseInt(edadStr);
                    if (ed <= 0) System.out.println("La edad debe ser mayor a 0.");
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido para la edad.");
                    ed = -1;
                }
            } while (ed <= 0);
            do {
                System.out.print("Sexo (M/F/O): ");
                sx = sc.nextLine().trim().toUpperCase();
                if (!(sx.equals("M") || sx.equals("F") || sx.equals("O"))) {
                    System.out.println("Sexo inválido. Debe ser M, F u O.");
                }
            } while (!(sx.equals("M") || sx.equals("F") || sx.equals("O")));
            do {
                System.out.print("RUT: ");
                rut = sc.nextLine().trim();
                if (!Utils.validarRut(rut)) System.out.println("RUT inválido.");
            } while (!Utils.validarRut(rut));
            do {
                System.out.print("Dirección: ");
                dir = sc.nextLine().trim();
                if (dir.isEmpty()) System.out.println("La dirección no puede estar vacía.");
            } while (dir.isEmpty());
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
            return;
        }
        // Verificar duplicados
        if (gestorUsuario.buscarUsuario(rut, nom) != null) {
            System.out.println("Usuario ya existe con ese RUT y nombre (DUPLICADO)");
            return;
        }
        if (!confirmar("¿Confirma registrar usuario? (S/N): ")) {
            System.out.println("Registro cancelado.");
            return;
        }
        Usuarios u = new Usuarios(
            Utils.generarIDUsuario(),
            nom, ape, ed, sx, rut, dir
        );
        gestorUsuario.agregarUsuario(u);
        try {
            gestorTXT.agregarUsuarioAlArchivo(u);
        } catch (Exception e) {
            System.out.println("Error al guardar el usuario en archivo: " + e.getMessage());
        }
        System.out.println("Usuario creado con ID " + u.getIdUsuario());
    }

    public void agregarUsuario() {
        // Alias de crearUsuario()
        crearUsuario();
    }

    public void modificarUsuario() {
        int id = -1;
        try {
            System.out.print("ID usuario a modificar: ");
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }
        Usuarios u = gestorUsuario.buscarUsuarioPorId(id);
        if (u == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        String dir = "";
        do {
            System.out.print("Nueva dirección: ");
            dir = sc.nextLine().trim();
            if (dir.isEmpty()) System.out.println("La dirección no puede estar vacía.");
        } while (dir.isEmpty());
        u.setDireccion(dir);
        System.out.println("Dirección actualizada.");
    }

    public void eliminarUsuario() {
        if (!confirmar("¿Confirma eliminar usuario? (S/N): ")) return;
        int id = -1;
        try {
            System.out.print("ID usuario a eliminar: ");
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }
        gestorUsuario.eliminarUsuario(id);
        System.out.println("Usuario eliminado.");
    }

    public Usuarios buscarUsuario(String rut, String nombre) {
        return gestorUsuario.buscarUsuario(rut, nombre);
    }

    private boolean confirmar(String msg) {
        System.out.print(msg);
        return sc.nextLine().equalsIgnoreCase("S");
    }
}