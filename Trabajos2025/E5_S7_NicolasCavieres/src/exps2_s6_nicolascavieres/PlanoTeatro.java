/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exps2_s6_nicolascavieres;

/**
 *
 * @author nikoc
 */

import java.util.ArrayList;

public class PlanoTeatro {
    private final String[][] teatro = {
        {"V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8"},
        {"P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8"},
        {"G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8"}
    };

    private final ArrayList<String> ocupados = new ArrayList<>();

    public void mostrarTeatro() {
        System.out.println("----------- Plano del Teatro -----------");

        String[] secciones = {" VIP    : ", " Platea : ", " General: "};

        for (int fila = 0; fila < teatro.length; fila++) {
            System.out.print(secciones[fila]);
            for (String asiento : teatro[fila]) {
                if (ocupados.contains(asiento)) {
                    System.out.print("[X] ");
                } else {
                    System.out.print("[" + asiento + "] ");
                }
            }
            System.out.println();
        }
        System.out.println("----------------------------------------");       
    }

    public boolean ocuparAsiento(String codigo) {
        if (!ocupados.contains(codigo)) {
            ocupados.add(codigo);
            return true;
        } else {
            System.out.println("Asiento ya ocupado.");
            return false;
        }
    }

    public boolean liberarAsiento(String codigo) {
        if (ocupados.contains(codigo)) {
            ocupados.remove(codigo);
            return true;
        } else {
            System.out.println("El asiento no ha sido ocupado.");
            return false;
        }
    }

    public ArrayList<String> getOcupados() {
        return ocupados;
    }

    public ArrayList<String> getTodosAsientos() {
        ArrayList<String> todos = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            todos.add("V" + i);
            todos.add("P" + i);
            todos.add("G" + i);
        }
        return todos;
    }
}
