/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank_boston.banco;

/**
 * Clase que almacena la información y operaciones de un cliente.
 * Implementa la interfaz IMostrable para mostrar información personal.
 * @author ariel
 */
import java.util.Scanner;

public class Clientes implements IMostrable {
    private final String rut;
    private final String nombre;
    private final String apellidoPaterno;
    private final String apellidoMaterno;
    private final String domicilio;
    private final String comuna;
    private final String telefono;

    private CuentaCorriente cuentaCorriente;
    private CuentaAhorro cuentaAhorro;
    private CuentaCredito cuentaCredito;

    /**
     * Constructor que inicializa los datos personales del cliente.
     */
    public Clientes(String rut, String nombre, String apellidoPaterno, String apellidoMaterno,
                    String domicilio, String comuna, String telefono) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.domicilio = domicilio;
        this.comuna = comuna;
        this.telefono = telefono;
    }

    /**
     * Muestra la información personal del cliente.
     */
    @Override
    public void mostrarInformacion() {
        System.out.println("\n--- Información del Cliente ---");
        System.out.println("RUT: " + rut);
        System.out.println("Nombre: " + nombre + " " + apellidoPaterno + " " + apellidoMaterno);
        System.out.println("Domicilio: " + domicilio + ", Comuna: " + comuna);
        System.out.println("Teléfono: " + telefono);
    }

    /**
     * Verifica si el cliente tiene al menos una cuenta activa.
     */
    public boolean tieneCuentasActivas() {
        return cuentaCorriente != null || cuentaAhorro != null || cuentaCredito != null;
    }

    /**
     * Muestra los saldos de todas las cuentas del cliente.
     */
    public void mostrarSaldos() {
        System.out.println("\n--- Saldos de Cuentas ---");
        if (cuentaCorriente != null) {
            System.out.println("Cuenta Corriente [" + cuentaCorriente.numeroCuenta + "]: $" + cuentaCorriente.saldo);
        }
        if (cuentaAhorro != null) {
            System.out.println("Cuenta Ahorro [" + cuentaAhorro.numeroCuenta + "]: $" + cuentaAhorro.saldo);
        }
        if (cuentaCredito != null) {
            System.out.println("Cuenta Crédito [" + cuentaCredito.numeroCuenta + "]: $" + cuentaCredito.saldo);
        }
        if (!tieneCuentasActivas()) {
            System.out.println("Este cliente no tiene cuentas activas.");
        }
    }

    /**
     * Permite al cliente realizar un depósito en una de sus cuentas.
     * @param sc Scanner para entrada de usuario
     */
    public void realizarDeposito(Scanner sc) {
        System.out.println("\n--- Seleccione cuenta para depositar ---");
        if (cuentaCorriente != null) System.out.println("1. Cuenta Corriente");
        if (cuentaAhorro != null) System.out.println("2. Cuenta Ahorro");
        if (cuentaCredito != null) System.out.println("3. Cuenta Crédito");
        int opcion = Validar.validarNumero(sc, "Ingrese una opción: ", 1, 3);
        if (opcion == -1) return;

        int monto = Validar.validarEsNumero(sc, "Ingrese monto a depositar: ");
        if (monto == -1) return;

        if (!Validar.validarAbono(monto)) return;

        switch (opcion) {
            case 1 -> {
                if (cuentaCorriente != null) cuentaCorriente.depositar(monto);
                else System.out.println("No tiene cuenta corriente.");
            }
            case 2 -> {
                if (cuentaAhorro != null) cuentaAhorro.depositar(monto);
                else System.out.println("No tiene cuenta ahorro.");
            }
            case 3 -> {
                if (cuentaCredito != null) cuentaCredito.depositar(monto);
                else System.out.println("No tiene cuenta crédito.");
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    /**
     * Permite al cliente realizar un giro en una de sus cuentas.
     * @param sc Scanner para entrada de usuario
     */
    public void realizarGiro(Scanner sc) {
        System.out.println("\n--- Seleccione cuenta para girar ---");
        if (cuentaCorriente != null) System.out.println("1. Cuenta Corriente");
        if (cuentaAhorro != null) System.out.println("2. Cuenta Ahorro");
        if (cuentaCredito != null) System.out.println("3. Cuenta Crédito");
        int opcion = Validar.validarNumero(sc, "Ingrese una opción: ", 1, 3);
        if (opcion == -1) return;

        int monto = Validar.validarEsNumero(sc, "Ingrese monto a girar: ");
        if (monto == -1) return;

        switch (opcion) {
            case 1 -> {
                if (cuentaCorriente != null) {
                    if (Validar.validarGiro(monto, cuentaCorriente.saldo)) cuentaCorriente.girar(monto);
                } else {
                    System.out.println("No tiene cuenta corriente.");
                }
            }
            case 2 -> {
                if (cuentaAhorro != null) {
                    if (Validar.validarGiro(monto, cuentaAhorro.saldo)) cuentaAhorro.girar(monto);
                } else {
                    System.out.println("No tiene cuenta ahorro.");
                }
            }
            case 3 -> {
                if (cuentaCredito != null) {
                    if (Validar.validarGiro(monto, cuentaCredito.saldo)) cuentaCredito.girar(monto);
                } else {
                    System.out.println("No tiene cuenta crédito.");
                }
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    // Getters
    public String getRut() {
        return rut;
    }

    // Setters para asociar cuentas
    public void setCuentaCorriente(CuentaCorriente cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }

    public void setCuentaAhorro(CuentaAhorro cuentaAhorro) {
        this.cuentaAhorro = cuentaAhorro;
    }

    public void setCuentaCredito(CuentaCredito cuentaCredito) {
        this.cuentaCredito = cuentaCredito;
    }
}
