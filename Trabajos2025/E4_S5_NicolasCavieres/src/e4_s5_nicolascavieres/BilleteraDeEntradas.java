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
import java.util.ArrayList;

public class BilleteraDeEntradas {

    public static void ventaEntradas(PlanoTeatro teatro, Scanner scanner) {
        int cantidad = ValidarInputUsuario.validarNumero(scanner,
                "¿Cuántas entradas desea comprar? (1-20):", 1, 20);
        if (cantidad == -1) return;

        ArrayList<String> asientosSeleccionados = new ArrayList<>();
        ArrayList<TipoCliente> clientes = new ArrayList<>();
        double total = 0;

        for (int i = 0; i < cantidad; i++) {
            System.out.println("\nEntrada " + (i + 1) + ":");
            teatro.mostrarTeatro();

            String tipoAsiento = ValidarInputUsuario.validarTipoEntrada(scanner);
            if (tipoAsiento == null) return;

            int numeroAsiento = ValidarInputUsuario.validarNumero(scanner,
                    "Ingrese el número del asiento (1-5):", 1, 5);
            if (numeroAsiento == -1) return;

            String codigo = tipoAsiento + numeroAsiento;

            String confirmacion = ValidarInputUsuario.validarSiNo(scanner,
                    "¿Quieres reservar el asiento " + codigo + "?");
            if (confirmacion == null) return;
            if (confirmacion.equals("N")) {
                i--; // Reintenta esta entrada
                continue;
            }

            while (teatro.getOcupados().contains(codigo)) {
                System.out.println("Ese asiento ya está ocupado. Intente con otro.");

                tipoAsiento = ValidarInputUsuario.validarTipoEntrada(scanner);
                if (tipoAsiento == null) return;

                numeroAsiento = ValidarInputUsuario.validarNumero(scanner,
                        "Ingrese el número del asiento (1-5):", 1, 5);
                if (numeroAsiento == -1) return;

                codigo = tipoAsiento + numeroAsiento;
            }

            double precioBase = switch (tipoAsiento) {
                case "V" -> 20000;
                case "P" -> 15000;
                case "G" -> 10000;
                default -> 0;
            };

            int edad = ValidarInputUsuario.validarNumero(scanner, "Ingrese la edad del espectador:", 1, 120);
            if (edad == -1) return;

            TipoCliente cliente = new TipoCliente(edad);
            double precioFinal = precioBase * (1 - cliente.getCantidadDescuento());

            asientosSeleccionados.add(codigo);
            clientes.add(cliente);
            total += precioFinal;

            System.out.printf("Precio para este asiento: $%.0f (%s, %.0f%% de descuento)\n",
                    precioFinal, cliente.getCategoriaCliente(), cliente.getPorcentajeDescuento());
        }

        // Descuentos por cantidad
        if (cantidad > 3 && cantidad < 10) {
            total *= 0.95;
            System.out.println("Descuento adicional del 5% aplicado por cantidad.");
        } else if (cantidad >= 10 && cantidad < 15) {
            total *= 0.90;
            System.out.println("Descuento adicional del 10% aplicado por cantidad.");
        } else if (cantidad >= 15) {
            total *= 0.80;
            System.out.println("Descuento adicional del 20% aplicado por cantidad.");
        }

        System.out.println("\nResumen de compra:");
        for (int i = 0; i < cantidad; i++) {
            System.out.printf("Asiento: %s - Cliente: %s - Edad: %d\n",
                    asientosSeleccionados.get(i),
                    clientes.get(i).getCategoriaCliente(),
                    clientes.get(i).getEdadUsuario());
        }
        System.out.printf("Total a pagar: $%.0f\n", total);

        String confirmar = ValidarInputUsuario.validarSiNo(scanner, "¿Desea confirmar la compra?");
        if (confirmar == null) return;

        if (confirmar.equals("S")) {
            for (String asiento : asientosSeleccionados) {
                teatro.getOcupados().add(asiento);
            }
            System.out.println("Compra registrada exitosamente.");
        } else {
            System.out.println("Compra cancelada.");
        }
    }
    
    public static void eliminarEntrada(PlanoTeatro teatro, Scanner scanner) {
        System.out.print("Ingrese el código del asiento que desea eliminar: ");
        String codigo = scanner.nextLine().toUpperCase();

        if (teatro.getOcupados().contains(codigo)) {
            teatro.getOcupados().remove(codigo);
            System.out.println("Entrada eliminada y asiento liberado.");
        } else {
            System.out.println("Ese asiento no está registrado como ocupado.");
        }
    }

    public static void mensajeIntentosSuperados() {
        System.out.println("Has superado el número de intentos permitidos.\nVolviendo al Menú Principal...\n");
    }
}