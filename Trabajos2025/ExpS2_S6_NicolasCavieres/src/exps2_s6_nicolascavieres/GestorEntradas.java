/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exps2_s6_nicolascavieres;

/**
 *
 * @author nikoc
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GestorEntradas {

    private static final double PRECIO_VIP = 20000;
    private static final double PRECIO_PLATEA = 15000;
    private static final double PRECIO_GENERAL = 10000;

    public static void procesarVenta(PlanoTeatro teatro, Scanner scanner, 
                                     EntradasReservadas reservas, EntradasVendidas ventas) {
        System.out.print("Ingrese su nombre: ");
        /* BREAKPOINT -> nombreCliente almacenará el nombre del cliente.*/
        String nombreCliente = scanner.nextLine();

        int cantidad = ValidarInputUsuario.validarNumero(scanner,"¿Cuántas entradas desea comprar? (1-24):", 1, 24);
        /* BREAKPOINT -> Analizamos el valor de cantidad
        cantidad debería tomar un valor entre 1 y 24, igual a la
        cantidad de veces que se repite la estructura for.*/
        if (cantidad == -1) return;

        ArrayList<String> asientosSeleccionados = new ArrayList<>();
        ArrayList<TipoCliente> clientes = new ArrayList<>();
        double total = 0;

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\n=== Entrada " + (i + 1) + " ===");
            if (!seleccionarAsiento(teatro, scanner, asientosSeleccionados, clientes)) {
                System.out.println("Operación cancelada.");
                return;
            }

            double precio = calcularPrecioEntrada(asientosSeleccionados.get(i), clientes.get(i));
            total += precio;
            mostrarResumenParcial(asientosSeleccionados, clientes, i, precio);
        }

        total = aplicarDescuentoCantidad(cantidad, total);
        mostrarResumenFinal(nombreCliente, asientosSeleccionados, clientes, total);

        int opcion = ValidarInputUsuario.validarNumero(scanner,
                "\n¿Qué desea hacer?\n1. Reservar\n2. Comprar\n3. Cancelar", 1, 3);

        if (opcion == 3) {
            System.out.println("Operación cancelada.");
            return;
        }

        String mensajeConfirmacion = (opcion == 1)
                ? "Vamos a Reservar sus asientos por 10 minutos.\n¿Desea continuar? (S/N):"
                : "¿Confirmar compra y generar boleta? (S/N):";
        
        String confirmacion = ValidarInputUsuario.validarLetra(scanner, mensajeConfirmacion, new String[]{"S", "N"});
        
        /* BREAKPOINT -> Analizamos el valor de confirmación.
        Si es S, debería confirmar la compra, si es N debería cancelarla y hacer
        print del mensaje "Operación Cancelada"*/
        if (!"S".equals(confirmacion)) {
            System.out.println("Operación cancelada.");
            return;
        }

        procesarTransaccion(opcion, teatro, nombreCliente, asientosSeleccionados,
                clientes, total, reservas, ventas);
    }

    private static boolean seleccionarAsiento(PlanoTeatro teatro, Scanner scanner,
                                              ArrayList<String> asientosSeleccionados,
                                              ArrayList<TipoCliente> clientes) {
        teatro.mostrarTeatro();

        String tipoAsiento = ValidarInputUsuario.validarLetra(scanner,
                "Ingrese tipo de asiento (V: VIP, P: Preferencial, G: General):",
                new String[]{"V", "P", "G"});
        if (tipoAsiento == null) return false;

        int numeroAsiento = ValidarInputUsuario.validarNumero(scanner,
                "Ingrese el número del asiento (1-8):", 1, 8);
        if (numeroAsiento == -1) return false;

        String codigoAsiento = tipoAsiento + numeroAsiento;

        if (teatro.getOcupados().contains(codigoAsiento)) {
            System.out.println("¡Asiento no disponible! Por favor seleccione otro.");
            return false;
        }

        String confirmacion = ValidarInputUsuario.validarLetra(scanner,
                "¿Confirmar asiento " + codigoAsiento + "? (S/N):",
                new String[]{"S", "N"});
        if (!"S".equals(confirmacion)) return false;

        int edad = ValidarInputUsuario.validarNumero(scanner,
                "Ingrese la edad del espectador:", 1, 120);
        if (edad == -1) return false;

        asientosSeleccionados.add(codigoAsiento);
        clientes.add(new TipoCliente(edad));
        return true;
    }

    public static double calcularPrecioEntrada(String codigoAsiento, TipoCliente cliente) {
        double precioBase = switch (codigoAsiento.charAt(0)) {
            case 'V' -> PRECIO_VIP;
            case 'P' -> PRECIO_PLATEA;
            case 'G' -> PRECIO_GENERAL;
            default -> 0;
        };
        return precioBase * (1 - cliente.getDescuento());
    }

    private static void mostrarResumenParcial(ArrayList<String> asientos,
                                              ArrayList<TipoCliente> clientes,
                                              int index, double precio) {
        System.out.println("\n--- Resumen entrada " + (index + 1) + " ---");
        System.out.println("Asiento: " + asientos.get(index));
        System.out.println("Tipo cliente: " + clientes.get(index).getTipoCliente());
        System.out.printf("Precio con descuento: $%.0f\n", precio);
        System.out.println("----------------------------");
    }

    public static double aplicarDescuentoCantidad(int cantidad, double total) {
        if (cantidad >= 15) {
            total *= 0.80;
            System.out.println("\n¡Descuento del 20% por compra de 15+ entradas!");
        } else if (cantidad >= 10) {
            total *= 0.90;
            System.out.println("\n¡Descuento del 10% por compra de 10-14 entradas!");
        } else if (cantidad > 3) {
            total *= 0.95;
            System.out.println("\n¡Descuento del 5% por compra de 4-9 entradas!");
        }
        return total;
    }

    private static void mostrarResumenFinal(String nombre, ArrayList<String> asientos,
                                            ArrayList<TipoCliente> clientes, double total) {
        System.out.println("\n========= RESUMEN FINAL =========");
        System.out.println("Cliente: " + nombre);
        for (int i = 0; i < asientos.size(); i++) {
            System.out.printf("Entrada %d: Asiento %s - %s (Edad: %d)\n",
                    i + 1, asientos.get(i),
                    clientes.get(i).getTipoCliente(),
                    clientes.get(i).getEdadCliente());
        }
        System.out.printf("\nTOTAL A PAGAR: $%.0f\n", total);
        System.out.println("================================");
    }

    private static void procesarTransaccion(int opcion, PlanoTeatro teatro,
                                            String nombre, ArrayList<String> asientos,
                                            ArrayList<TipoCliente> clientes, double total,
                                            EntradasReservadas reservas, EntradasVendidas ventas) {

        if (opcion == 1) { // Reservar
            for (String asiento : asientos) {
                teatro.ocuparAsiento(asiento);} // ocupar asiento
            
            HashMap<String, Object> reserva = new HashMap<>();
            reserva.put("nombre", nombre);
            reserva.put("asientos", new ArrayList<>(asientos));
            reserva.put("clientes", new ArrayList<>(clientes));
            reserva.put("total", total);

            reservas.agregarReserva(reserva);
            System.out.println("¡Reserva registrada exitosamente!");
            reservas.mostrarResumenReserva(reserva);

        } else if (opcion == 2) { // Comprar
            for (String asiento : asientos) {
                teatro.ocuparAsiento(asiento);} // ocupar asiento

            HashMap<String, Object> venta = new HashMap<>();
            venta.put("nombre", nombre);
            venta.put("asientos", new ArrayList<>(asientos));
            venta.put("clientes", new ArrayList<>(clientes));
            venta.put("total", total);

            ventas.registrarVenta(venta, teatro);
            ventas.imprimirBoleta(ventas.getListaVentas().size());
        }
    }
}
