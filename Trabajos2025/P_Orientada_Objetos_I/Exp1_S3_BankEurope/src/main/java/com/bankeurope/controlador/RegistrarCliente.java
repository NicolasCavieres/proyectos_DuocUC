/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bankeurope.controlador;

import java.util.Scanner;
import com.bankeurope.modelo.*;
import com.bankeurope.utils.ValidarInput;

/**
 * Clase para registrar un nuevo cliente y sus cuentas.
 * @author ariel
 */
public class RegistrarCliente {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Solicita y valida todos los datos del cliente.
     * Permite crear cuentas opcionales.
     */
    public static Cliente registrar() {
        System.out.print("RUT (con puntos y guión): ");
        String rut = ValidarInput.validarRut(scanner);
        if (rut.isEmpty()) {
            System.out.println("Registro cancelado por intentos fallidos en RUT.");
            return null;
        }

        // Validar Nombre
        System.out.print("Nombre: ");
        String nombre = ValidarInput.validarCadena(scanner);
        if (nombre.isEmpty()) {
            System.out.println("Registro cancelado por intentos fallidos en Nombre.");
            return null;
        }

        // Validar Apellido Paterno
        System.out.print("Apellido Paterno: ");
        String apellidoPaterno = ValidarInput.validarCadena(scanner);
        if (apellidoPaterno.isEmpty()) {
            System.out.println("Registro cancelado por intentos fallidos en Apellido Paterno.");
            return null;
        }

        // Validar Apellido Materno
        System.out.print("Apellido Materno: ");
        String apellidoMaterno = ValidarInput.validarCadena(scanner);
        if (apellidoMaterno.isEmpty()) {
            System.out.println("Registro cancelado por intentos fallidos en Apellido Materno.");
            return null;
        }

        // Validar Domicilio
        System.out.print("Domicilio: ");
        String domicilio = ValidarInput.validarCadena(scanner);
        if (domicilio.isEmpty()) {
            System.out.println("Registro cancelado por intentos fallidos en Domicilio.");
            return null;
        }

        // Validar Comuna
        System.out.print("Comuna: ");
        String comuna = ValidarInput.validarCadena(scanner);
        if (comuna.isEmpty()) {
            System.out.println("Registro cancelado por intentos fallidos en Comuna.");
            return null;
        }

        // Validar Teléfono
        System.out.print("Teléfono:\n+569 ");
        String telefonoIngresado = ValidarInput.validarTelefono(scanner);
        if (telefonoIngresado.isEmpty()) {
            System.out.println("Registro cancelado por intentos fallidos en Teléfono.");
            return null;
        }
        String telefono = "+569 " + telefonoIngresado;

        // Crear Cliente
        Cliente cliente = new Cliente(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);

        // Opcional: Crear cuentas
        System.out.print("¿Desea cuenta corriente? [S/N]: ");
        String opcionCC = ValidarInput.validarOpcionSN(scanner);
        if (opcionCC.equals("S")) {
            cliente.setCuentaCorriente(RegistrarCuenta.crearCuentaCorriente(scanner));
        }

        System.out.print("¿Desea cuenta ahorro? [S/N]: ");
        String opcionAH = ValidarInput.validarOpcionSN(scanner);
        if (opcionAH.equals("S")) {
            cliente.setCuentaAhorro(RegistrarCuenta.crearCuentaAhorro(scanner));
        }

        System.out.print("¿Desea cuenta digital? [S/N]: ");
        String opcionCD = ValidarInput.validarOpcionSN(scanner);
        if (opcionCD.equals("S")) {
            cliente.setCuentaDigital(RegistrarCuenta.crearCuentaDigital(scanner));
        }

        return cliente;
    }
}
