/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bankeurope.utils;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Métodos utilitarios para validar entradas y operaciones bancarias.
 * Controla intentos y muestra mensajes de error.
 * @author ariel
 */
public class ValidarInput {
    private static final int MAX_INTENTOS = 5;

    /**
     * Muestra mensaje cuando se superan los intentos permitidos.
     */
    public static String mensajeIntentosSuperados() {
        String mensaje = "Ha superado el número de intentos permitidos.\nVolviendo al menú principal.";
        System.out.println(mensaje);
        return mensaje;
    }

    /**
     * Valida que el saldo ingresado sea un número entero mayor o igual a 0.
     */
    public static boolean validarSaldo(Scanner scanner) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            System.out.print("Ingrese saldo: ");
            String entrada;
            try {
                entrada = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Error: No se pudo leer la entrada.");
                return false;
            }
            try {
                int saldo = Integer.parseInt(entrada);
                if (saldo >= 0) {
                    return true;
                } else {
                    System.out.println("Error: El saldo no puede ser negativo");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
            intentos--;
        }
        mensajeIntentosSuperados();
        return false;
    }

    /**
     * Valida que el giro sea un número válido y no supere el saldo.
     */
    public static boolean validarGiro(Scanner scanner, int saldo) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            System.out.print("Ingrese monto a girar: ");
            String entrada;
            try {
                entrada = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Error: No se pudo leer la entrada.");
                return false;
            }
            try {
                int giro = Integer.parseInt(entrada);
                if (giro < 0) {
                    System.out.println("Error: El monto a girar debe ser mayor a 0.");
                } else if (giro > saldo) {
                    System.out.println("Error: No puede realizar un giro mayor a su saldo.");
                } else {
                    return true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
            intentos--;
        }
        mensajeIntentosSuperados();
        return false;
    }

    /**
     * Valida que el abono sea un número mayor que cero.
     */
    public static int validarAbono(Scanner scanner) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            String entrada;
            try {
                entrada = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Error: No se pudo leer la entrada.");
                return -1;
            }
            try {
                int numero = Integer.parseInt(entrada);
                if (numero > 0) {
                    return numero;
                } else {
                    System.out.println("Error: Debe ingresar un número mayor que cero");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número. No se permiten otros caracteres.");
            }
            intentos--;
        }
        mensajeIntentosSuperados();
        return -1;
    }

    /**
     * Valida que la cadena ingresada no esté vacía.
     */
    public static String validarCadena(Scanner scanner) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            String cadena;
            try {
                cadena = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Error: No se pudo leer la entrada.");
                return "";
            }
            if (cadena != null && !cadena.isEmpty()) {
                return cadena;
            } else {
                System.out.println("Error: Debe ingresar al menos un carácter.");
            }
            intentos--;
            System.out.print("-> ");
        }
        mensajeIntentosSuperados();
        return "";
    }

    /**
     * Valida que el número esté dentro de un rango.
     */
    public static int validarNumero(Scanner scanner, int min, int max) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            String entrada;
            try {
                entrada = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Error: No se pudo leer la entrada.");
                return -1;
            }
            try {
                int numero = Integer.parseInt(entrada);
                if (numero >= min && numero <= max) {
                    return numero;
                } else {
                    System.out.println("Error: Debe ingresar un número entre " + min + " y " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            }
            intentos--;
        }
        mensajeIntentosSuperados();
        return -1;
    }

    /**
     * Valida que la opción ingresada sea S o N.
     */
    public static String validarOpcionSN(Scanner scanner) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            String opcion;
            try {
                opcion = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Error: No se pudo leer la entrada.");
                return "N";
            }
            if (opcion == null || opcion.isEmpty()) {
                System.out.println("Error: Debe ingresar una opción (S o N).");
            } else {
                String opcionMod = opcion.toUpperCase();
                if (opcionMod.equals("S") || opcionMod.equals("N")) {
                    return opcionMod;
                } else {
                    System.out.println("Error: La opción ingresada es inválida. Debe ser 'S' o 'N'.");
                }
            }
            intentos--;
        }
        mensajeIntentosSuperados();
        return "N";
    }

    /**
     * Solicita y valida el RUT con reintentos.
     */
    public static String validarRut(Scanner scanner) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            String rut;
            try {
                rut = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Error: No se pudo leer la entrada.");
                return "";
            }
            if (rut.length() < 11 || rut.length() > 12) {
                System.out.println("Error: El RUT debe tener entre 11 y 12 caracteres.");
            } else if (rut.chars().filter(c -> c == '-').count() != 1) {
                System.out.println("Error: El RUT debe contener un único guión ('-').");
            } else if (rut.charAt(rut.length() - 2) != '-') {
                System.out.println("Error: El guión debe estar antes del dígito verificador.");
            } else if (!rut.matches("[0-9.]+-[0-9kK]")) {
                System.out.println("Error: El RUT solo debe contener números, puntos y un guión, sin letras fuera del dígito verificador.");
            } else {
                return rut;
            }
            intentos--;
            if (intentos > 0) {
                System.out.print("RUT (con puntos y guión): ");
            }
        }
        mensajeIntentosSuperados();
        return "";
    }

    /**
     * Valida que la entrada sea un número entero.
     */
    public static boolean validarEsNumero(Scanner scanner) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            String entrada;
            try {
                entrada = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Error: No se pudo leer la entrada.");
                return false;
            }
            if (entrada == null || entrada.isEmpty()) {
                System.out.println("Error: Debe ingresar a lo menos un número.");
            } else {
                try {
                    Integer.parseInt(entrada);
                    return true;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Solo se permiten números enteros, sin puntos o comas.");
                }
            }
            intentos--;
        }
        mensajeIntentosSuperados();
        return false;
    }

    /**
     * Valida que el teléfono tenga exactamente 8 dígitos numéricos.
     */
    public static String validarTelefono(Scanner scanner) {
        int intentos = MAX_INTENTOS;
        while (intentos > 0) {
            String numero;
            try {
                numero = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Error: No se pudo leer la entrada.");
                return "";
            }
            // Solo acepta exactamente 8 dígitos numéricos, sin letras ni otros caracteres
            if (numero.matches("^[0-9]{8}$")) {
                return numero;
            } else {
                System.out.println("Error: Debe ingresar exactamente 8 dígitos numéricos (sin letras ni otros caracteres).");
            }
            intentos--;
            System.out.print("-> ");
        }
        mensajeIntentosSuperados();
        return "";
    }
}