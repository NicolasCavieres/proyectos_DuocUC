/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bankeurope.modelo;

/**
 * Representa una cuenta corriente.
 * @author ariel
 */
public class CuentaCorriente extends CuentaBancaria {
    public CuentaCorriente(int numeroCuenta, int saldo) {
        super(numeroCuenta, saldo);
    }
    public CuentaCorriente(int numeroCuenta) {
        super(numeroCuenta);
    }

    @Override
    public String getTipo() {
        return "Corriente";
    }

    // Calcula el interés (1%) para giros
    @Override
    public int calcularInteres(int monto) {
        return (int) (monto * 0.01); // 1% de interés negativo
    }

    // Realiza un depósito en la cuenta
    @Override
    public void depositar(int monto) {
        if (monto > 0) {
            saldo += monto;
            System.out.printf("Depósito realizado en Cuenta Corriente. Nuevo saldo: $ %,d pesos chilenos%n", saldo);
        }
    }

    // Realiza un giro, aplicando interés
    @Override
    public boolean girar(int monto) {
        int interes = calcularInteres(monto);
        int total = monto + interes;
        if (monto > 0 && saldo >= total) {
            saldo -= total;
            System.out.printf("Giro realizado en Cuenta Corriente. Se descontó un 1%% de interés ($ %,d). Nuevo saldo: $ %,d pesos chilenos%n", interes, saldo);
            return true;
        }
        System.out.println("No se pudo realizar el giro en Cuenta Corriente.");
        return false;
    }
}
