/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.comiccollector;

/**
 *
 * @author ariel
 */
import com.comiccollector.io.GestorCSV;
import com.comiccollector.io.GestorTXT;
import com.comiccollector.manager.GestorComic;
import com.comiccollector.manager.GestorUsuario;
import com.comiccollector.control.TransaccionesComic;
import com.comiccollector.control.TransaccionesUsuario;
import com.comiccollector.menu.Menu;
import com.comiccollector.utils.Utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.List;
import com.comiccollector.modelo.Comics;

public class ComicApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            int maxId = 100;
            try (BufferedReader br = new BufferedReader(new FileReader("data/usuarios.txt"))) {
                br.readLine(); // Saltar encabezado
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split("\\|");
                    if (partes.length > 0) {
                        try {
                            int id = Integer.parseInt(partes[0]);
                            if (id > maxId) maxId = id;
                        } catch (NumberFormatException ignored) {}
                    }
                }
            } catch (java.io.FileNotFoundException e) {
                System.err.println("Archivo usuarios.txt no encontrado. Se iniciará con ID 101.");
            } catch (Exception e) {
                System.err.println("Error al leer usuarios.txt: " + e.getMessage());
            }
            Utils.setearIDUsuarioInicial(maxId + 1);

            GestorCSV gestorCSV = new GestorCSV();
            GestorTXT gestorTXT = new GestorTXT();
            GestorComic gestorComic = new GestorComic();
            GestorUsuario gestorUser = new GestorUsuario();

            // carga inicial
            try {
                List<Comics> listaComics = gestorCSV.leerComics();
                gestorComic.cargar(listaComics);
                gestorUser.cargar(gestorTXT.leerUsuarios(listaComics));
                // Sincronizar disponibilidad de cómics según usuarios comprados/reservados
                for (com.comiccollector.modelo.Usuarios usr : gestorUser.getUsuarios()) {
                    for (Comics comprado : usr.getComicsCompradosUsuario()) {
                        gestorComic.agregarComicComprado(comprado);
                    }
                    for (Comics reservado : usr.getComicsReservadosUsuario()) {
                        gestorComic.agregarComicReservado(reservado);
                    }
                }
                gestorComic.sincronizarListas();
                // Persistir cambios de disponibilidad al CSV
                try {
                    gestorCSV.escribirComics(gestorComic.listar());
                } catch (Exception e) {
                    System.err.println("Error al actualizar disponibilidad en CSV: " + e.getMessage());
                }
            } catch (Exception e) {
                System.err.println("Error al cargar comics: " + e.getMessage());
            }

            // transacciones y menú
            TransaccionesComic txComic = new TransaccionesComic(gestorComic, gestorUser, gestorCSV, gestorTXT);
            TransaccionesUsuario txUser = new TransaccionesUsuario(gestorUser, gestorTXT);
            Menu menu = new Menu(txComic, txUser);
            try {
                menu.mostrar();
            } catch (Exception e) {
                System.err.println("Error inesperado en el menú: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error fatal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
