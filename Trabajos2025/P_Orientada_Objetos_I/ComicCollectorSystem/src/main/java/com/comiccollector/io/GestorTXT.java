/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.io;

/**
 *
 * @author ariel
 */
import com.comiccollector.modelo.Usuarios;
import com.comiccollector.excepciones.ExtensionExcepciones;
import com.comiccollector.excepciones.TipoError;
import com.comiccollector.modelo.Comics;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorTXT {
    private static final String RUTA = "data/usuarios.txt";

    public Map<Integer,Usuarios> leerUsuarios(List<Comics> listaComics) throws IOException, ExtensionExcepciones {
        if (!RUTA.endsWith(".txt"))
            throw new ExtensionExcepciones("Extensión inválida", TipoError.FORMATO_INVALIDO);
        Map<Integer,Usuarios> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            // Saltar encabezado
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                try {
                    String[] c = linea.split("\\|");
                    // Validar largo mínimo
                    if (c.length < 7) {
                        System.err.println("Línea de usuario ignorada (formato insuficiente): " + linea);
                        continue;
                    }

                    Usuarios u = new Usuarios(
                        Integer.parseInt(c[0].trim()),
                        c[1].trim(), c[2].trim(),
                        Integer.parseInt(c[3].trim()),
                        c[4].trim(), c[5].trim(), c[6].trim()
                    );

                    // Reconstruir listas de cómics comprados y reservados
                    if (c.length > 7) {
                        reconstruirListaComics(u, listaComics, c[7], true); // compras
                    }
                    if (c.length > 8) {
                        reconstruirListaComics(u, listaComics, c[8], false); // reservas
                    }

                    map.put(u.getIdUsuario(), u);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                    System.err.println("Línea de usuario ignorada (error de formato): " + linea);
                }
            }
        } catch (FileNotFoundException e) {
            throw new ExtensionExcepciones("Archivo no encontrado: " + RUTA, TipoError.ERROR_ARCHIVO);
        } catch (IOException e) {
            throw new ExtensionExcepciones("Error de IO al leer el archivo: " + e.getMessage(), TipoError.ERROR_ARCHIVO);
        }
        return map;
    }

    public void escribirUsuarios() throws IOException, ExtensionExcepciones {
        if (!RUTA.endsWith(".txt"))
            throw new ExtensionExcepciones("Extensión inválida", TipoError.FORMATO_INVALIDO);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA))) {
            // iterar gestorUsuario.lUsuarios y escribir líneas
        } catch (IOException e) {
            throw new ExtensionExcepciones("Error de IO al escribir el archivo: " + e.getMessage(), TipoError.ERROR_ARCHIVO);
        }
    }

    /**
     * Convierte el set de cómics a la representación usada en el archivo TXT.
     * Ejemplo: [Maus, Akira]
     */
    private String serializarComics(java.util.Set<com.comiccollector.modelo.Comics> comics) {
        if (comics == null || comics.isEmpty()) {
            return "[]";
        }
        String lista = comics.stream()
                .map(com.comiccollector.modelo.Comics::getTitulo)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
        return "[" + lista + "]";
    }

    public void escribirUsuarios(List<Usuarios> lista) throws IOException, ExtensionExcepciones {
        if (!RUTA.endsWith(".txt"))
            throw new ExtensionExcepciones("Extensión inválida", TipoError.FORMATO_INVALIDO);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA))) {
            // Escribir encabezado
            bw.write("id|nombre|apellido|edad|sexo|rut|direccion|compras|reservas");
            bw.newLine();
            for (Usuarios u : lista) {
                bw.write(String.format("%d|%s|%s|%d|%s|%s|%s|%s|%s",
                    u.getIdUsuario(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getEdad(),
                    u.getSexo(),
                    u.getRut(),
                    u.getDireccion(),
                    serializarComics(u.getComicsCompradosUsuario()),
                    serializarComics(u.getComicsReservadosUsuario())
                ));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ExtensionExcepciones("Error de IO al escribir el archivo: " + e.getMessage(), TipoError.ERROR_ARCHIVO);
        }
    }

    /**
     * Agrega un usuario como una nueva línea al archivo, sin sobrescribir el resto.
     */
    public void agregarUsuarioAlArchivo(Usuarios u) throws IOException, ExtensionExcepciones {
        if (!RUTA.endsWith(".txt"))
            throw new ExtensionExcepciones("Extensión inválida", TipoError.FORMATO_INVALIDO);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA, true))) { // true = append
            bw.write(String.format("%d|%s|%s|%d|%s|%s|%s|%s|%s",
                u.getIdUsuario(),
                u.getNombre(),
                u.getApellido(),
                u.getEdad(),
                u.getSexo(),
                u.getRut(),
                u.getDireccion(),
                serializarComics(u.getComicsCompradosUsuario()),
                serializarComics(u.getComicsReservadosUsuario())
            ));
            bw.newLine();
        } catch (IOException e) {
            throw new ExtensionExcepciones("Error de IO al escribir el archivo: " + e.getMessage(), TipoError.ERROR_ARCHIVO);
        }
    }

    /**
     * Actualiza la línea de un usuario en el archivo, manteniendo el resto igual.
     * Si el usuario no existe, no hace nada.
     */
    public void actualizarLineaUsuario(Usuarios usuarioActualizado) throws IOException, ExtensionExcepciones {
        if (!RUTA.endsWith(".txt"))
            throw new ExtensionExcepciones("Extensión inválida", TipoError.FORMATO_INVALIDO);
        File archivo = new File(RUTA);
        List<String> lineas = new java.util.ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes[0].equalsIgnoreCase("IDusuario") ||
                    !partes[0].equals(String.valueOf(usuarioActualizado.getIdUsuario()))) {
                    lineas.add(linea);
                } else {
                    // Reemplazar por la nueva línea del usuario actualizado
                    lineas.add(String.format("%d|%s|%s|%d|%s|%s|%s|%s|%s",
                        usuarioActualizado.getIdUsuario(),
                        usuarioActualizado.getNombre(),
                        usuarioActualizado.getApellido(),
                        usuarioActualizado.getEdad(),
                        usuarioActualizado.getSexo(),
                        usuarioActualizado.getRut(),
                        usuarioActualizado.getDireccion(),
                        serializarComics(usuarioActualizado.getComicsCompradosUsuario()),
                        serializarComics(usuarioActualizado.getComicsReservadosUsuario())
                    ));
                }
            }
        } catch (FileNotFoundException e) {
            throw new ExtensionExcepciones("Archivo no encontrado: " + RUTA, TipoError.ERROR_ARCHIVO);
        } catch (IOException e) {
            throw new ExtensionExcepciones("Error de IO al leer el archivo: " + e.getMessage(), TipoError.ERROR_ARCHIVO);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String l : lineas) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ExtensionExcepciones("Error de IO al escribir el archivo: " + e.getMessage(), TipoError.ERROR_ARCHIVO);
        }
    }

    private void reconstruirListaComics(Usuarios u, List<Comics> listaComics, String listaComicsStr, boolean esCompra) throws IOException, ExtensionExcepciones {
        if (listaComicsStr == null || listaComicsStr.isEmpty()) {
            return;
        }
        String cadena = listaComicsStr.trim();
        // Eliminar corchetes [ ] si existen
        if (cadena.startsWith("[")) {
            cadena = cadena.substring(1);
        }
        if (cadena.endsWith("]")) {
            cadena = cadena.substring(0, cadena.length() - 1);
        }
        if (cadena.isEmpty()) {
            return;
        }
        String[] titulos = cadena.split(",");
        for (String titulo : titulos) {
            Comics comic = listaComics.stream()
                .filter(cm -> cm.getTitulo().equalsIgnoreCase(titulo.trim()))
                .findFirst().orElse(null);
            if (comic != null) {
                if (esCompra) {
                    u.asignarCompra(comic);
                } else {
                    u.asignarReserva(comic);
                }
            }
        }
    }
}