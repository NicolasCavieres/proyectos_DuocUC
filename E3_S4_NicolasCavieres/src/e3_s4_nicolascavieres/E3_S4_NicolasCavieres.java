/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package e3_s4_nicolascavieres;

/**
 * Se han definido los contadores de intentos como menores a 4 por comodidad.
 * @author NicolasAriel
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class E3_S4_NicolasCavieres {
    public static void main(String[] args) {
        // Inicialización del escáner, instancia de Teatro y lista de compras
        Scanner scanner = new Scanner(System.in);
        Teatro teatro = new Teatro();
        List<String> listaCompra = new ArrayList<>();

        boolean continuarPrograma = true;
        int numerodeintentos = 4;
        int contadorMenu = 0; // Contador para limitar los intentos del menú principal

        while (continuarPrograma && contadorMenu < 4) {
            contadorMenu++;
            System.out.println("\nMenú Principal:");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Salto de línea

            switch (opcion) {
                case 1 -> {
                    boolean continuarCompra = true;
                    int contadorCompra = 0; // Contador para limitar los intentos de compra

                    while (continuarCompra && contadorCompra < numerodeintentos) {
                        contadorCompra++; // incrementamos el contador para limitar el loop
                        continuarCompra = false; // Reiniciar variable por defecto

                        teatro.mostrarPlano();
                        System.out.print("Ingrese la ubicación del asiento: ");
                        String asiento = scanner.nextLine().toUpperCase();

                        if (teatro.asientoDisponible(asiento)) {
                            // Validación de ingreso de edad con contador y manejo de errores
                            int edad = -1;
                            int intentosEdad = 0;
                            while (edad < 0 && intentosEdad < numerodeintentos) {
                                System.out.print("Ingrese su edad: ");
                                if (scanner.hasNextInt()) { // validamos que el usuario ingrese un número
                                    edad = scanner.nextInt(); // guardamos el valor en la variable edad
                                    if (edad < 0) { // validamos que el número sea positivo
                                        System.out.println("La edad debe ser un número positivo.");
                                    }
                                } else { // gestión de error para ingresos distintos a un numero
                                    System.out.println("Entrada inválida. Ingrese un número entero positivo.");
                                    scanner.next(); // Limpiar entrada inválida
                                }
                                intentosEdad++; // incrementamos el contador para limitar el loop
                            }

                            // Límite del loop para el ingreso de la edad
                            if (intentosEdad >= numerodeintentos) {
                                System.out.println("\nDemasiados intentos fallidos para ingresar la edad.\nVolviendo al Menú Principal...");
                                break;
                            }
                            scanner.nextLine(); // Limpiar buffer después de edad

                            // Crear entrada, calcular descuento y reservar asiento
                            Entrada entrada = new Entrada(10000.0, edad);
                            double precioBase = 10000.0;
                            double descuento = entrada.getDescuentoAplicado() / 100.0 * precioBase;
                            double precioFinal = precioBase - descuento;

                            teatro.reservarAsiento(asiento);

                            // Crear y mostrar resumen de la compra
                            String resumen = "Asiento: " + asiento +
                                    ", Precio base: $" + precioBase +
                                    ", Descuento: " + entrada.getDescuentoAplicado() + "%" +
                                    ", Precio final: $" + precioFinal;

                            listaCompra.add(resumen);

                            System.out.println("\nResumen de la compra:");
                            System.out.println(resumen);

                            // Validación para continuar comprando entradas (s/n) con contador
                            int intentosRespuesta = 0;
                            boolean respuestaValida = false; // Reseteo de variable de respuesta valida por defecto
                            while (!respuestaValida && (intentosRespuesta < numerodeintentos)) {
                                System.out.print("\n¿Desea comprar otra entrada? (s/n): ");
                                String respuesta = scanner.nextLine().toLowerCase();
                                if (respuesta.equals("s")) {
                                    continuarCompra = true;
                                    respuestaValida = true;
                                } else if (respuesta.equals("n")) {
                                    respuestaValida = true;
                                    System.out.println("\nResumen total de compras:");
                                    double total = 0.0;
                                    for (String item : listaCompra) {
                                        System.out.println(item);
                                        int index = item.lastIndexOf("$");
                                        if (index != -1) {
                                            try {
                                                total += Double.parseDouble(item.substring(index + 1));
                                            } catch (NumberFormatException e) {
                                                // Ignorar error de formato
                                            }
                                        }
                                    }
                                    System.out.println("Total a pagar: $" + total);
                                    System.out.println("\nGracias por su compra. Hasta luego!");
                                    continuarPrograma = false; // Terminar el programa
                                } else {
                                    System.out.println("\nEntrada no válida. Responda con 's' o 'n'.");
                                    intentosRespuesta++;
                                }
                            }

                            // Limite del loop para confirmar la compra
                            if (!respuestaValida) {
                                System.out.println("\nDemasiados intentos inválidos.\nCerrando el programa...");
                                continuarPrograma = false;
                            }

                        } else if (contadorCompra >= numerodeintentos){
                            System.out.println("\nDemasiados intentos fallidos para ingresar el asiento.\nVolviendo al Menu Principal...");
                            break;                                  
                        } else {
                            System.out.println("\nAsiento no disponible. Intente con otro.");
                            continuarCompra = true; // Permitir nuevo intento
                        }
                    }
                }
                case 2 -> {
                    System.out.println("\nGracias por su visita. Hasta luego!");
                    continuarPrograma = false;
                }
                default -> System.out.println("\nOpción no válida. Intente nuevamente.");
            }
        }

        // Cierre del escáner
        scanner.close();
    }
}
