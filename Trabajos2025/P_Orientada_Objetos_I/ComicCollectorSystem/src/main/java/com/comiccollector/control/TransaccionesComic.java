/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.control;

/**
 *
 * @author ariel
 */
import com.comiccollector.manager.GestorComic;
import com.comiccollector.manager.GestorUsuario;
import com.comiccollector.modelo.Comics;
import com.comiccollector.modelo.Usuarios;
import com.comiccollector.modelo.Compras;
import com.comiccollector.modelo.Reservas;
import com.comiccollector.utils.Utils;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Iterator;
import com.comiccollector.io.GestorCSV;
import com.comiccollector.io.GestorTXT;

public class TransaccionesComic {
    private GestorComic gestorComic;
    private GestorUsuario gestorUsuario;
    private GestorCSV gestorCSV;
    private GestorTXT gestorTXT;
    private Set<Compras> compras       = new HashSet<>();
    private SortedSet<Reservas> reservas = new TreeSet<>();
    private final Scanner sc = new Scanner(System.in);

    public TransaccionesComic(GestorComic gc, GestorUsuario gu, GestorCSV gestorCSV, GestorTXT gestorTXT) {
        this.gestorComic = gc;
        this.gestorUsuario = gu;
        this.gestorCSV = gestorCSV;
        this.gestorTXT = gestorTXT;
    }

    public void comprarComic(Usuarios u, Comics c) {
        if (u == null || c == null || !c.estaDisponible()) {
            System.out.println("No se puede completar la compra.");
            return;
        }
        // Eliminar de reservados si estaba reservado
        u.quitarReserva(c);
        gestorComic.getTodosComicsReservados().remove(c);
        // Añadir a comprados si no está
        if (!u.getComicsCompradosUsuario().contains(c)) {
            u.asignarCompra(c);
        }
        if (!gestorComic.getTodosComicsComprados().contains(c)) {
            gestorComic.agregarComicComprado(c);
        }
        c.setDisponible(false);
        // Actualizar CSV
        try {
            gestorCSV.actualizarLineaComic(c);
        } catch (Exception ex) {
            System.err.println("Error al actualizar CSV: " + ex.getMessage());
        }
        // Persistir cambios del usuario
        try {
            gestorTXT.actualizarLineaUsuario(u);
        } catch (Exception ex) {
            System.err.println("Error al actualizar TXT: " + ex.getMessage());
        }
        Compras cp = new Compras(
            Utils.generarIDCompra(),
            java.time.LocalDate.now(),
            java.time.LocalTime.now(),
            c.getPrecio(),
            u
        );
        compras.add(cp);
        System.out.println("Compra registrada: " + cp.getIdCompra());
        // Persistir cambios del usuario
        try {
            gestorTXT.actualizarLineaUsuario(u);
        } catch (Exception ex) {
            System.err.println("Error al actualizar TXT: " + ex.getMessage());
        }
    }

    public void reservarComic(Usuarios u, Comics c) {
        if (u == null || c == null || !c.estaDisponible()) {
            System.out.println("No se puede reservar el cómic.");
            return;
        }
        // Eliminar de comprados si estaba comprado
        u.quitarCompra(c);
        gestorComic.getTodosComicsComprados().remove(c);
        // Añadir a reservados si no está
        if (!u.getComicsReservadosUsuario().contains(c)) {
            u.asignarReserva(c);
        }
        if (!gestorComic.getTodosComicsReservados().contains(c)) {
            gestorComic.agregarComicReservado(c);
        }
        c.setDisponible(false);
        // Actualizar CSV
        try {
            gestorCSV.actualizarLineaComic(c);
        } catch (Exception ex) {
            System.err.println("Error al actualizar CSV: " + ex.getMessage());
        }
        // Persistir cambios del usuario
        try {
            gestorTXT.actualizarLineaUsuario(u);
        } catch (Exception ex) {
            System.err.println("Error al actualizar TXT: " + ex.getMessage());
        }
        Reservas r = new Reservas(
            java.time.LocalDate.now(),
            java.time.LocalTime.now(),
            c.getTitulo(),
            c.getAutor(),
            u
        );
        reservas.add(r);
        System.out.println("Reserva registrada.");
        // Persistir cambios del usuario
        try {
            gestorTXT.actualizarLineaUsuario(u);
        } catch (Exception ex) {
            System.err.println("Error al actualizar TXT: " + ex.getMessage());
        }
    }

    public void anularReservaComic() {
        System.out.print("ID usuario: ");
        int uid = sc.nextInt(); sc.nextLine();
        System.out.print("Título a anular reserva: ");
        String t = sc.nextLine().trim();
        Usuarios u = gestorUsuario.buscarUsuarioPorId(uid);
        if (u == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        Comics comicAAnular = null;
        for (Comics c : u.getComicsReservadosUsuario()) {
            if (c.getTitulo().equalsIgnoreCase(t)) {
                comicAAnular = c;
                break;
            }
        }
        if (comicAAnular == null) {
            System.out.println("No existe reserva para ese título en este usuario.");
            return;
        }
        // Quitar de reservados
        u.quitarReserva(comicAAnular);
        gestorComic.getTodosComicsReservados().remove(comicAAnular);
        // Marcar como disponible
        comicAAnular.setDisponible(true);
        // Actualizar CSV
        try {
            gestorCSV.actualizarLineaComic(comicAAnular);
        } catch (Exception ex) {
            System.err.println("Error al actualizar CSV: " + ex.getMessage());
        }
        // Eliminar la reserva del set de reservas
        Iterator<Reservas> it = reservas.iterator();
        while (it.hasNext()) {
            Reservas r = it.next();
            if (r.getUsuarioReservador().equals(u) && r.getTitulo().equalsIgnoreCase(t)) {
                it.remove();
                break;
            }
        }
        // Persistir cambios del usuario
        try {
            gestorTXT.actualizarLineaUsuario(u);
        } catch (Exception ex) {
            System.err.println("Error al actualizar TXT: " + ex.getMessage());
        }
        System.out.println("Reserva anulada correctamente.");
    }

    public Comics buscarComicPorTitulo(String titulo) {
        return gestorComic.buscarPorTitulo(titulo);
    }

    public void verCatalogo() {
        gestorComic.verCatalogo();
    }

    public void agregarNuevoComic(Comics nuevoComic) {
        if (nuevoComic == null) {
            System.out.println("Cómic inválido.");
            return;
        }
        // Validar duplicados por título
        if (gestorComic.buscarPorTitulo(nuevoComic.getTitulo()) != null) {
            System.out.println("Ya existe un cómic con ese título.");
            return;
        }
        gestorComic.listar().add(nuevoComic);
        try {
            gestorCSV.escribirComics(gestorComic.listar());
            System.out.println("Cómic agregado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al escribir CSV: " + e.getMessage());
        }
    }

    public void eliminarComicPorTitulo(String titulo) {
        Comics c = gestorComic.buscarPorTitulo(titulo);
        if (c == null) {
            System.out.println("Cómic no encontrado.");
            return;
        }
        // Liberar de reservas/compras globales
        gestorComic.liberarComic(c);
        gestorComic.listar().remove(c);
        try {
            gestorCSV.escribirComics(gestorComic.listar());
            System.out.println("Cómic eliminado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al escribir CSV: " + e.getMessage());
        }
    }
}
