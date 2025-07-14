/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package safevotesystem.io;

/**
 * Gestor de archivos de texto para la aplicación <strong>SafeVoteSystem</strong>.<br>
 * <p>
 * Su responsabilidad se centra en persistir en disco el mensaje cifrado junto a
 * la lista de números primos utilizados como claves. Se encarga, además, de
 * verificar si el archivo ya existe y solicitar confirmación antes de
 * sobrescribirlo, aplicando buenas prácticas de manejo de E/S y evitando fugas
 * de recursos.
 * </p>
 *
 * @author ariel
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class gestorTXT {

    /**
     * Crea (o sobrescribe) un archivo de texto con el mensaje cifrado y los números primos.
     *
     * @param nombreArchivoSinExtension nombre del archivo sin la extensión <code>.txt</code>.
     * @param mensaje                   texto resultante del proceso de cifrado.
     * @param codigos                   lista de números primos que actuaron como clave.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    public void escribirEnArchivo(String nombreArchivoSinExtension, String mensaje, List<Integer> codigos) throws IOException {
        // Ruta completa donde se almacenará el archivo dentro del proyecto
        String rutaCompleta = "src/archivos/" + nombreArchivoSinExtension + ".txt";
        File archivo = new File(rutaCompleta);

        // Si ya existe, preguntar si desea sobrescribir
        if (archivo.exists()) {
            try (Scanner sc = new Scanner(System.in)) {
                System.out.print("⚠️ El archivo ya existe. ¿Desea sobrescribirlo? (S/N): ");
                String respuesta = sc.nextLine();
                if (!respuesta.equalsIgnoreCase("S")) {
                    System.out.println("Escritura de archivo cancelada.");
                    return;
                }
            }
        }

        // Escribir archivo
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaCompleta))) {
            pw.println("MENSAJE ENCRIPTADO:");
            pw.println(mensaje);
            pw.println();
            pw.println("CÓDIGOS PRIMOS UTILIZADOS:");
            for (Integer codigo : codigos) {
                pw.println(codigo);
            }
        } catch (IOException e) {
            throw new IOException("Error al escribir el archivo: " + e.getMessage());
        }
    }
}