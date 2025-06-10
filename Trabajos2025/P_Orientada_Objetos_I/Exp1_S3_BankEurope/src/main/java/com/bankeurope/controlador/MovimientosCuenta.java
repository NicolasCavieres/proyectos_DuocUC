/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bankeurope.controlador;

import com.bankeurope.modelo.CuentaBancaria;
import com.bankeurope.utils.ValidarInput;
import java.util.Scanner;

/**
 * Clase para realizar depósitos y giros en cuentas bancarias.
 * @author ariel
 */
public class MovimientosCuenta {

    /**
     * Permite depositar dinero en una cuenta bancaria.
     */
    public static void depositar(CuentaBancaria cuenta) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese monto a depositar: ");
        int monto = ValidarInput.validarAbono(scanner);
        if (monto > 0) {
            cuenta.depositar(monto);
            // El mensaje de saldo ya se imprime en el método depositar de la cuenta
        } else {
            System.out.println("No se realizó el depósito.");
        }
    }

    /**
     * Permite girar dinero desde una cuenta bancaria.
     */
    public static void girar(CuentaBancaria cuenta) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese monto a girar: ");
        int monto = ValidarInput.validarAbono(scanner);
        if (monto > 0 && cuenta.girar(monto)) {
            // El mensaje de saldo ya se imprime en el método girar de la cuenta
            System.out.println("Giro realizado.");
        } else {
            System.out.println("No se pudo realizar el giro (fondos insuficientes o monto inválido).");
        }
    }
}
