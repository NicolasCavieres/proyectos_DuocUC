/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package e4_s5_nicolascavieres;

/**
 *
 * @author nikoc
 */

import java.util.Scanner;

public class ValidarInputUsuario {

    public static final int cantidadDeErrores = 5;

    public static void mensajeIntentosSuperados() {
        System.out.println("Has superado el número de intentos permitidos.\nVolviendo al Menú Principal...\n");
    }

    public static int validarNumero(Scanner scanner, String mensaje, int min, int max) {
        int contadorIntentos = cantidadDeErrores;
        while (contadorIntentos > 0) {
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
            contadorIntentos--;
        }
        mensajeIntentosSuperados();
        return -1;
    }

    public static String validarTipoEntrada(Scanner scanner) {
        int contadorIntentos = cantidadDeErrores;
        while (contadorIntentos > 0) {
            System.out.print("Ingrese el tipo de asiento (VIP -> V, Platea -> P, General -> G):\n-> ");
            String tipo = scanner.nextLine().trim().toUpperCase();
            if (tipo.equals("V") || tipo.equals("P") || tipo.equals("G")) {
                return tipo;
            } else {
                System.out.println("Tipo no válido. Intente nuevamente.");
                contadorIntentos--;
            }
        }
        mensajeIntentosSuperados();
        return null;
    }

    public static String validarSiNo(Scanner scanner, String mensaje) {
        int contadorIntentos = cantidadDeErrores;
        while (contadorIntentos > 0) {
            System.out.print(mensaje + " (S/N):\n-> ");
            String opcion = scanner.nextLine().trim().toUpperCase();
            if (opcion.equals("S") || opcion.equals("N")) {
                return opcion;
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese 'S' o 'N'.");
                contadorIntentos--;
            }
        }
        mensajeIntentosSuperados();
        return null;
    }

    public static String validarCodigoAsiento(Scanner scanner) {
        int contadorIntentos = cantidadDeErrores;
        while (contadorIntentos > 0) {
            System.out.print("Ingrese el código del asiento (ej: V1, P3, G4):\n-> ");
            String codigo = scanner.nextLine().trim().toUpperCase();
            if (codigo.matches("^[VPG][1-5]$")) {
                return codigo;
            } else {
                System.out.println("Código inválido. Intente nuevamente.");
                contadorIntentos--;
            }
        }
        mensajeIntentosSuperados();
        return null;
    }
}