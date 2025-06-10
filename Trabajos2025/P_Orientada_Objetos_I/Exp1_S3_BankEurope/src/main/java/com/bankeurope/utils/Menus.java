/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bankeurope.utils;

import com.bankeurope.modelo.*;
import com.bankeurope.controlador.*;
import java.util.Scanner;

/**
 * Clase que gestiona todos los menús e interacción con el usuario.
 * @author ariel
 */
public class Menus {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Muestra el menú principal del sistema.
     */
    public static void menuPrincipal(Clientes registro) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Acceso Cliente");
            System.out.println("2. Nuevo Cliente");
            System.out.println("3. Salir");
            System.out.print("Ingrese una opción:\n-> ");
            int op = ValidarInput.validarNumero(scanner, 1, 3);
            switch (op) {
                case 1:
                    menuAccesoCliente(registro);
                    break;
                case 2:
                    Cliente nuevo = RegistrarCliente.registrar();
                    if (nuevo != null) {
                        registro.agregarCliente(nuevo);
                        System.out.println("Cliente registrado.");
                    }
                    break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    /**
     * Muestra el menú de acceso para clientes ya registrados.
     */
    public static void menuAccesoCliente(Clientes registro) {
        System.out.println("\n--- ACCESO CLIENTE ---");

        System.out.print("Nombre:\n-> ");
        String nombre = ValidarInput.validarCadena(scanner);

        System.out.print("RUT (con puntos y guión):\n-> ");
        String rut = ValidarInput.validarCadena(scanner);

        Cliente c = registro.buscarCliente(nombre, rut);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        if (!c.tieneCuentas()) {
            System.out.println("No tiene cuentas asociadas.");
            System.out.println("Nombre: " + c.getNombre());
            System.out.println("Apellido Paterno: " + c.getApellidoPaterno());
            System.out.println("RUT: " + c.getRut());
            return;
        }
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("1. Depósitos");
            System.out.println("2. Giros");
            System.out.println("3. Visualizar datos personales");
            System.out.println("4. Salir");
            System.out.print("Ingrese una opción:\n-> ");
            int opc = ValidarInput.validarNumero(scanner, 1, 5);
            CuentaBancaria cuenta = null;
            switch (opc) {
                case 1:
                    cuenta = elegirCuenta(c);
                    if (cuenta != null) MovimientosCuenta.depositar(cuenta);
                    break;
                case 2:
                    cuenta = elegirCuenta(c);
                    if (cuenta != null) MovimientosCuenta.girar(cuenta);
                    break;
                case 3:
                    c.mostrarInformacionCliente();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    /**
     * Permite al usuario elegir una de sus cuentas.
     */
    private static CuentaBancaria elegirCuenta(Cliente c) {
        System.out.println("Seleccione cuenta:");
        int idx = 1;
        CuentaBancaria[] cuentas = new CuentaBancaria[3];
        if (c.getCuentaCorriente() != null) {
            System.out.println(idx + ". Corriente");
            cuentas[idx - 1] = c.getCuentaCorriente();
            idx++;
        }
        if (c.getCuentaAhorro() != null) {
            System.out.println(idx + ". Ahorro");
            cuentas[idx - 1] = c.getCuentaAhorro();
            idx++;
        }
        if (c.getCuentaDigital() != null) {
            System.out.println(idx + ". Digital");
            cuentas[idx - 1] = c.getCuentaDigital();
        }
        System.out.print("Ingrese una opción:\n-> ");
        int op = ValidarInput.validarNumero(scanner, 1, idx - 1);
        if (op >= 1 && op <= 3 && cuentas[op - 1] != null) {
            return cuentas[op - 1];
        }
        System.out.println("Opción inválida.");
        return null;
    }
}
