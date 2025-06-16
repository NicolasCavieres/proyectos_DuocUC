/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duocuc.biblioteca.visor;

/**
 *
 * @author ariel
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public static int mostrarMenu(Scanner scanner) {
        int opcion = -1;

        System.out.println("\n========== MENÚ PRINCIPAL ==========");
        System.out.println("1. Registrar Usuario");
        System.out.println("2. Registrar Libro");
        System.out.println("3. Buscar Libro");
        System.out.println("4. Pedir Libro");
        System.out.println("5. Devolver Libro");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");

        try {
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un número válido.");
            scanner.nextLine(); // limpiar entrada inválida
        }
        
        return opcion;
    }
}