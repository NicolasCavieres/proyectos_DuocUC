/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package bank_boston.banco;

/**
 * Subclase de Cuentas que representa una cuenta corriente.
 * Permite depositar y girar indefinidamente
 * Implementación específica para cuenta corriente.
 * Cada giro descuenta $100 por operación.
 * @author ariel
 */
public class CuentaCorriente extends Cuentas {

    /**
     * Constructor de cuenta corriente.
     */
    public CuentaCorriente(int numeroCuenta, int saldo) {
        super(numeroCuenta, saldo);
    }

    /**
     * Permite depositar dinero en la cuenta corriente.
     */
    @Override
    public void depositar(int monto) {
        if (Validar.validarAbono(monto)) {
            saldo += monto;
            System.out.println("Depósito realizado en Cuenta Corriente. Nuevo saldo: $" + saldo);
        }
    }

    /**
     * Permite girar dinero de la cuenta corriente, descontando $100 por operación.
     */
    @Override
    public void girar(int monto) {
        int totalGiro = monto + 100; // $100 por operación
        if (Validar.validarGiro(totalGiro, saldo)) {
            saldo -= totalGiro;
            System.out.println("Giro realizado en Cuenta Corriente. Se descontaron $100 por operación. Nuevo saldo: $" + saldo);
        }
    }
}