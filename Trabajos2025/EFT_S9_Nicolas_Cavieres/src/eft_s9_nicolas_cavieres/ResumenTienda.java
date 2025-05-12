/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eft_s9_nicolas_cavieres;

/**
 *
 * @author nikoc
 */
import java.util.List;
import java.util.Scanner;

public class ResumenTienda {
    private static final String CONTRASENA = "MORO2025";
    private static final int MAX_INTENTOS = 5;
    
    /**
     * Muestra el submenú para el administrador y presenta:
     * - Reimpresión de todas las ventas.
     * - Un resumen global de las ventas.
     */
    public void mostrarResumenTienda(Scanner scanner, AsientosTeatro asientos) {
        // Saludo para el administrador.
        System.out.println("\n=== Resumen de Tienda ===");
        System.out.println("Bienvenido al módulo de Resumen de Tienda.\n");
        
        // Verificar contraseña (máximo 5 intentos)
        int intentos = MAX_INTENTOS;
        boolean accesoConcedido = false;
        while (intentos > 0) {
            System.out.print("Ingrese la contraseña de la tienda: ");
            String pass = scanner.nextLine().trim();
            if (pass.equals(CONTRASENA)) {
                accesoConcedido = true;
                break;
            } else {
                intentos--;
                System.out.println("Contraseña incorrecta. Intentos restantes: " + intentos);
            }
        }
        if (!accesoConcedido) {
            System.out.println("Has superado el número de intentos permitidos. Volviendo al Menú Principal...");
            return;
        }
        
        // Menú de opciones para el administrador.
        boolean salirSubMenu = false;
        while (!salirSubMenu) {
            System.out.println("\n¿Qué le gustaría revisar?");
            System.out.println("1. Reimprimir todas las ventas");
            System.out.println("2. Resumen del total de ventas");
            System.out.println("3. Volver al Menú Principal");
            int opcion = ValidarInput.validarNumero(scanner, "Ingrese la opción deseada:", 1, 3);
            if (opcion == -1) return;

            // Acceder a la lista global de ventas a través de la clase ModificarVenta
            List<ModificarVenta.Venta> ventas = ModificarVenta.getVentas();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- Reimpresión de Todas las Ventas ---");
                    if (ventas.isEmpty()) {
                        System.out.println("No hay ventas registradas.");
                    } else {
                        for (ModificarVenta.Venta v : ventas) {
                            ModificarVenta.imprimirVenta(v);
                        }
                    }
                    break;
                case 2:
                    // Inicializar variables para el resumen.
                    double totalVendido = 0;
                    double totalDescuentos = 0;
                    int totalBoletas = ventas.size();
                    int totalAsientosOcupados = 0;
                    int numNinos = 0;
                    int numAdultos = 0;
                    int numEstudiantes = 0;
                    int numTerceraEdad = 0;
                    
                    // Recorrer cada venta para acumular la información.
                    for (ModificarVenta.Venta v : ventas) {
                        String[] header = v.getHeader();
                        totalVendido += Double.parseDouble(header[3]);      // totalCompra.
                        totalDescuentos += Double.parseDouble(header[4]);     // totalDescuentos.
                        totalAsientosOcupados += v.getDetalle().length;
                        
                        for (String[] det : v.getDetalle()) {
                            int edad = Integer.parseInt(det[1]);  // edad del espectador.
                            if (edad < 12) {
                                numNinos++;
                            } else if (edad >= 65) {
                                numTerceraEdad++;
                            } else {
                                numAdultos++;
                            }
                            if (det[4].equalsIgnoreCase("SÍ")) {
                                numEstudiantes++;
                            }
                        }
                    }
                    
                    // Imprimir el resumen en un formato amigable.
                    System.out.println("\n--- Resumen del Total de Ventas ---");
                    System.out.println("Total de boletas emitidas: " + totalBoletas);
                    System.out.println("Total de asientos ocupados: " + totalAsientosOcupados);
                    System.out.printf("Monto total vendido: $%d%n", totalVendido);
                    System.out.printf("Monto total de descuentos: $%d%n", totalDescuentos);
                    System.out.println("Número de niños: " + numNinos);
                    System.out.println("Número de adultos: " + numAdultos);
                    System.out.println("Número de estudiantes: " + numEstudiantes);
                    System.out.println("Número de clientes de tercera edad: " + numTerceraEdad);
                    break;
                case 3:
                    salirSubMenu = true;
                    System.out.println("Volviendo al Menú Principal...");
                    break;
            }
        }
    }
}