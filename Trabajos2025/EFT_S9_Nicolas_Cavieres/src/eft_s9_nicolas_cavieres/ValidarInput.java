/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eft_s9_nicolas_cavieres;

/**
 *
 * @author nikoc
 */

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ValidarInput {
    /**
     * Determina la cantidad de veces que se puede equivocar el usuario
     */    
    public static final int cantidadDeErrores = 5;

    public static void mensajeIntentosSuperados() {
        System.out.println("Has superado el número de intentos permitidos.\nVolviendo al Menú Principal...\n");
    }

    /**
     * Valida sea ingresado un número entero y que éste se encuentre entre el valor minimo y máximo.
     *
     * @param scanner Objeto Scanner para leer la entrada del usuario.
     * @param mensaje Mensaje de solicitud (por ejemplo, "Ingrese el código del asiento deseado").
     * @param plano   Arreglo 2D con el plano del teatro (por ejemplo, el arreglo de AsientosTeatro).
     * @return El código de asiento ingresado si es válido, o null en caso de superar el límite de intentos.
     */
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

    /**
     * Valida que la entrada del usuario sea una de las cadenas especificadas en 'opcionesValidas'.
     * Se permite un máximo de 'cantidadDeErrores' intentos.
     *
     * @param scanner         Objeto Scanner para leer la entrada del usuario.
     * @param mensaje         Mensaje para solicitar la entrada.
     * @param opcionesValidas Arreglo de cadenas que representan los valores permitidos.
     * @return La entrada validada (convertida a mayúsculas), o null cuando se superan los intentos.
     */
    public static String validarLetra(Scanner scanner, String mensaje, String[] opcionesValidas) {
        int contadorIntentos = cantidadDeErrores;
        while (contadorIntentos > 0) {
            System.out.print(mensaje + "\n-> ");
            String opcion = scanner.nextLine().trim().toUpperCase();
            for (String opcionValida : opcionesValidas) {
                if (opcion.equals(opcionValida.toUpperCase())) {
                    return opcion;
                }
            }
            System.out.println("Entrada inválida.\nIntente nuevamente con alguno de los siguientes valores permitidos:\n" + Arrays.toString(opcionesValidas));
            contadorIntentos--;
        }
        mensajeIntentosSuperados();
        return null;
    }

    /**
     * Valida que el código de asiento ingresado exista en el plano del teatro.
     *
     * @param scanner Objeto Scanner para leer la entrada del usuario.
     * @param mensaje Mensaje de solicitud (por ejemplo, "Ingrese el código del asiento deseado").
     * @param plano   Arreglo 2D con el plano del teatro (por ejemplo, el arreglo de AsientosTeatro).
     * @return El código de asiento ingresado si es válido, o null en caso de superar el límite de intentos.
     */
    public static String validarAsiento(Scanner scanner, String mensaje, String[][] plano) {
        int contadorIntentos = cantidadDeErrores;
        while (contadorIntentos > 0) {
            System.out.print(mensaje + "\n-> ");
            String entrada = scanner.nextLine().trim();
            boolean valido = false;
            // Se recorre el plano para verificar si el asiento existe.
            for (int i = 0; i < plano.length && !valido; i++) {
                for (int j = 0; j < plano[i].length && !valido; j++) {
                    if (plano[i][j].equalsIgnoreCase(entrada)) {
                        valido = true;
                    }
                }
            }
            if (valido) {
                return entrada;
            } else {
                System.out.println("Lo siento, éste asiento no existe, intente otra vez.");
            }
            contadorIntentos--;
        }
        mensajeIntentosSuperados();
        return null;
    }
    
    /**
     * Valida que el texto ingresado contenga únicamente letras (y espacios) y no esté vacío.
     * Utiliza un contador de intentos basado en 'cantidadDeErrores'.
     *
     * @param scanner Objeto Scanner para leer la entrada.
     * @param mensaje Mensaje a mostrar al usuario.
     * @return Un String con el texto validado (conserva el formato que ingrese el usuario) o null si se agotan los intentos.
     */
    public static String validarTexto(Scanner scanner, String mensaje) {
        int contadorIntentos = cantidadDeErrores;
        while (contadorIntentos > 0) {
            System.out.print(mensaje + "\n-> ");
            String texto = scanner.nextLine().trim();
            // Verifica que la entrada no esté vacía y que contenga únicamente letras (A-Z o a-z)
            if (texto.isEmpty()) {
                System.out.println("Entrada inválida. No puede estar vacía.");
            } else if (texto.matches("^[A-Za-zÑñ]+$")) {
                return texto;
            } else {
                System.out.println("Entrada inválida. Debe contener solo letras (A-Z o a-z) sin espacios ni otros caracteres.");
            }
            contadorIntentos--;
        }
        mensajeIntentosSuperados();
        return null;
    }
    
    /**
     * Lee la entrada del usuario con un timeout.
     *
     * @param scanner       Objeto Scanner.
     * @param timeoutSeconds Tiempo de espera en segundos.
     * @param mensaje        Mensaje a mostrar al usuario.
     * @param defaultValue   Valor por defecto a utilizar en caso de timeout.
     * @return La entrada del usuario si llega en tiempo o el valor por defecto si se agota el tiempo.
     */
    public static String leerInputConTimeout(Scanner scanner, int timeoutSeconds, String mensaje, String defaultValue) {
        System.out.print(mensaje + "\n-> ");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> task = () -> scanner.nextLine();
        String input = defaultValue;
        try {
            Future<String> future = executor.submit(task);
            input = future.get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("\nTiempo agotado.");
        }
        executor.shutdownNow();
        return input;
    }
    
    /**
     * Valida que la entrada del usuario (leída con timeout) sea una de las cadenas especificadas en 'opcionesValidas'.
     * Se permite un máximo de 'cantidadDeErrores' intentos; en cada uno se espera un tiempo determinado antes de tomar el valor por defecto.
     *
     * @param scanner       Objeto Scanner para leer la entrada.
     * @param timeoutSeconds Tiempo de espera en segundos para cada intento.
     * @param mensaje        Mensaje a mostrar al usuario.
     * @param defaultValue   Valor por defecto (idealmente debe formar parte de las opciones válidas).
     * @param opcionesValidas Arreglo de cadenas válidas (por ejemplo, {"S", "N"}).
     * @return La entrada validada (convertida a mayúsculas), o el valor por defecto si se agota el número de intentos.
     */
    public static String validarLetraConTimeout(Scanner scanner, int timeoutSeconds, String mensaje, String defaultValue, String... opcionesValidas) {
        int contadorIntentos = cantidadDeErrores;
        while (contadorIntentos > 0) {
            // Lee la entrada con timeout (por ejemplo, 20 segundos)
            String entrada = leerInputConTimeout(scanner, timeoutSeconds, mensaje, defaultValue);
            entrada = entrada.trim().toUpperCase();
            
            // Verifica si la entrada coincide con alguna de las opciones válidas
            for (String opcionValida : opcionesValidas) {
                if (entrada.equals(opcionValida.toUpperCase())) {
                    return entrada;
                }
            }
            
            System.out.println("Entrada inválida. Valores permitidos: " + Arrays.toString(opcionesValidas));
            contadorIntentos--;
        }
        mensajeIntentosSuperados();
        return defaultValue;
    }
}    