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
import java.text.SimpleDateFormat;

/**
 * Clase para mostrar resúmenes de las ventas
 */
public class GestorDeVentas {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Muestra un resumen general de todas las ventas registradas
     * @param ventas Lista de ventas confirmadas
     */
    public void resumenVentas(ArrayList<HashMap<String, Object>> ventas) {
        System.out.println("\n========== RESUMEN DE VENTAS ==========");
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            for (HashMap<String, Object> venta : ventas) {
                int id = (int) venta.get("ventaID");
                String nombre = (String) venta.get("nombre");
                double total = (double) venta.get("total");
                long fechaMillis = (long) venta.get("fechaVenta");
                String fechaFormateada = dateFormat.format(fechaMillis);
                ArrayList<String> asientos = (ArrayList<String>) venta.get("asientos");

                // Obtener la lista de clientes y el descuento
                ArrayList<TipoCliente> clientes = (ArrayList<TipoCliente>) venta.get("clientes");
                String descuento = "N/A";  // Valor por defecto si no hay descuento

                // Si la lista de clientes no está vacía, obtener el descuento de cada cliente
                if (!clientes.isEmpty()) {
                    TipoCliente cliente = clientes.get(0);  // En este ejemplo, solo hay un cliente por venta
                    descuento = String.format("%.0f", cliente.determinarDescuento() * 100) + "%";  // Convertir a porcentaje
                }

                // Mostrar detalles de cada venta
                System.out.println("----------------------------------------");
                System.out.println("ID Venta:   " + id);
                System.out.println("Cliente:    " + nombre);
                System.out.println("Fecha:      " + fechaFormateada);
                System.out.println("Total:      $" + (int) total);
            }
        }
    }
}
