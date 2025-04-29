/* Diagrama del flujo de funciones, clases y variables

////////////////////////////////////////////////////////////////////////////////////////////////

Recomiendo revisar el archivo PlanificacionProyectoTeatroMoro.md
En él se encuentra toda la información resumida del pseudo código, con la planificación
de el flujo del menú, la segmentación de los métodos y variables; para ayudarme
a crear éste trabajo.

////////////////////////////////////////////////////////////////////////////////////////////////
*/

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package exps2_s6_nicolascavieres;

/**
 *
 * @author nikoc
 */
import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalTime;
import java.util.ArrayList;

public class ExpS2_S6_NicolasCavieres {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlanoTeatro teatro = new PlanoTeatro();
        EntradasReservadas entradasReservadas = new EntradasReservadas(teatro);
        EntradasVendidas entradasVendidas = new EntradasVendidas();
        // Método nuevo para el resumen
        GestorDeVentas gestor = new GestorDeVentas();

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n==== Menú Principal ====");
            System.out.println("1. Comprar Entradas.");
            System.out.println("2. Ver Promociones.");
            System.out.println("3. Modificar una reserva.");
            System.out.println("4. Modificar una venta.");
            System.out.println("5. Ver asientos disponibles.");
            System.out.println("6. Ver Resumen de Ventas Totales.");
            System.out.println("7. Salir.");
            System.out.println("==========================");

            VariablesEstadisticas.cantidadDeInteracciones++;
            /* BREAKPOINT -> Analizamos si se activa el contador de interacciones con el Menu Principal.
            debería incrementar el valor de la variable candidadDeInteracciones */
            int opcion = ValidarInputUsuario.validarNumero(scanner, "Seleccione una opción:", 1, 6);

