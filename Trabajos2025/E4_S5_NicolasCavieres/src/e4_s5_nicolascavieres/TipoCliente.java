/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package e4_s5_nicolascavieres;

/**
 *
 * @author nikoc
 */
public class TipoCliente {
    private final String categoriaCliente; // "estudiante", "general" o "tercera edad"
    private final double cantidadDescuento;
    private final int edadUsuario;

    // Constructor
    public TipoCliente(int edadUsuario) {
        this.edadUsuario = edadUsuario;
        this.categoriaCliente = determinarTipoCliente();
        this.cantidadDescuento = calcularDescuento();
    }
    
    // Método que pide la edad del usuario

    // Método para determinar el tipo de descuento según la edad
    private String determinarTipoCliente() {
        if (edadUsuario >= 60) { // Desde 60 en adelante
            return "tercera edad";
        } else if (edadUsuario < 18) { // Menores de 18
            return "estudiante";
        } else {
            return "general";
        }
    }

    // Método para calcular el descuento
    private double calcularDescuento() {
        return switch (categoriaCliente) {
            case "tercera edad" -> 0.15; // 15% de descuento
            case "estudiante" -> 0.10; // 10% de descuento
            default -> 0.0; // Sin descuento
        };       
    }

// Getters para acceder a la información
    // Tipo de Cliente (estudiante, tercera edad o general)
    public String getCategoriaCliente() {
        return categoriaCliente;
    }

    // Multiplicador para el descuento
    public double getCantidadDescuento() {
        return cantidadDescuento;
    }
    
    // Para mostrar en porcentaje
    public double getPorcentajeDescuento() {
        return cantidadDescuento * 100;
    }

    // Para acceder a la edad del usuario
    public int getEdadUsuario() {
        return edadUsuario;
    }
}

