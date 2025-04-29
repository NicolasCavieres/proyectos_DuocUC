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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Clase para gestionar las reservas de entradas con expiración automática
 * Almacena las reservas y maneja su ciclo de vida
 */
public class EntradasReservadas {
    private ArrayList<HashMap<String, Object>> listaReservas;
    private int reservaID = 1;
    private final Timer timer = new Timer(true); // Timer como daemon para que no impida la terminación del programa
    private PlanoTeatro teatro;  // Referencia al plano del teatro 

    /**
     * Agrega una nueva reserva al sistema y programa su expiración
     * @param reserva HashMap con los datos de la reserva:
     *               - nombre (String): Nombre del cliente
     *               - asientos (ArrayList<String>): Lista de asientos reservados
     *               - clientes (ArrayList<TipoCliente>): Lista de tipos de cliente
     *               - total (double): Monto total de la reserva
     */

    // Constructor de EntradasReservadas
    public EntradasReservadas(PlanoTeatro teatro) {
        if (teatro == null) {
            throw new IllegalArgumentException("El objeto PlanoTeatro no puede ser null");
        }
        this.teatro = teatro;  // Inicializamos la referencia al plano del teatro
        this.listaReservas = new ArrayList<>();
    }
    
    public void agregarReserva(HashMap<String, Object> reserva) {
        /*BREAKPOINT con cada Reserva creada, creamos un "id" que se almacena en
        reservaID y que es progresivo (1, 2 ,3 ...)
         Revisar linea 64*/
        reserva.put("id", reservaID);
        reserva.put("fechaCreacion", System.currentTimeMillis());
        
        // Agregar a la lista
        listaReservas.add(reserva);
        
        // Programar expiración después de 10 minutos (600,000 ms)
        timer.schedule(new ReservaTimerTask(reservaID), 600000);
        
        System.out.println("\n¡Reserva registrada exitosamente!");
        System.out.println("ID de Reserva: " + reservaID);
        System.out.println("Tienes 10 minutos para completar la compra antes de que expire.");
        System.out.println("Asientos reservados temporalmente: " + reserva.get("asientos"));
        
        // Aquí reservaID aumenta 1 dígito, despues de haber agregado la reserva a la base de datos
        reservaID++;
    }

    /**
     * Clase interna para manejar la expiración de reservas
     */
    private class ReservaTimerTask extends TimerTask {
        private final int idReserva;

        public ReservaTimerTask(int id) {
            this.idReserva = id;
        }

        @Override
        public void run() {
            HashMap<String, Object> reserva = buscarReserva(idReserva);
            if (reserva != null) {
                listaReservas.remove(reserva);
                System.out.println("\n[AVISO] La reserva ID " + idReserva + " ha expirado por tiempo.");
                System.out.println("Asientos liberados: " + reserva.get("asientos"));
            }
        }
    }

    /**
     * Busca una reserva por su ID
     * @param id ID de la reserva a buscar
     * @return HashMap con los datos de la reserva o null si no se encuentra
     */
    public HashMap<String, Object> buscarReserva(int id) {
        for (HashMap<String, Object> reserva : listaReservas) {
            if ((int) reserva.get("id") == id) {
                return reserva;
            }
        }
        return null;
    }

    /**
     * Confirma una reserva como venta
     * @param idReserva ID de la reserva a confirmar
     * @return Datos de la reserva convertidos en venta o null si no se encontró
     */
    public HashMap<String, Object> confirmarCompra(int idReserva) {
        HashMap<String, Object> reserva = buscarReserva(idReserva);
        if (reserva != null) {
            listaReservas.remove(reserva);
            System.out.println("Reserva ID " + idReserva + " confirmada como venta.");
            return reserva;
        }
        System.out.println("No se encontró la reserva con ID " + idReserva);
        return null;
    }

    /**
     * Cancela una reserva manualmente
     * @param idReserva ID de la reserva a cancelar
     */
    public void cancelarReserva(int idReserva) {
        HashMap<String, Object> reserva = buscarReserva(idReserva);
        if (reserva != null) {
            // Liberar los asientos de la reserva antes de eliminarla
            ArrayList<String> asientos = (ArrayList<String>) reserva.get("asientos");
            for (String asiento : asientos) {
            teatro.liberarAsiento(asiento);  // Liberar el asiento en el PlanoTeatro
            }
            
            listaReservas.remove(reserva);
            
            System.out.println("Reserva ID " + idReserva + " cancelada exitosamente.");
        } else {
            System.out.println("No se encontró la reserva con ID " + idReserva);
        }
    }

