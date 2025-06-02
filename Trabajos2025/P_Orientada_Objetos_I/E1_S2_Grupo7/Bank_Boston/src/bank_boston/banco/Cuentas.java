/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank_boston.banco;

/**
 * Clase abstracta que representa una cuenta bancaria genérica.
 * Proporciona atributos y métodos comunes para las subclases.
 * @author ariel
 */
public abstract class Cuentas {
    protected int numeroCuenta;
    protected int saldo;

    /**
     * Constructor que inicializa solo el número de cuenta (saldo en 0).
     */
    public Cuentas(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0;
    }

    /**
     * Constructor que inicializa número de cuenta y saldo.
     */
    public Cuentas(int numeroCuenta, int saldo) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    /**
     * Método abstracto para depositar dinero en la cuenta.
     * Debe ser implementado por las subclases.
     */
    public abstract void depositar(int monto);

    /**
     * Método abstracto para girar dinero de la cuenta.
     * Debe ser implementado por las subclases.
     */
    public abstract void girar(int monto);

    /**
     * Sobrecarga para mostrar saldo de cuenta corriente.
     */
    public void mostrarSaldos(int numeroCorriente, int saldoCorriente) {
        System.out.println("Cuenta Corriente [" + numeroCorriente + "]: $" + saldoCorriente);
    }

    /**
     * Sobrecarga para mostrar saldo de cuenta corriente y ahorro.
     */
    public void mostrarSaldos(int numeroCorriente, int saldoCorriente, int numeroAhorro, int saldoAhorro) {
        mostrarSaldos(numeroCorriente, saldoCorriente);
        System.out.println("Cuenta Ahorro [" + numeroAhorro + "]: $" + saldoAhorro);
    }

    /**
     * Sobrecarga para mostrar saldo de cuenta corriente, ahorro y crédito.
     */
    public void mostrarSaldos(int numeroCorriente, int saldoCorriente, int numeroAhorro, int saldoAhorro, int numeroCredito, int saldoCredito) {
        mostrarSaldos(numeroCorriente, saldoCorriente, numeroAhorro, saldoAhorro);
        System.out.println("Cuenta Crédito [" + numeroCredito + "]: $" + saldoCredito);
    }

    // Getters y setters
    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
