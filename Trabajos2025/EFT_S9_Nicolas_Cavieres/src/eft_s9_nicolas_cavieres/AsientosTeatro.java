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

public class AsientosTeatro {
    
    // ==================== PLANO DEL TEATRO ====================
    private final String[][] planoTeatro = {
        {"V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8"},
        {"P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8"},
        {"PB1", "PB2", "PB3", "PB4", "PB5", "PB6", "PB7", "PB8"},
        {"PA1", "PA2", "PA3", "PA4", "PA5", "PA6", "PA7", "PA8"},
        {"G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8"}
     };

    
    // ==================== INSTANCIAS ====================
    private final List<String> asientosVendidos   = new ArrayList<>();
    private final List<String> asientosTemporales   = new ArrayList<>();    

    
    // ==================== METODOS ====================

    /**
     * Muestra el plano del teatro en consola.
     * Se muestran:
     *   - [X] para asientos vendidos (ocupados),
     *   - [R] para asientos ocupados temporalmente,
     *   - [código] para asientos disponibles.
     *
     * @param teatro     El arreglo 2D que representa el plano del teatro.
     * @param vendidos   Lista con los identificadores de los asientos vendidos.
     * @param temporales Lista con los identificadores de los asientos ocupados temporalmente.
     */
    public void mostrarPlanoAsientos(String[][] teatro, 
                                List<String> vendidos, 
                                List<String> temporales) {
        System.out.println("\n----------- Plano del Teatro -----------");
        String[] secciones = {  " VIP          : ", 
                                " Palco        : ", 
                                " Platea Baja  : ", 
                                " Platea Alta  : ", 
                                " Galería      : " };

        for (int i = 0; i < teatro.length; i++) {
            System.out.print(secciones[i]);
            for (String asiento : teatro[i]) {
                String codigo = asiento.toUpperCase();
                if (vendidos.contains(codigo)) {
                    System.out.print("[X] ");
                } else if (temporales.contains(codigo)) {
                    System.out.print("[R] ");
                } else {
                    System.out.print("[" + codigo + "] ");
                }
            }
            System.out.println(); // Nueva línea para la siguiente sección
        }
        System.out.println("----------------------------------------");
        System.out.println("Leyenda: [X] Ocupado | [R] Ocupado Temporalmente | [ ] Disponible\n");
    }
    
    /**
     * Reserva un asiento de forma temporal (durante el proceso de compra).
     *
     * @param idAsiento El identificador del asiento.
     * @return true si se pudo reservar temporalmente; false en caso contrario.
     */
    public boolean reservarAsientoTemporal(String asiento) {
        // Conviertes la entrada a mayúsculas
        String codigo = asiento.toUpperCase();
        if (!asientosVendidos.contains(codigo) && !asientosTemporales.contains(codigo)) {
            asientosTemporales.add(codigo);
            return true;
        }
        System.out.println("El asiento " + codigo + " no está disponible.");
        return false;
    }
    
    /**
     * Confirma la venta de un asiento, removiéndolo del estado temporal y agregándolo a vendidos.
     *
     * @param idAsiento El identificador del asiento.
     * @return true si la venta se confirmó; false en caso de error o que el asiento ya esté vendido.
     */
    public void venderAsiento(String asiento) {
        String codigo = asiento.toUpperCase();
        // Eliminar de temporales (si estuviera allí)
        // Agregar a la lista de vendidos si aún no está
        if (!asientosVendidos.contains(codigo)) {
            asientosTemporales.remove(codigo);
            asientosVendidos.add(codigo);
        }
    }
    
    /**
     * Libera un asiento que se encuentre en estado temporal.
     *
     * @param idAsiento El identificador del asiento.
     * @return true si se liberó el asiento; false si no estaba ocupado temporalmente.
     */
    public boolean liberarAsiento(String idAsiento) {
        if (asientosTemporales.contains(idAsiento)){
            asientosTemporales.remove(idAsiento);
            return true;
        } else return false;
    }
    
    /**
     * Cambia el input del usuario a mayúsculas.
     *
     * @param asiento El texto ingresado.
     * @return El texto en mayúsculas.
     */
    public static String normalizarAsiento(String asiento) {
        return asiento == null ? null : asiento.toUpperCase();
    }    
    
    // ==================== GETTERS ====================
    public String[][] getPlanoTeatro() {
        return planoTeatro;
    }
    public List<String> getAsientosVendidos() {
        return asientosVendidos;
    }
    public List<String> getAsientosTemporales() {
        return asientosTemporales;
    }
}