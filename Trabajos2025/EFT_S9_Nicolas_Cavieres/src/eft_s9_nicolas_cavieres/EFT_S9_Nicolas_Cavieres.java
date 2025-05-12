/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eft_s9_nicolas_cavieres;

/**
 *
 * @author nikoc
 */
import java.util.Scanner;

public class EFT_S9_Nicolas_Cavieres {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Inicio del Programa
        System.out.println("=== Inicio del Programa ===\n");
        SaludosMoro.saludoInicial();
        
        // Preguntar sobre promociones con timeout (20 segundos, default "N")
        String promoInput = ValidarInput.validarLetraConTimeout(
                scanner, 
                20, 
                "¿Quieres saber de nuestras promociones? (S/N):", 
                "N", "S", "N"
                );

        
        if (promoInput != null && promoInput.equalsIgnoreCase("S")) {
            mostrarPromociones();
        } else {
            System.out.println("Continuando al Menú Principal...");
        }
        
        // Instanciar el objeto AsientosTeatro, que es compartido por el proceso
        AsientosTeatro asientos = new AsientosTeatro();
        
        // Bucle del Menú Principal
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Comprar entradas");
            System.out.println("2. Modificar una venta");
            System.out.println("3. Ver asientos disponibles");
            System.out.println("4. Ver Resumen de Tienda");
            System.out.println("5. Salir");
            
            int opcion = ValidarInput.validarNumero(scanner, "Ingrese la opción deseada:", 1, 5);
            if(opcion == -1) continue;
            
            switch(opcion) {
                case 1:
                    new IngresarVenta().realizarVenta(scanner, asientos);
                    break;
                case 2:
                    new ModificarVenta().modificarVenta(scanner, asientos);
                    break;
                case 3:
                    asientos.mostrarPlanoAsientos(asientos.getPlanoTeatro(), 
                                                  asientos.getAsientosVendidos(), 
                                                  asientos.getAsientosTemporales());
                    break;
                case 4:
                    new ResumenTienda().mostrarResumenTienda(scanner, asientos);
                    break;
                case 5:
                    salir = true;
                    break;
            }
        }
        SaludosMoro.mensajeDespedida();
        scanner.close();
    }
    
   
    /**
     * Muestra las promociones disponibles.
     */
    private static void mostrarPromociones() {
        System.out.println("\n================ PROMOCIONES DISPONIBLES ================");
        System.out.println("*  Niños           : " + (PreciosTeatro.DESCUENTO_NINOS * 100) + "% de descuento");
        System.out.println("*  Mujeres         : " + (PreciosTeatro.DESCUENTO_MUJERES * 100) + "% de descuento");
        System.out.println("*  Estudiantes     : " + (PreciosTeatro.DESCUENTO_ESTUDIANTES * 100) + "% de descuento");
        System.out.println("*  Tercera Edad    : " + (PreciosTeatro.DESCUENTO_TERCERA_EDAD * 100) + "% de descuento");
        System.out.println("=========================================================\n");
        System.out.println("Continuando al Menú Principal...");
    }
}

