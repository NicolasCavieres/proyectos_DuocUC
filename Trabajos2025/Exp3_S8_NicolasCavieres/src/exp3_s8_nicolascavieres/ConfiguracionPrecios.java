/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exp3_s8_nicolascavieres;

/**
 *
 * @author nikoc
 */

import java.util.Map;


public class ConfiguracionPrecios {
    // INSTANCIAS
    public static final Map<String, Double> listaPreciosBase = Map.of(
        "VIP", 20000.0,
        "Platea", 15000.0,
        "General", 10000.0
    );
    public static final Map<String, Double> listaDescuentos = Map.of(
        "Estudiante", 0.10,
        "Tercera Edad", 0.15,
        "General", 0.0
    );

    // METODOS
    public static double calcularPrecio(String codigoAsiento, TipoCliente cliente) {
        // Extraer el tipo de asiento desde el código
        String tipoAsiento = switch (codigoAsiento.charAt(0)) {
            case 'V' -> "VIP";
            case 'P' -> "Platea";
            case 'G' -> "General";
            default -> "General";
        };

        // Obtener el precio base y descuento desde los mapas
        double precioBase = obtenerPrecioBase(tipoAsiento);
        double descuento = obtenerDescuento(cliente.getTipoCliente());

        return precioBase * (1 - descuento);
    }   

    // GETTERS
    public static double obtenerPrecioBase(String tipoAsiento) {
        return listaPreciosBase.getOrDefault(tipoAsiento, 0.0);
    }

    public static double obtenerDescuento(String tipoCliente) {
        return listaDescuentos.getOrDefault(tipoCliente, 0.0);
    }
}

