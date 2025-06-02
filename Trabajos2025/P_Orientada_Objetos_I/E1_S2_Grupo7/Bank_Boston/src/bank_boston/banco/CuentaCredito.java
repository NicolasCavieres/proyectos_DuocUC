/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package bank_boston.banco;

/**
 * Subclase de Cuentas que representa una cuenta de crédito.
 * Permite girar incluso con saldo negativo y descuenta un 10% extra por giro.
 * Permite saldo negativo y descuenta un 10% extra por cada giro.
 * @author ariel
 */
public class CuentaCredito extends Cuentas {

    /**
     * Constructor de cuenta de crédito.
     */
    public CuentaCredito(int numeroCuenta, int saldo) {
        super(numeroCuenta, saldo);
    }

    /**
     * Permite depositar dinero en la cuenta de crédito.
     */
    @Override
    public void depositar(int monto) {
        if (Validar.validarAbono(monto)) {
            saldo += monto;
            System.out.println("Depósito realizado a Cuenta Crédito. Nuevo saldo disponible: $" + saldo);
        }
    }

    /**
     * Permite girar dinero, descontando un 10% extra y permitiendo saldo negativo.
     */
    @Override
    public void girar(int monto) {
        int descuento = (int) (monto * 0.10);
        int totalGiro = monto + descuento;
        saldo -= totalGiro;
        System.out.println("Uso de crédito realizado. Se descontó un 10% extra ($" + descuento + ") por operación.");
        System.out.println("Advertencia: El saldo puede quedar negativo.\nNuevo saldo: $" + saldo);
    }
}