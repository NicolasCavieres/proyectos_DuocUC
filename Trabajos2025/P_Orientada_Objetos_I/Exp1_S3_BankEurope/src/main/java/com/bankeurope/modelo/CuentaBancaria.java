/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bankeurope.modelo;

import com.bankeurope.vista.InfoCuenta;

/**
 * Clase abstracta base para todas las cuentas bancarias.
 * Define atributos y métodos comunes.
 * @author ariel
 */
public abstract class CuentaBancaria implements InfoCuenta {
    protected int numeroCuenta;
    protected int saldo;

    // Constructor con ambos parámetros
    public CuentaBancaria(int numeroCuenta, int saldo) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    // Sobrecarga: solo número de cuenta, saldo en 0
    public CuentaBancaria(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0;
    }

    // Métodos getter y setter
    public int getNumeroCuenta() { return numeroCuenta; }
    public int getSaldo() { return saldo; }
    public void setSaldo(int saldo) { this.saldo = saldo; }

    // Métodos abstractos que deben implementar las subclases
    public abstract void depositar(int monto);
    public abstract boolean girar(int monto);
    public abstract int calcularInteres(int monto);
    public abstract String getTipo();

    /**
     * Muestra el saldo de la cuenta con formato de pesos chilenos.
     */
    @Override
    public void mostrarSaldoCuenta() {
        System.out.printf("%s [%d]: $ %,d pesos chilenos%n", getTipo(), getNumeroCuenta(), getSaldo());
    }
}
