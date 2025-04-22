/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exps2_s6_nicolascavieres;

/**
 *
 * @author nikoc
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase para gestionar las ventas confirmadas del sistema
 * Registra ventas, genera boletas y maneja modificaciones
 */
public class EntradasVendidas {
    private final ArrayList<HashMap<String, Object>> listaVentas = new ArrayList<>();
    private int ventaID = 1;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Registra una nueva venta en el sistema
     * @param venta Datos de la venta:
     *              - nombre (String): Nombre del cliente
     *              - asientos (ArrayList<String>): Lista de asientos vendidos
     *              - clientes (ArrayList<TipoCliente>): Lista de tipos de cliente
     *              - total (double): Monto total de la venta
     */
    public void registrarVenta(HashMap<String, Object> venta, PlanoTeatro teatro) {
        // Asignar ID único y fecha/hora de venta
        venta.put("ventaID", ventaID);
        venta.put("fechaVenta", System.currentTimeMillis());

        listaVentas.add(venta);

        // Marcar asientos como ocupados
        @SuppressWarnings("unchecked")
        ArrayList<String> asientosVendidos = (ArrayList<String>) venta.get("asientos");
        for (String asiento : asientosVendidos) {
            teatro.ocuparAsiento(asiento);
        }

        System.out.println("\n=== Venta Registrada ===");
        System.out.println("ID Venta: " + ventaID);
        System.out.println("Cliente: " + venta.get("nombre"));
        System.out.println("Total: $" + venta.get("total"));
        System.out.println("=========================");

        ventaID++;
    }

    /**
     * Busca una venta por su ID
     * @param id ID de la venta a buscar
     * @return HashMap con los datos de la venta o null si no se encuentra
     */
    public HashMap<String, Object> buscarVenta(int id) {
        for (HashMap<String, Object> venta : listaVentas) {
            if ((int) venta.get("ventaID") == id) {
                return venta;
            }
        }
        return null;
    }

    /**
     * Imprime la boleta de una venta específica
     * @param id ID de la venta a imprimir
     */
public void imprimirBoleta(int id) {
    /* BREAKPOINT -> venta tomará el valor de id que buscamos para imprimir 
    */    
    HashMap<String, Object> venta = buscarVenta(id);
    if (venta != null) {
        // Título de la boleta
        System.out.println("\n================== BOLETA TEATRO MORO ==================");
        System.out.println("Teatro Moro - Compra Confirmada");
        System.out.println("ID Venta: " + venta.get("ventaID"));
        System.out.println("Cliente: " + venta.get("nombre"));

        // Mostrar tipo de cliente y asientos
        ArrayList<String> asientos = (ArrayList<String>) venta.get("asientos");
        ArrayList<TipoCliente> clientes = (ArrayList<TipoCliente>) venta.get("clientes");
        System.out.println("\nDetalles de las entradas:");
        
        // Contador de entradas
        int totalEntradas = 0;
        // BREAKPOINT -> totalEntradas tomará el valor de el numero de asientos
        totalEntradas = asientos.size();

        // Mostrar información de cada entrada
        for (int i = 0; i < totalEntradas; i++) {
            /* BREAKPOINT -> el valor de asiento cambiara con cada iteración, 
            a medida que cambie el índice */
            String asiento = asientos.get(i);
            /* BREAKPOINT -> el tipo de cliente cambiara con cada iteración, 
            a medida que cambie el índice */            
            TipoCliente cliente = clientes.get(i);
            System.out.printf("Asiento: %s - Tipo: %s - Edad: %d - Precio: $%.0f\n", 
                               asiento, cliente.getTipoCliente(), cliente.getEdadCliente(),
                               GestorEntradas.calcularPrecioEntrada(asiento, cliente));  // Calculando el precio
        }

        // Mostrar número total de entradas
        System.out.println("\nNúmero total de entradas:     " + totalEntradas);

        // Mostrar total de la compra
        System.out.println("TOTAL:                   $" + ((Double) venta.get("total")).intValue());
        System.out.println("=========================================================");
    } else {
        System.out.println("Venta no encontrada.");
    }
}