    /**
     * Imprime los detalles de una reserva
     * @param reserva Datos de la reserva a imprimir
     */
    public void imprimirReserva(HashMap<String, Object> reserva) {
        System.out.println("\n=== DETALLES RESERVA ===");
        System.out.println("ID: " + reserva.get("id"));
        System.out.println("Cliente: " + reserva.get("nombre"));
        
        @SuppressWarnings("unchecked")
        ArrayList<String> asientos = (ArrayList<String>) reserva.get("asientos");
        System.out.println("Asientos: " + String.join(", ", asientos));
        
        @SuppressWarnings("unchecked")
        ArrayList<TipoCliente> clientes = (ArrayList<TipoCliente>) reserva.get("clientes");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.printf("Asiento %s: %s (Edad: %d)\n",
                    asientos.get(i),
                    clientes.get(i).getTipoCliente(),
                    clientes.get(i).getEdadCliente());
        }
        
        System.out.printf("Total reserva: $%.0f\n", reserva.get("total"));
        System.out.println("=======================");
    }

    /**
     * Agrega un asiento a una reserva existente
     * @param scanner Para entrada de usuario
     * @param reserva Reserva a modificar
     * @param teatro Plano del teatro para validar disponibilidad
     */
    public void agregarAsiento(Scanner scanner, HashMap<String, Object> reserva, PlanoTeatro teatro) {
        teatro.mostrarTeatro();
        System.out.print("Ingrese código de asiento a agregar (ej: V2, P3): ");
        String codigo = scanner.nextLine().toUpperCase();

        // Validar formato
        
        if (!codigo.matches("^[VPG][1-8]$")) {
            System.out.println("Formato de asiento inválido.");
            return;
        }

        // Validar disponibilidad
        if (teatro.getOcupados().contains(codigo)) {
            System.out.println("Asiento no disponible.");
            return;
        }

        @SuppressWarnings("unchecked")
        ArrayList<String> asientos = (ArrayList<String>) reserva.get("asientos");
        if (asientos.contains(codigo)) {
            System.out.println("Este asiento ya está en la reserva.");
            return;
        }

        // Solicitar edad para descuentos
        System.out.print("Ingrese edad del nuevo espectador: ");
        int edad = ValidarInputUsuario.validarNumero(scanner, "Edad:", 1, 120);
        
        @SuppressWarnings("unchecked")
        ArrayList<TipoCliente> clientes = (ArrayList<TipoCliente>) reserva.get("clientes");
        
        // Agregar a la reserva
        asientos.add(codigo);
        clientes.add(new TipoCliente(edad));
        
        // Recalcular total
        double nuevoTotal = calcularTotalReserva(asientos, clientes);
        reserva.put("total", nuevoTotal);
        
        System.out.println("Asiento " + codigo + " agregado a la reserva.");
    }

    /**
     * Quita un asiento de una reserva existente
     * @param scanner Para entrada de usuario
     * @param reserva Reserva a modificar
     */
    public void quitarAsiento(Scanner scanner, HashMap<String, Object> reserva) {
        teatro.mostrarTeatro();
        @SuppressWarnings("unchecked")
        ArrayList<String> asientos = (ArrayList<String>) reserva.get("asientos");
        
        System.out.println("Asientos actuales: " + String.join(", ", asientos));
        System.out.print("Ingrese código de asiento a quitar: ");
        String codigo = scanner.nextLine().toUpperCase();

        if (!asientos.contains(codigo)) {
            System.out.println("El asiento no está en esta reserva.");
            return;
        }

        // Obtener índice para eliminar también el tipo de cliente correspondiente
        int index = asientos.indexOf(codigo);
        
        @SuppressWarnings("unchecked")
        ArrayList<TipoCliente> clientes = (ArrayList<TipoCliente>) reserva.get("clientes");
        
        // Eliminar
        asientos.remove(index);
        clientes.remove(index);
        
        // Recalcular total
        double nuevoTotal = calcularTotalReserva(asientos, clientes);
        reserva.put("total", nuevoTotal);
        
        System.out.println("Asiento " + codigo + " eliminado de la reserva.");
    }

    /**
     * Calcula el total de una reserva
     */
    private double calcularTotalReserva(ArrayList<String> asientos, ArrayList<TipoCliente> clientes) {
        double total = 0;
        for (int i = 0; i < asientos.size(); i++) {
            total += GestorEntradas.calcularPrecioEntrada(asientos.get(i), clientes.get(i));
        }
        return GestorEntradas.aplicarDescuentoCantidad(asientos.size(), total);
    }

    /**
     * Obtiene la lista completa de reservas
     * @return 
     */
    public ArrayList<HashMap<String, Object>> getListaReservas() {
        return new ArrayList<>(listaReservas); // Devuelve copia para evitar modificaciones externas
    }
    
    public void mostrarResumenReserva(HashMap<String, Object> reserva) {
        System.out.println("\n========== RESERVA CONFIRMADA ==========");
        System.out.println("ID: " + reserva.get("id"));
        System.out.println("Cliente: " + reserva.get("nombre"));
        System.out.println("Asientos: " + reserva.get("asientos"));
        System.out.printf("Total: $%.0f\n", reserva.get("total"));
        System.out.println("Tienes 10 minutos para confirmar la compra");
        System.out.println("===========================================");
    }    
}