/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exps2_s6_nicolascavieres;

/**
 *
 * @author nikoc
 */
public class VerPromociones {

    public static final double precioEntradaVip = 20000;
    public static final double precioEntradaPlatea = 15000;
    public static final double precioEntradaGeneral = 10000;
    private static double descuentoEstudiante = 0.20;
    private static double descuentoTerceraEdad = 0.30;

    public static void mostrar() {
        System.out.println("\n=== Promociones Disponibles ===");
        System.out.println("Precios base:");
        System.out.printf("VIP: $%.0f\n", precioEntradaVip);
        System.out.printf("Platea: $%.0f\n", precioEntradaPlatea);
        System.out.printf("General: $%.0f\n", precioEntradaGeneral);
        System.out.println("\nDescuentos por tipo de cliente:");
        System.out.println("Estudiantes: " + (int)(descuentoEstudiante * 100) + "%");
        System.out.println("Tercera Edad: " + (int)(descuentoTerceraEdad * 100) + "%");
        System.out.println("\nDescuentos por cantidad de entradas:");
        System.out.println("4 a 9 entradas: 5% de descuento adicional");
        System.out.println("10 a 14 entradas: 10% de descuento adicional");
        System.out.println("15 o más entradas: 20% de descuento adicional");
    }
}