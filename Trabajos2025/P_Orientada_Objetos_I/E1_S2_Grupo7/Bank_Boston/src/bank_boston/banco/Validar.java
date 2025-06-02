/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank_boston.banco;

import java.util.Scanner;

/**
 * Métodos estáticos para validar entradas y operaciones bancarias.
 * Controla intentos y muestra mensajes de error.
 * @author ariel
 */
public class Validar {
    private static final int MAX_INTENTOS = 5;

    // Mensaje a mostrar cuando se supera el número de intentos
    public static void mensajeIntentosSuperados() {
        System.out.println("Ha superado el número máximo de intentos permitidos.");
    }

    /**
     * Valida que el número de cuenta sea un entero de 9 dígitos.
     */
    public static boolean validarNumeroCuenta(int numeroCuenta) {
        if (numeroCuenta >= 100000000 && numeroCuenta <= 999999999) {
            return true;
        } else {
            System.out.println("Error: El número de cuenta debe ser un entero de 9 dígitos.");
            return false;
        }
    }

    /**
     * Valida que el saldo sea mayor o igual a cero.
     */
    public static boolean validarSaldo(int saldo) {
        if (saldo >= 0) {
            return true;
        } else {
            System.out.println("Error: El saldo no puede ser negativo.");
            return false;
        }
    }

    /**
     * Valida que el monto a depositar (abono) sea mayor que cero.
     */
    public static boolean validarAbono(int abono) {
        if (abono > 0) {
            return true;
        } else {
            System.out.println("Error: El monto a depositar debe ser mayor que cero.");
            return false;
        }
    }

    /**
     * Valida que el giro (retiro) cumpla condiciones de saldo.
     */
    public static boolean validarGiro(int giro, int saldo) {
        if (saldo <= 0) {
            System.out.println("Error: Para realizar un giro, el saldo debe ser mayor que 0.");
            return false;
        }
        if (giro <= 0) {
            System.out.println("Error: El monto a girar debe ser mayor que cero.");
            return false;
        }
        if (giro > saldo) {
            System.out.println("Error: No se puede girar un monto mayor que el saldo disponible.");
            return false;
        }
        return true;
    }

    /**
     * Valida que el usuario ingrese un número dentro de un rango, con 5 intentos.
     */
    public static int validarNumero(Scanner scanner, String mensaje, int min, int max) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            System.out.print(mensaje + "\n-> ");
            String entrada = scanner.nextLine().trim();
            try {
                int numero = Integer.parseInt(entrada);
                if (numero >= min && numero <= max) {
                    return numero;
                } else {
                    System.out.println("Debe ingresar un número entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
            intentos--;
        }
        mensajeIntentosSuperados();
        return -1;
    }

    /**
     * Valida que una cadena no esté vacía (puede tener espacios).
     */
    public static Object validarCadena(Scanner scanner, String mensaje) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            System.out.print(mensaje);
            String cadena = scanner.nextLine();
            if (cadena != null && !cadena.trim().isEmpty()) {
                return cadena;
            } else {
                System.out.println("Error: Debe ingresar a lo menos un caracter.");
            }
            intentos--;
        }
        mensajeIntentosSuperados();
        return -1;
    }

    /**
     * Valida que el valor ingresado sea un número mayor a 0.
     */
    public static int validarEsNumero(Scanner scanner, String mensaje) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                int numero = Integer.parseInt(entrada);
                if (numero > 0) {
                    return numero;
                } else {
                    System.out.println("Debe ingresar un número mayor que cero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
            intentos--;
        }
        mensajeIntentosSuperados();
        return -1;
    }

    /**
     * Valida un RUT con estructura: 11 o 12 caracteres, incluyendo puntos y guion.
     * Controla intentos y muestra mensajes de error.
     */
    public static String validarRut(Scanner sc, String mensaje) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            System.out.print(mensaje);
            String rut = sc.nextLine().trim();
            if (rut == null) {
                System.out.println("Error: RUT no puede ser nulo.");
                intentos--;
                continue;
            }
            // Validar largo
            if (rut.length() < 11 || rut.length() > 12) {
                System.out.println("Error: El RUT debe tener entre 11 y 12 caracteres.");
                intentos--;
                continue;
            }
            // Validar que contenga solo caracteres válidos: números, puntos, guion y k/K
            if (!rut.matches("^[0-9\\.\\-kK]+$")) {
                System.out.println("Error: El RUT solo puede contener números, puntos, guión y/o la letra k/K.");
                intentos--;
                continue;
            }
            // Validar que esté en un formato tipo: XX.XXX.XXX-X o X.XXX.XXX-X
            if (!rut.matches("^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$")) {
                System.out.println("Error: El formato del RUT no es válido. Ejemplo: 12.345.678-9");
                intentos--;
                continue;
            }
            // Si es correcto
            return rut;
        }
        mensajeIntentosSuperados();
        return null;
    }    
}