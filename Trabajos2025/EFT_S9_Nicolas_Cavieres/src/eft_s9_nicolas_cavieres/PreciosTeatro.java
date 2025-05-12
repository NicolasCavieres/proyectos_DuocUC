/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eft_s9_nicolas_cavieres;

/**
 *
 * @author nikoc
 */

public class PreciosTeatro {

    // Precios base según la sección del teatro.
    public static final int PRECIO_VIP = 30000;
    public static final int PRECIO_PALCO = 25000;
    public static final int PRECIO_PLATEA_BAJA = 20000;
    public static final int PRECIO_PLATEA_ALTA = 15000;
    public static final int PRECIO_GALERIA = 10000;

    // Porcentajes de descuento según el tipo de cliente.
    public static final double DESCUENTO_NINOS = 0.10;        // 10%
    public static final double DESCUENTO_MUJERES = 0.20;       // 20%
    public static final double DESCUENTO_ESTUDIANTES = 0.15;   // 15%
    public static final double DESCUENTO_TERCERA_EDAD = 0.25;  // 25%

    /**
     * Devuelve el precio base de la entrada según la sección del teatro.
     *
     * @param seccion La sección, esperada como "vip", "palco",
     *                "platea baja", "platea alta" o "galeria" (sin distinción de mayúsculas).
     * @return El precio base de la entrada. Si la sección no es válida, se retorna 0.
     */
    public static double obtenerPrecioBase(String asiento) {
        String seccion = "";
        String asientoUpper = asiento.toUpperCase();

        if (asientoUpper.startsWith("V")) {
            seccion = "vip";
        } else if (asientoUpper.startsWith("PB")) {
            seccion = "platea baja";
        } else if (asientoUpper.startsWith("PA")) {
            seccion = "platea alta";
        } else if (asientoUpper.startsWith("G")) {
            seccion = "galeria";
        } else if (asientoUpper.startsWith("P")) { // Palco
            seccion = "palco";
        }        
        
        if (seccion.equalsIgnoreCase("vip")) {
            return PRECIO_VIP;
        } else if (seccion.equalsIgnoreCase("palco")) {
            return PRECIO_PALCO;
        } else if (seccion.equalsIgnoreCase("platea baja")) {
            return PRECIO_PLATEA_BAJA;
        } else if (seccion.equalsIgnoreCase("platea alta")) {
            return PRECIO_PLATEA_ALTA;
        } else if (seccion.equalsIgnoreCase("galeria")) {
            return PRECIO_GALERIA;
        } else {
            System.out.println("Sección no válida. Se retornará precio 0.");
            return 0;
        }
    }

    /**
     * Calcula el precio final de forma acumulativa, sumando todos los descuentos que se apliquen.
     * 
     * @param tiposDescuento Un arreglo de strings con los tipos de descuento a aplicar. 
     *        Por ejemplo: {"niño", "mujer", "estudiante"}.
     * @param precioBase     El precio original de la entrada.
     * @return El precio final con todos los descuentos acumulados.
     */
    public static double calcularPrecioFinalAcumulativo(String[] tiposDescuento, double precioBase) {
        double descuentoAcumulado = 0.0;
        for (String tipo : tiposDescuento) {
            if (tipo.equalsIgnoreCase("niño") || tipo.equalsIgnoreCase("niña")) {
                descuentoAcumulado += DESCUENTO_NINOS;
            } 
            if (tipo.equalsIgnoreCase("mujer")) {
                descuentoAcumulado += DESCUENTO_MUJERES;
            } 
            if (tipo.equalsIgnoreCase("estudiante")) {
                descuentoAcumulado += DESCUENTO_ESTUDIANTES;
            }
            if (tipo.equalsIgnoreCase("tercera edad")) {
                descuentoAcumulado += DESCUENTO_TERCERA_EDAD;
            }
        }
        // Aseguramos que el descuento total no sobrepase el 100%
        if (descuentoAcumulado > 1.0) {
            descuentoAcumulado = 1.0;
        }
        return precioBase * (1 - descuentoAcumulado);
    }
}