            switch (opcion) {
                case 1 -> {
                    GestorEntradas.procesarVenta(teatro, scanner, entradasReservadas, entradasVendidas);
                }

                case 2 -> {
                    VerPromociones.mostrar();
                    break;
                }

                // MODIFICAR UNA RESERVA
                case 3 -> {
                    // System.out.print("Ingrese el ID de su reserva: ");
                    int reservaID = ValidarInputUsuario.validarNumero(scanner, "Ingrese el ID de su reserva:", 1, 1000);
                    // scanner.nextLine(); // limpiar buffer

                    HashMap<String, Object> reserva = entradasReservadas.buscarReserva(reservaID);
                    if (reserva == null) {
                        System.out.println("Reserva no encontrada.");
                        break;
                    }

                    entradasReservadas.imprimirReserva(reserva);

                    boolean seguir = true;
                    while (seguir) {
                        System.out.println("\n¿Qué deseas hacer con esta reserva?");
                        System.out.println("1. Modificar mi reserva");
                        System.out.println("2. Anular mi reserva");
                        System.out.println("3. Comprar mi reserva");
                        System.out.println("4. Salir");
                        int opcionSub = ValidarInputUsuario.validarNumero(scanner, "", 1,4);
                        //        scanner.nextInt();
                        // scanner.nextLine();

                        switch (opcionSub) {
                            case 1 -> {
                                System.out.println("¿Cómo deseas modificarla?");
                                System.out.println("1. Agregar asiento");
                                System.out.println("2. Quitar asiento");
                                int subop = scanner.nextInt();
                                scanner.nextLine();
                                if (subop == 1) {
                                    entradasReservadas.agregarAsiento(scanner, reserva, teatro);
                                } else if (subop == 2) {
                                    entradasReservadas.quitarAsiento(scanner, reserva);
                                }
                                // Actualizar la reserva en la lista
                                entradasReservadas.imprimirReserva(reserva);
                            }
                            case 2 -> {
                                entradasReservadas.cancelarReserva(reservaID);
                                VariablesEstadisticas.totalVentasReservasCanceladas++;
                                seguir = false;
                            }
                            case 3 -> {
                                HashMap<String, Object> compra = entradasReservadas.confirmarCompra(reservaID);
                                if (compra != null) {
                                    entradasVendidas.registrarVenta(compra, teatro);
                                    // Marcar asientos como ocupados
                                    @SuppressWarnings("unchecked")
                                    ArrayList<String> asientos = (ArrayList<String>) compra.get("asientos");
                                    asientos.forEach(teatro::ocuparAsiento);
                                    /* BREAKPOINT -> Analizamos si se activa el contador de Reservas Compradas.
                                    debería incrementar el valor de la variable totalReservasCompradas */
                                    VariablesEstadisticas.totalReservasCompradas++;
                                }
                                seguir = false;
                            }
                            case 4 -> seguir = false;
                            default -> System.out.println("Opción inválida.");
                        }
                    }
                }

                // MODIFICAR UNA VENTA
                case 4 -> {
                    System.out.print("\nIngrese el ID de su compra: ");
                    int id = ValidarInputUsuario.validarNumero(scanner, "ID de venta", 1, Integer.MAX_VALUE);
                    HashMap<String, Object> venta = entradasVendidas.buscarVenta(id);

                    if (venta == null) {
                        System.out.println("No se encontró una venta con ese ID.");
                        break;
                    }

                    entradasVendidas.imprimirBoleta(id);

                    boolean modificarVenta = true;
                    while (modificarVenta) {
                        System.out.println("\n¿Qué deseas hacer?");
                        System.out.println("1. Modificar mi compra");
                        System.out.println("2. Anular mi compra");
                        System.out.println("3. Reimprimir boleta");
                        System.out.println("4. Salir al menú principal");
                        int opcionVenta = ValidarInputUsuario.validarNumero(scanner, "Selecciona una opción:", 1, 4);

                        switch (opcionVenta) {
                        case 1 -> {
                            System.out.println("¿Cómo desea modificarla?");
                            System.out.println("1. Agregar Asiento");
                            System.out.println("2. Quitar Asiento");
                            int opcionMod = ValidarInputUsuario.validarNumero(scanner, "Seleccione una opción:", 1, 2);

                            teatro.mostrarTeatro();  // Mostrar plano del teatro

                            System.out.print("Ingresa el código del asiento (ej: V1, P3, G2): ");
                            String codigo = scanner.nextLine().toUpperCase();

                            if (opcionMod == 1) {
                                entradasVendidas.agregarAsiento(id, codigo, teatro, scanner);
                            } else if (opcionMod == 2) {
                                entradasVendidas.quitarAsiento(id, codigo, teatro);
                            }

                            // Mostrar boleta actualizada
                            entradasVendidas.imprimirBoleta(id);
                        }
                            case 2 -> {
                                entradasVendidas.cancelarVenta(id, teatro);
                                VariablesEstadisticas.totalVentasReservasCanceladas++;
                                modificarVenta = false;
                            }
                            case 3 -> entradasVendidas.imprimirBoleta(id);
                            case 4 -> modificarVenta = false;
                        }
                    }
                }

                case 5 -> {
                    System.out.println("\nA continuación podrá ver el mapa del teatro.");
                    System.out.println("Los asientos con una [X] ya están ocupados, mientras que los asientos con su respectivo código están disponibles.");
                    teatro.mostrarTeatro();
                }
                case 6 -> {
                    // Ver resumen de ventas
                    
                    gestor.resumenVentas(entradasVendidas.getListaVentas());
                }
                    
                case 7 -> {
                    /** BREAKPOINT -> Analizamos si se cierra bien el programa por el loop while.
                    continuar tiene un valor verdadero por defecto, y mientras continuar sea verdadero
                    el loop while seguira eternamente. Si cambiamos el valor de
                    continuar como falso, el programa no debería continuar con el loop while*/
                    continuar = false;
                    mensajeDeSalida();
                    
                    /*
                    Aplicar solo para hacer print de las variables estadísticas
                    VariablesEstadisticas.mostrarResumenFinal();
                    */
                }

                default -> System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }

    public static void mensajeDeSalida() {
        LocalTime ahora = LocalTime.now();
        /* BREAKPOINT -> Analizamos si se lee bien la hora.
        la variable hora debería tomar la hora (getHour) de la hora actual (LocalTime)
        para determinar si es de mañana, tarde o noche.*/
        int hora = ahora.getHour();
        String saludo = (hora >= 6 && hora < 12) ? "un buen día"
                : (hora >= 12 && hora < 20) ? "una buena tarde" : "buenas noches";

        System.out.println("\nGracias por visitar el Teatro Moro.");
        System.out.println("Que tengas " + saludo + ", y nos vemos pronto.");
        System.out.println("----------[Programa terminado]-------------");
    }
}