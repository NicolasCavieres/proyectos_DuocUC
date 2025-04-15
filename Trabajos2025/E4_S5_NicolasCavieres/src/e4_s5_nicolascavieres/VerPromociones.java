/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package e4_s5_nicolascavieres;

/**
 *
 * @author nikoc
 */
public class VerPromociones {

    public static final double precioEntradaVip = 20000;
    public static final double precioEntradaPlatea = 15000;
    public static final double precioEntradaGeneral = 10000;

    public static void mostrar() {
        System.out.println("\n=== Promociones Disponibles ===");
        System.out.println("Precios base:");
        System.out.printf("VIP: $%.0f\n", precioEntradaVip);
        System.out.printf("Platea: $%.0f\n", precioEntradaPlatea);
        System.out.printf("General: $%.0f\n", precioEntradaGeneral);
        System.out.println("\nDescuentos por tipo de cliente:");
        System.out.println("Estudiantes: 10% de descuento");
        System.out.println("Tercera edad (60+): 15% de descuento");
        System.out.println("\nDescuentos por cantidad de entradas:");
        System.out.println("4 a 9 entradas: 5% de descuento adicional");
        System.out.println("10 a 14 entradas: 10% de descuento adicional");
        System.out.println("15 o más entradas: 20% de descuento adicional");
    }
}

