/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package e3_s4_nicolascavieres;

/**
 *
 * @author nikoc
 */
import java.util.ArrayList;
import java.util.List;

public class Teatro {
    private List<String> asientosDisponibles;
    private List<String> asientosOcupados;

    // Constructor que inicializa los asientos disponibles
    public Teatro() {
        this.asientosDisponibles = new ArrayList<>();
        this.asientosOcupados = new ArrayList<>();
        inicializarAsientos();
    }

    // Método para inicializar los asientos disponibles
    private void inicializarAsientos() {
        for (char zona = 'A'; zona <= 'C'; zona++) {
            for (int i = 1; i <= 10; i++) {
                asientosDisponibles.add(zona + String.valueOf(i));
            }
        }
    }

    // Método para mostrar el plano del teatro
    public void mostrarPlano() {
        System.out.println("\nPlano de asientos:");
        for (char zona = 'A'; zona <= 'C'; zona++) {
            System.out.print(zona + ": ");
            for (int i = 1; i <= 10; i++) {
                String asiento = zona + String.valueOf(i);
                if (asientosDisponibles.contains(asiento)) {
                    System.out.print("[" + asiento + "]");
                } else {
                    System.out.print("[XX]");
                }
            }
            System.out.println(); // Salto de línea por fila
        }
    }

    // Método para verificar si un asiento está disponible
    public boolean asientoDisponible(String asiento) {
        return asientosDisponibles.contains(asiento);
    }

    // Método para reservar un asiento
    public boolean reservarAsiento(String asiento) {
        if (asientoDisponible(asiento)) {
            asientosDisponibles.remove(asiento);
            asientosOcupados.add(asiento);
            return true;
        } else {
            return false;
        }
    }

    // Método para obtener la lista de asientos ocupados
    public List<String> getAsientosOcupados() {
        return asientosOcupados;
    }
}

