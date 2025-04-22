/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exps2_s6_nicolascavieres;

/**
 *
 * @author nikoc
 */
public class VariablesEstadisticas {
    
    // Cuenta cuántas veces se ingresó al Menu Principal
    public static int cantidadDeInteracciones = 0;
    // VariablesEstadisticas.cantidadDeInteracciones++;

    // Cuántas reservas fueron compradas (reservas que pasaron a ventas)
    public static int totalReservasCompradas = 0;
    // VariablesEstadisticas.totalReservasCompradas++;
    
    // Suma total de reservas y ventas que fueron canceladas
    public static int totalVentasReservasCanceladas = 0;
    // VariablesEstadisticas.totalVentasReservasCanceladas++;
    
    
    public static void mostrarResumenFinal(){
        System.out.println("\nResumen del sistema:");
        System.out.println("Interacciones con el menú principal: " + VariablesEstadisticas.cantidadDeInteracciones);
        System.out.println("Reservas que fueron compradas: " + VariablesEstadisticas.totalReservasCompradas);
        System.out.println("Total de cancelaciones (reservas + ventas): " + VariablesEstadisticas.totalVentasReservasCanceladas);
    }
}
