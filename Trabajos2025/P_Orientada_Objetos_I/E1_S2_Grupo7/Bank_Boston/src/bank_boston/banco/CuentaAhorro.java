/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank_boston.banco;

/**
 * Subclase de Cuentas que representa una cuenta de ahorro.
 * Permite girar solo una vez y da un abono éxtra por cada depósito.
 * Cada depósito abona un 2% extra y solo permite girar el total del saldo.
 * @author ariel
 */
import java.util.Scanner;

public class CuentaAhorro extends Cuentas {

    /**
     * Constructor de cuenta de ahorro.
     */
    public CuentaAhorro(int numeroCuenta, int saldo) {
        super(numeroCuenta, saldo);
    }

    /**
     * Permite depositar dinero en la cuenta de ahorro, abonando un 2% extra.
     */
    @Override
    public void depositar(int monto) {
        if (Validar.validarAbono(monto)) {
            int abonoExtra = (int) (monto * 0.02);
            saldo += monto + abonoExtra;
            System.out.println("Depósito realizado en Cuenta Ahorro. Se abonó un 2% extra ($" + abonoExtra + "). Nuevo saldo: $" + saldo);
        }
    }

    /**
     * Permite girar solo el total del saldo, solicitando confirmación.
     */
    @Override
    public void girar(int monto) {
        if (monto != saldo) {
            System.out.println("Solo puede girar el total de su saldo en Cuenta Ahorro ($" + saldo + ").");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("¿Está seguro que desea girar el total de su saldo? [S/N]: ");
        String confirm = sc.nextLine().trim().toUpperCase();
        if (confirm.equals("S")) {
            saldo = 0;
            System.out.println("Giro total realizado. Su saldo ahora es $0.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
}