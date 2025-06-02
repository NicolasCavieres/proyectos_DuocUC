/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bank_boston.banco;

/**
 * Gestiona la lista de clientes del sistema y operaciones asociadas.
 * @author ariel
 */
import java.util.ArrayList;

public class RegistroClientes {
    // Lista estática para mantener los clientes del sistema
    public static ArrayList<Clientes> lista_Clientes = new ArrayList<>();

    /**
     * Agrega un cliente a la lista de clientes.
     */
    public static void agregarCliente(Clientes cliente) {
        lista_Clientes.add(cliente);
    }

    /**
     * Busca un cliente por RUT (sin puntos ni guión).
     * @param rut RUT del cliente
     * @return Cliente encontrado o null si no existe
     */
    public static Clientes buscarClientePorRut(String rut) {
        for (Clientes c : lista_Clientes) {
            if (c.getRut().equalsIgnoreCase(rut)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Verifica si un cliente ya existe por su RUT.
     * @param rut RUT del cliente
     * @return true si existe, false si no
     */
    public static boolean existeCliente(String rut) {
        return buscarClientePorRut(rut) != null;
    }

    /**
     * Muestra todos los clientes registrados en consola.
     */
    public static void mostrarTodosLosClientes() {
        if (lista_Clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (Clientes c : lista_Clientes) {
                c.mostrarInformacion();
                c.mostrarSaldos();
                System.out.println("-----------------------");
            }
        }
    }
}
