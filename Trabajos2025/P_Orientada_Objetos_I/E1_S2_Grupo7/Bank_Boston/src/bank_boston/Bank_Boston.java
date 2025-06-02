/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bank_boston;

/**
 * Clase principal que contiene el método main y la lógica de interacción con el usuario.
 * Permite registrar nuevos clientes, acceder a clientes existentes y gestionar sus cuentas.
 * @author ariel
 */
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import bank_boston.banco.CuentaAhorro;
import bank_boston.banco.CuentaCredito;
import bank_boston.banco.CuentaCorriente;
import bank_boston.banco.Clientes;
import bank_boston.banco.Validar;
import bank_boston.banco.RegistroClientes;

public class Bank_Boston {

    /**
     * Método principal. Muestra el menú principal y gestiona la navegación del sistema.
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        try (Scanner sc = new Scanner(System.in)) {
            boolean salir = false;
            
            while (!salir) {
                System.out.println("\n=== Bienvenido a Bank Boston ===");
                System.out.println("1. Acceso Cliente");
                System.out.println("2. Nuevo Cliente");
                System.out.println("3. Salir");
                int op = Validar.validarNumero(sc, "Seleccione una opción: ", 1, 3);
                
                switch (op) {
                    case 1 -> accesoCliente(sc);
                    case 2 -> registrarNuevoCliente(sc);
                    case 3 -> {
                        salir = true;
                        System.out.println("Gracias por utilizar Bank Boston.");
                    }
                    default -> System.out.println("Opción no válida.");
                }
            }
        }
    }

    /**
     * Permite el acceso a un cliente existente mediante su RUT.
     * Muestra el menú de operaciones del cliente (depositar, girar, consultar saldo, ver datos personales, salir).
     * @param sc Scanner para entrada de usuario
     */
    private static void accesoCliente(Scanner sc) {
        String rut = Validar.validarRut(sc, "Ingrese RUT (con puntos y guión): ");
        if (rut == null) return; // Si se superan los intentos, aborta la operación

        Clientes cliente = RegistroClientes.buscarClientePorRut(rut);

        if (cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- Menú del Cliente ---");
            System.out.println("1. Depositar");
            System.out.println("2. Girar");
            System.out.println("3. Consultar saldo");
            System.out.println("4. Ver datos personales");
            System.out.println("5. Salir");
            int op = Validar.validarNumero(sc, "Seleccione una opción:", 1, 5);

            switch (op) {
                case 1 -> {
                    if (!cliente.tieneCuentasActivas()) {
                        System.out.println("Este cliente no tiene cuentas activas.");
                        break;
                    }
                    cliente.realizarDeposito(sc);
                }
                case 2 -> {
                    if (!cliente.tieneCuentasActivas()) {
                        System.out.println("Este cliente no tiene cuentas activas.");
                        break;
                    }
                    cliente.realizarGiro(sc);
                }
                case 3 -> cliente.mostrarSaldos();
                case 4 -> cliente.mostrarInformacion();
                case 5 -> volver = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Genera un número de cuenta aleatorio de 9 dígitos.
     * @return número de cuenta generado
     */
    private static int crearNumeroDeCuenta() {
        return 100_000_000 + new java.util.Random().nextInt(900_000_000);
    }

    /**
     * Permite registrar un nuevo cliente solicitando sus datos personales y las cuentas que desea abrir.
     * @param sc Scanner para entrada de usuario
     */
    private static void registrarNuevoCliente(Scanner sc) {
        System.out.println("\n--- Registro de Nuevo Cliente ---");

        String rut = Validar.validarRut(sc, "Ingrese RUT (con puntos y guión): ");
        if (rut == null) return; // Si se superan los intentos, aborta la operación
        if (RegistroClientes.existeCliente(rut)) {
            System.out.println("El cliente ya está registrado.");
            return;
        }

        String nombre = (String) Validar.validarCadena(sc, "Nombre: ");
        if (nombre.equals("-1")) return;

        String apellidoPaterno = (String) Validar.validarCadena(sc, "Apellido Paterno: ");
        if (apellidoPaterno.equals("-1")) return;

        String apellidoMaterno = (String) Validar.validarCadena(sc, "Apellido Materno: ");
        if (apellidoMaterno.equals("-1")) return;

        String domicilio = (String) Validar.validarCadena(sc, "Domicilio: ");
        if (domicilio.equals("-1")) return;

        String comuna = (String) Validar.validarCadena(sc, "Comuna: ");
        if (comuna.equals("-1")) return;

        int telefonoInt = Validar.validarEsNumero(sc, "Teléfono: +56 - ");
        if (telefonoInt == -1) return;
        String telefono = String.valueOf(telefonoInt);

        Clientes cliente = new Clientes(rut, nombre, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);

        // Registro de cuentas
        System.out.print("¿Desea cuenta corriente? [S/N]: ");
        String op = sc.nextLine().toUpperCase();
        if (op.equals("S")) {
            int numero = crearNumeroDeCuenta();
            System.out.println("Número de cuenta corriente generado: " + numero);
            cliente.setCuentaCorriente(new CuentaCorriente(numero, 0));
        }
        
        System.out.print("¿Desea cuenta ahorro? [S/N]: ");
        op = sc.nextLine().toUpperCase();
        if (op.equals("S")) {
            int numero = crearNumeroDeCuenta();
            System.out.println("Número de cuenta ahorro generado: " + numero);
            cliente.setCuentaAhorro(new CuentaAhorro(numero, 0));
        }

        System.out.print("¿Desea cuenta crédito? [S/N]: ");
        op = sc.nextLine().toUpperCase();
        if (op.equals("S")) {
            int numero = crearNumeroDeCuenta();
            System.out.println("Número de cuenta crédito generado: " + numero);
            cliente.setCuentaCredito(new CuentaCredito(numero, 0));
        }

        RegistroClientes.agregarCliente(cliente);
        System.out.println("Cliente registrado exitosamente.");
    }
}