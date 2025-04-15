/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package e4_s5_nicolascavieres;

/**
 *
 * @author nikoc
 */
import java.util.ArrayList;

public class BuscarEntrada {

    // Método 1: Buscar por código de asiento
    public static void buscarPorCodigo(String codigo, PlanoTeatro teatro) {
        if (teatro.getOcupados().contains(codigo)) {
            System.out.println("El asiento " + codigo + " está ocupado.");
        } else {
            System.out.println("El asiento " + codigo + " está disponible.");
        }
    }

    // Método 2: Mostrar solo los asientos ocupados o disponibles
    public static void mostrarPorEstado(PlanoTeatro teatro, boolean mostrarOcupados) {
        ArrayList<String> todos = teatro.getTodosAsientos();
        ArrayList<String> ocupados = teatro.getOcupados();

        System.out.println("=== Asientos " + (mostrarOcupados ? "ocupados" : "disponibles") + " ===");
        for (String asiento : todos) {
            boolean ocupado = ocupados.contains(asiento);
            if ((mostrarOcupados && ocupado) || (!mostrarOcupados && !ocupado)) {
                System.out.print(asiento + " ");
            }
        }
        System.out.println(); // línea nueva
    }
}

