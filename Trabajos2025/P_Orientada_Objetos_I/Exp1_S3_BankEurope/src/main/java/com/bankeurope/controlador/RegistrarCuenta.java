/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bankeurope.controlador;

import java.util.Random;
import java.util.Scanner;
import com.bankeurope.modelo.*;

/**
 * Clase para crear cuentas bancarias nuevas.
 * @author ariel
 */
public class RegistrarCuenta {

    // Genera un número de cuenta aleatorio de 9 dígitos
    private static int generarNumeroCuenta() {
        Random random = new Random();
        int numero = 100000000 + random.nextInt(900000000);
        return numero;
    }

    /**
     * Crea una cuenta corriente con saldo $0.
     */
    public static CuentaCorriente crearCuentaCorriente(Scanner scanner) {
        int numero = generarNumeroCuenta();
        System.out.println("Cuenta corriente creada con saldo $0.");
        return new CuentaCorriente(numero);
    }

    /**
     * Crea una cuenta de ahorro con saldo $0.
     */
    public static CuentaAhorro crearCuentaAhorro(Scanner scanner) {
        int numero = generarNumeroCuenta();
        System.out.println("Cuenta ahorro creada con saldo $0.");
        return new CuentaAhorro(numero);
    }

    /**
     * Crea una cuenta digital con saldo $0.
     */
    public static CuentaDigital crearCuentaDigital(Scanner scanner) {
        int numero = generarNumeroCuenta();
        System.out.println("Cuenta digital creada con saldo $0.");
        return new CuentaDigital(numero);
    }
}
