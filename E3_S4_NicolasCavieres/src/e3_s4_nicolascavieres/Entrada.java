/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package e3_s4_nicolascavieres;

/**
 *
 * @author nikoc
 */
public class Entrada {
    private String tipoDescuento; // "estudiante", "general" o "tercera edad"
    private double descuentoAplicado;
    private int edadUsuario;

    // Constructor
    public Entrada(double precioBase, int edadUsuario) {
        this.edadUsuario = edadUsuario;
        this.tipoDescuento = determinarTipoDescuento();
        this.descuentoAplicado = calcularDescuento();
    }

    // Método para determinar el tipo de descuento según la edad
    private String determinarTipoDescuento() {
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
        return switch (tipoDescuento) {
            case "tercera edad" -> 0.15; // 15% de descuento
            case "estudiante" -> 0.10; // 10% de descuento
            default -> 0.0; // Sin descuento
        };       
    }

    // Getters para acceder a la información
    public String getTipoDescuento() {
        return tipoDescuento;
    }

    public double getDescuentoAplicado() {
        return descuentoAplicado * 100; // Para mostrar en porcentaje
    }

    public int getEdadUsuario() {
        return edadUsuario;
    }
}

