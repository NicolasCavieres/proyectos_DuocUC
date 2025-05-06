/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exp3_s8_nicolascavieres;

/**
 *
 * @author nikoc
 */
public class TipoCliente {
    private final String tipoCliente;
    private final int edadCliente;

    public TipoCliente(int edadCliente) {
        this.edadCliente = edadCliente;
        this.tipoCliente = determinarTipoCliente();
    }        

    public String determinarTipoCliente() {
        if (edadCliente < 18) return "Estudiante";
        else if (edadCliente >= 60) return "Tercera Edad";
        else return "General";
    }

    public double getDescuento() {
        return ConfiguracionPrecios.obtenerDescuento(tipoCliente);
    }

    public double getPorcentajeDescuento() {
        return getDescuento() * 100;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public int getEdadCliente() {
        return edadCliente;
    }
}
