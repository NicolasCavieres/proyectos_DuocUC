/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bankeurope.modelo;

/**
 * Representa una cuenta de ahorro.
 * @author ariel
 */
public class CuentaAhorro extends CuentaBancaria {
    public CuentaAhorro(int numeroCuenta, int saldo) {
        super(numeroCuenta, saldo);
    }
    public CuentaAhorro(int numeroCuenta) {
        super(numeroCuenta);
    }

    @Override
    public String getTipo() {
        return "Ahorro";
    }

    // Calcula el interés (2%) para depósitos
    @Override
    public int calcularInteres(int monto) {
        return (int) (monto * 0.02); // 2% de interés positivo
    }

    // Realiza un depósito, sumando el interés
    @Override
    public void depositar(int monto) {
        if (monto > 0) {
            int interes = calcularInteres(monto);
            saldo += monto + interes;
            System.out.printf("Depósito realizado en Cuenta Ahorro. Se abonó un 2%% extra ($ %,d). Nuevo saldo: $ %,d pesos chilenos%n", interes, saldo);
        }
    }

    // Realiza un giro
    @Override
    public boolean girar(int monto) {
        if (monto > 0 && saldo >= monto) {
            saldo -= monto;
            System.out.printf("Giro realizado en Cuenta Ahorro. Nuevo saldo: $ %,d pesos chilenos%n", saldo);
            return true;
        }
        System.out.println("No se pudo realizar el giro en Cuenta Ahorro.");
        return false;
    }
}
