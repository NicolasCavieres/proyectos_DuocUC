/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bankeurope.modelo;

/**
 * Representa una cuenta digital.
 * @author ariel
 */
public class CuentaDigital extends CuentaBancaria {
    public CuentaDigital(int numeroCuenta, int saldo) {
        super(numeroCuenta, saldo);
    }
    public CuentaDigital(int numeroCuenta) {
        super(numeroCuenta);
    }

    @Override
    public String getTipo() {
        return "Digital";
    }

    // Interés: descuenta $500 fijo en cada operación
    @Override
    public int calcularInteres(int monto) {
        return 500;
    }

    // Realiza un depósito, descontando el interés fijo
    @Override
    public void depositar(int monto) {
        int interes = calcularInteres(monto);
        if (monto > 0 && monto > interes) {
            saldo += (monto - interes);
            System.out.printf("Depósito realizado en Cuenta Digital. Se descontó $ %,d de interés fijo. Nuevo saldo: $ %,d pesos chilenos%n", interes, saldo);
        } else {
            System.out.println("El monto a depositar debe ser mayor a $500.");
        }
    }

    // Realiza un giro, descontando el interés fijo
    @Override
    public boolean girar(int monto) {
        int interes = calcularInteres(monto);
        int total = monto + interes;
        if (monto > 0 && saldo >= total) {
            saldo -= total;
            System.out.printf("Giro realizado en Cuenta Digital. Se descontó $ %,d de interés fijo. Nuevo saldo: $ %,d pesos chilenos%n", interes, saldo);
            return true;
        }
        System.out.println("No se pudo realizar el giro en Cuenta Digital.");
        return false;
    }
}