    /**
     * Agrega un asiento a una venta existente
     * @param idVenta ID de la venta a modificar
     * @param codigoAsiento Código del asiento a agregar (ej: V2, P3)
     * @param teatro Referencia al plano del teatro para validar disponibilidad
     * @param scanner Para entrada de usuario
     * @return true si se agregó exitosamente
     */
    public boolean agregarAsiento(int idVenta, String codigoAsiento, PlanoTeatro teatro, Scanner scanner) {
        HashMap<String, Object> venta = buscarVenta(idVenta);
        if (venta == null) {
            System.out.println("Venta no encontrada.");
            return false;
        }

        // Validar formato
        if (!codigoAsiento.matches("^[VPG][1-8]$")) {
            System.out.println("Formato de asiento inválido.");
            return false;
        }

        // Validar disponibilidad
        if (teatro.getOcupados().contains(codigoAsiento)) {
            System.out.println("Asiento no disponible.");
            return false;
        }

        @SuppressWarnings("unchecked")
        ArrayList<String> asientos = (ArrayList<String>) venta.get("asientos");
        if (asientos.contains(codigoAsiento)) {
            System.out.println("Este asiento ya está en la venta.");
            return false;
        }

        // Solicitar edad para descuentos
        System.out.print("Ingrese edad del nuevo espectador: ");
        int edad = ValidarInputUsuario.validarNumero(scanner, "Edad:", 1, 120);
        if (edad == -1) return false;

        @SuppressWarnings("unchecked")
        ArrayList<TipoCliente> clientes = (ArrayList<TipoCliente>) venta.get("clientes");
        
        // Agregar a la venta
        asientos.add(codigoAsiento);
        clientes.add(new TipoCliente(edad));
        
        // Recalcular total
        double nuevoTotal = calcularTotalVenta(asientos, clientes);
        venta.put("total", nuevoTotal);
        
        // Marcar asiento como ocupado
        teatro.ocuparAsiento(codigoAsiento);
        
        System.out.println("Asiento " + codigoAsiento + " agregado a la venta.");
        return true;
    }

    /**
     * Elimina un asiento de una venta existente
     * @param idVenta ID de la venta a modificar
     * @param codigoAsiento Código del asiento a quitar
     * @param teatro Referencia al plano del teatro para liberar asiento
     * @return true si se eliminó exitosamente
     */
    public boolean quitarAsiento(int idVenta, String codigoAsiento, PlanoTeatro teatro) {
        HashMap<String, Object> venta = buscarVenta(idVenta);
        if (venta == null) {
            System.out.println("Venta no encontrada.");
            return false;
        }

        @SuppressWarnings("unchecked")
        ArrayList<String> asientos = (ArrayList<String>) venta.get("asientos");
        if (!asientos.contains(codigoAsiento)) {
            System.out.println("El asiento no está en esta venta.");
            return false;
        }

        // Obtener índice para eliminar también el tipo de cliente correspondiente
        int index = asientos.indexOf(codigoAsiento);
        
        @SuppressWarnings("unchecked")
        ArrayList<TipoCliente> clientes = (ArrayList<TipoCliente>) venta.get("clientes");
        
        // Eliminar
        asientos.remove(index);
        clientes.remove(index);
        
        // Recalcular total
        double nuevoTotal = calcularTotalVenta(asientos, clientes);
        venta.put("total", nuevoTotal);
        
        // Liberar asiento
        teatro.liberarAsiento(codigoAsiento);
        
        System.out.println("Asiento " + codigoAsiento + " eliminado de la venta.");
        return true;
    }

    /**
     * Cancela una venta completa
     * @param idVenta ID de la venta a cancelar
     * @param teatro Referencia al plano del teatro para liberar asientos
     */
public void cancelarVenta(int idVenta, PlanoTeatro teatro) {
    HashMap<String, Object> venta = buscarVenta(idVenta);
    
    if (venta != null) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\nSe hará nula su compra ID " + idVenta + ".");
        System.out.println("El encargado gestionará la devolución de su dinero \ny la reserva de asientos quedará sin efecto.");
        System.out.print("¿Desea continuar? (S/N): ");
        String respuesta = scanner.nextLine().trim().toUpperCase();
        
        if (respuesta.equals("S")) {
            // Liberar los asientos
            @SuppressWarnings("unchecked")
            ArrayList<String> asientos = (ArrayList<String>) venta.get("asientos");
            asientos.forEach(teatro::liberarAsiento);
            
            // Eliminar venta
            listaVentas.remove(venta);
            System.out.println("\nSu compra ID " + idVenta + " ha sido anulada exitosamente.");
        } else {
            System.out.println("Volviendo al menú principal...");
        }
    } else {
        System.out.println("No se encontró la venta con ID " + idVenta);
    }
}

    /**
     * Calcula el total de una venta
     */
    private double calcularTotalVenta(ArrayList<String> asientos, ArrayList<TipoCliente> clientes) {
        double total = 0;
        for (int i = 0; i < asientos.size(); i++) {
            total += GestorEntradas.calcularPrecioEntrada(asientos.get(i), clientes.get(i));
        }
        return GestorEntradas.aplicarDescuentoCantidad(asientos.size(), total);
    }

    /**
     * Obtiene la lista completa de ventas
     */
    public ArrayList<HashMap<String, Object>> getListaVentas() {
        return new ArrayList<>(listaVentas); // Devuelve copia para evitar modificaciones externas
    }
}