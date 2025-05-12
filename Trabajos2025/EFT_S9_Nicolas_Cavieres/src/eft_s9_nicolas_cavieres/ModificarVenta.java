/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eft_s9_nicolas_cavieres;

/**
 *
 * @author nikoc
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ModificarVenta {

    // Registro global de ventas confirmadas
    private static List<Venta> ventas = new ArrayList<>();

    /**
     * Registra una venta en el registro global.
     *
     * @param header  Cabecera de la venta ({nombreCliente, clienteID, fechaCompra, totalCompra, totalDescuentos}).
     * @param detalle Matriz con el detalle de cada espectador.
     */
    public static void registrarVenta(String[] header, String[][] detalle) {
        ventas.add(new Venta(header, detalle));
    }

    /**
     * Permite modificar una venta identificándola por clienteID y nombre del comprador.
     * Se muestra un submenú con opciones para:
     * - Agregar asientos
     * - Quitar asientos
     * - Reimprimir la venta
     * - Cancelar la compra
     *
     * @param scanner  Objeto Scanner para lectura.
     * @param asientos Objeto AsientosTeatro para actualizar disponibilidad.
     */
    public void modificarVenta(Scanner scanner, AsientosTeatro asientos) {
        System.out.println("=== MODIFICAR VENTA ===");
        System.out.print("Ingrese el ID de su compra: ");
        String idBusqueda = scanner.nextLine().trim();
        System.out.print("Ingrese el nombre del comprador: ");
        String nombreBusqueda = scanner.nextLine().trim();

        Venta ventaEncontrada = null;
        for (Venta v : ventas) {
            // posición 1 es el clienteID y 0 es el nombre del cliente.
            if (v.header[1].equalsIgnoreCase(idBusqueda) &&
                v.header[0].equalsIgnoreCase(nombreBusqueda)) {
                ventaEncontrada = v;
                break;
            }
        }

        if (ventaEncontrada == null) {
            System.out.println("No se encontró ninguna compra con esos datos. Volviendo al Menú Principal.");
            return;
        }

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Opciones de Modificación ---");
            System.out.println("1. Agregar Asientos");
            System.out.println("2. Quitar Asientos");
            System.out.println("3. Reimprimir mi Compra");
            System.out.println("4. Cancelar mi Compra");
            System.out.println("5. Salir de Modificaciones");
            System.out.print("¿Qué le gustaría hacer? (Ingrese número): ");
            int opcion = ValidarInput.validarNumero(scanner, "", 1, 5);
            if (opcion == -1) {
                // Si se supera el límite de intentos, se vuelve al menú principal.
                return;
            }
            switch (opcion) {
                case 1:
                    agregarAsientos(ventaEncontrada, scanner, asientos);
                    break;
                case 2:
                    quitarAsientos(ventaEncontrada, scanner, asientos);
                    break;
                case 3:
                    imprimirVenta(ventaEncontrada);
                    break;
                case 4:
                    cancelarCompra(ventaEncontrada, asientos);
                    ventas.remove(ventaEncontrada);
                    salir = true;
                    break;
                case 5:
                    salir = true;
                    break;
            }
            ventaEncontrada.recalcularTotales();
        }
    }

    /**
     * Método único para imprimir la venta.
     *
     * @param v Objeto Venta a imprimir.
     */
    public static void imprimirVenta(Venta v) {
        v.reimprimirBoleta();
    }
    
    /**
     * Método único para imprimir la última venta.
     *
     * @param v Objeto Venta a imprimir.
     */
    public static void imprimirUltimaVenta() {
        if (!ventas.isEmpty()) {
            imprimirVenta(ventas.get(ventas.size() - 1));
        } else {
            System.out.println("No hay ventas registradas.");
        }
    }    
    
    /**
     * Agrega asientos (nuevos espectadores) a una venta existente.
     *
     * @param venta    La venta a modificar.
     * @param scanner  Objeto Scanner.
     * @param asientos Objeto AsientosTeatro para reserva de asientos.
     */
    private void agregarAsientos(Venta venta, Scanner scanner, AsientosTeatro asientos) {
        System.out.println("=== AGREGAR ASIENTOS ===");
        System.out.print("¿Cuántos asientos desea agregar? ");
        int cantidad = ValidarInput.validarNumero(scanner, "", 1, 10);
        if (cantidad == -1) return;
        
        List<String[]> nuevosDetalles = new ArrayList<>();
        for (int i = 1; i <= cantidad; i++) {
            System.out.println("\n--- Datos del nuevo Espectador ---");
            IngresarCliente espectador = new IngresarCliente();
            espectador.capturarDatosEspectador(scanner, i);
            String nombreEsp = espectador.getNombreEspectador();
            if (nombreEsp == null || nombreEsp.isEmpty()) continue;
            int edadEsp = espectador.getEdadEspectador();
            int anioNac = espectador.getAnioNacimiento();
            int mesNac = espectador.getMesNacimiento();
            int diaNac = espectador.getDiaNacimiento();
            String fechaNacStr = diaNac + "-" + mesNac + "-" + anioNac;
            String sexoEsp = espectador.getSexoEspectador();
            boolean esEstudiante = espectador.isEsEstudianteEspectador();
            
            asientos.mostrarPlanoAsientos(
                asientos.getPlanoTeatro(), 
                asientos.getAsientosVendidos(), 
                asientos.getAsientosTemporales()
            );
            String asientoSeleccionado = ValidarInput.validarAsiento(scanner, "Seleccione el asiento deseado:", asientos.getPlanoTeatro());
            asientoSeleccionado = AsientosTeatro.normalizarAsiento(asientoSeleccionado); // Estandar de Asiento en Mayuscula para Coherencia

            if (asientoSeleccionado == null) continue;
            if (!asientos.reservarAsientoTemporal(asientoSeleccionado)) continue;
            
            double precioBase = PreciosTeatro.obtenerPrecioBase(asientoSeleccionado);
            double maxDescuento = 0.0;
            if (edadEsp < 12) {
                maxDescuento = Math.max(maxDescuento, PreciosTeatro.DESCUENTO_NINOS);
            }
            if (edadEsp >= 65) {
                maxDescuento = Math.max(maxDescuento, PreciosTeatro.DESCUENTO_TERCERA_EDAD);
            }
            if (sexoEsp.equalsIgnoreCase("M")) {
                maxDescuento = Math.max(maxDescuento, PreciosTeatro.DESCUENTO_MUJERES);
            }
            if (esEstudiante) {
                maxDescuento = Math.max(maxDescuento, PreciosTeatro.DESCUENTO_ESTUDIANTES);
            }
            
            double totalParcial = precioBase * (1 - maxDescuento);
            double descuentoParcial = precioBase * maxDescuento;
            
            System.out.println("\nResumen del nuevo Espectador:");
            System.out.println("  Nombre: " + nombreEsp);
            System.out.println("  Edad: " + edadEsp);
            System.out.println("  Fecha de Nacimiento: " + fechaNacStr);
            System.out.println("  Sexo: " + sexoEsp);
            System.out.println("  Estudiante: " + (esEstudiante ? "Sí" : "No"));
            System.out.println("  Asiento seleccionado: " + asientoSeleccionado);
            System.out.println("  Precio base: $" + (int) precioBase);
            System.out.println("  Descuento aplicado: " + (int)(maxDescuento * 100) + "% (Monto: $" + (int) descuentoParcial + ")");
            System.out.println("  Precio final: $" + (int) totalParcial);
            System.out.print("¿Desea agregar este nuevo espectador a la compra? (S/N): ");
            String confirmar = scanner.nextLine().trim();
            if (!confirmar.equalsIgnoreCase("S")) {
                asientos.liberarAsiento(asientoSeleccionado);
                continue;
            }
            
            String[] detalle = {
                nombreEsp,
                String.valueOf(edadEsp),
                fechaNacStr,
                sexoEsp,
                esEstudiante ? "Sí" : "No",
                asientoSeleccionado,
                String.valueOf(totalParcial),
                String.valueOf(descuentoParcial)
            };
            nuevosDetalles.add(detalle);
        }
        // Combinar los nuevos detalles con el detalle existente
        int totalDetalles = venta.detalle.length + nuevosDetalles.size();
        String[][] nuevaMatriz = new String[totalDetalles][];
        for (int i = 0; i < venta.detalle.length; i++) {
            nuevaMatriz[i] = venta.detalle[i];
        }
        for (int i = 0; i < nuevosDetalles.size(); i++) {
            nuevaMatriz[venta.detalle.length + i] = nuevosDetalles.get(i);
        }
        venta.detalle = nuevaMatriz;
        System.out.println("Se han agregado " + nuevosDetalles.size() + " asiento(s) a la compra.");
    }

    /**
     * Quita asientos de una venta existente.
     *
     * @param venta    La venta a modificar.
     * @param scanner  Objeto Scanner.
     * @param asientos Objeto AsientosTeatro para liberar el asiento.
     */
    private void quitarAsientos(Venta venta, Scanner scanner, AsientosTeatro asientos) {
        if (venta.detalle.length == 0) {
            System.out.println("No existen asientos en esta compra para quitar.");
            return;
        }
        System.out.println("\n--- Detalle de Espectadores ---");
        for (int i = 0; i < venta.detalle.length; i++) {
            System.out.println((i + 1) + ". " + venta.detalle[i][0] + " - Asiento: " + venta.detalle[i][5]);
        }
        System.out.print("Ingrese el número de espectador que desea quitar: ");
        int opcion = ValidarInput.validarNumero(scanner, "", 1, venta.detalle.length);
        if (opcion == -1) return;
        String asientoAQuitar = venta.detalle[opcion - 1][5];
        asientos.getAsientosVendidos().remove(asientoAQuitar);
        
        String[][] nuevaMatriz = new String[venta.detalle.length - 1][];
        int indiceNuevo = 0;
        for (int i = 0; i < venta.detalle.length; i++) {
            if (i == (opcion - 1)) continue;
            nuevaMatriz[indiceNuevo++] = venta.detalle[i];
        }
        venta.detalle = nuevaMatriz;
        System.out.println("El asiento del espectador seleccionado se ha quitado de la compra.");
    }
    
    /**
     * Cancela la compra, liberando todos los asientos vendidos asociados.
     *
     * @param venta    La venta a cancelar.
     * @param asientos Objeto AsientosTeatro para liberar los asientos.
     */
    private void cancelarCompra(Venta venta, AsientosTeatro asientos) {
        System.out.println("Cancelando la compra...");
        for (String[] detalle : venta.detalle) {
            String asiento = detalle[5];
            asientos.getAsientosVendidos().remove(asiento);
        }
        System.out.println("La compra ha sido cancelada.");
    }
    
    /**
     * Clase interna que representa una venta.
     * Contiene la cabecera y el detalle de la compra.
     */
    public static class Venta {
        String[] header;    // {nombreCliente, clienteID, fechaCompra, totalCompra, totalDescuentos}
        String[][] detalle; // Matriz con el detalle de cada espectador
        
        public Venta(String[] header, String[][] detalle) {
            this.header = header;
            this.detalle = detalle;
        }
        
        /**
         * Recalcula los totales de la venta a partir del detalle.
         */
        public void recalcularTotales() {
            double totalCompra = 0;
            double totalDescuentos = 0;
            for (String[] fila : detalle) {
                totalCompra += Double.parseDouble(fila[6]);
                totalDescuentos += Double.parseDouble(fila[7]);
            }
            header[3] = String.valueOf(totalCompra);
            header[4] = String.valueOf(totalDescuentos);
        }
        
        /**
         * Imprime la boleta de la venta utilizando un único formato.
         */
        public void reimprimirBoleta() {
            System.out.println("\n-------------------- BOLETA --------------------");
            System.out.println("Cliente: " + header[0]);
            System.out.println("Cliente ID: " + header[1]);
            System.out.println("Fecha de compra: " + header[2]);
            System.out.println("Total Compra: $" + header[3]);
            System.out.println("Total Descuentos: $" + header[4]);
            System.out.println("\nDetalle de Espectadores:");
            for (int i = 0; i < detalle.length; i++) {
                System.out.println("Espectador Nº " + (i + 1) + ":");
                System.out.println("  Nombre: " + detalle[i][0]);
                System.out.println("  Edad: " + detalle[i][1]);
                System.out.println("  Fecha de Nacimiento: " + detalle[i][2]);
                System.out.println("  Sexo: " + detalle[i][3]);
                System.out.println("  Estudiante: " + detalle[i][4]);
                System.out.println("  Asiento: " + detalle[i][5]);
                System.out.println("  Precio Final: $" + detalle[i][6]);
                System.out.println("  Descuento: $" + detalle[i][7]);
            }
            System.out.println("------------------------------------------------\n");
        }
        public String[] getHeader() {
            return header;
        }
        public String[][] getDetalle() {
            return detalle;
        }
    }
    
    // Getter para ventas
    public static List<Venta> getVentas() {
        return ventas;
    }
}