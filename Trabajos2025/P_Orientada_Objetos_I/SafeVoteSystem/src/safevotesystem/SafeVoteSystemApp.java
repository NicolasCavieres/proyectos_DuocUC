/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package safevotesystem;

import safevotesystem.io.gestorCSS;
import safevotesystem.io.gestorTXT;
import safevotesystem.modelo.PrimeList;
import safevotesystem.tareas.PrimesThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Clase principal
 *
 * @author ariel
 */
public class SafeVoteSystemApp {

    public static void main(String[] args) throws InterruptedException {
        try (Scanner scanner = new Scanner(System.in)) {
            PrimeList topic = new PrimeList();
            List<Integer> numerosIngresados = solicitarNumeros(scanner);

            if (numerosIngresados.isEmpty()) {
                System.out.println("No se procesaron números. Finalizando el programa.");
                return;
            }

            procesarNumerosConThreads(numerosIngresados, topic);
            mostrarResultados(topic);
            guardarResultados(scanner, topic);

            System.out.println("\nGracias por usar SafeVoteSystem 🛡️.");
        }
    }

    /**
     * Solicita al usuario la fuente de los números (archivo CSV o generación hasta N)
     * y retorna la lista de números resultante.
     */
    private static List<Integer> solicitarNumeros(Scanner scanner) {
        List<Integer> numerosIngresados = new ArrayList<>();

        System.out.println("===========================================");
        System.out.println("   SafeVoteSystem - Verificador de Primos");
        System.out.println("===========================================\n");

        System.out.println("Seleccione el modo de entrada de datos:");
        System.out.println("1. Leer desde archivo CSV");
        System.out.println("2. Generar números desde 1 hasta un valor N");
        System.out.print("Ingrese su opción (1 o 2): ");
        String opcion = scanner.nextLine();

        switch (opcion) {
            case "1":
            System.out.print("Ingrese el nombre del archivo CSV (sin extensión): ");
            String nombreArchivo = scanner.nextLine();
            gestorCSS lector = new gestorCSS();
            try {
                numerosIngresados = lector.leerDesdeArchivo(nombreArchivo);
                    System.out.println("Archivo cargado con " + numerosIngresados.size() + " números.");
                } catch (IOException e) {
                    System.err.println("Error al leer el archivo: " + e.getMessage());
                }
                break;

            case "2":
                System.out.print("Ingrese el número N: ");
                try {
                    int n = Integer.parseInt(scanner.nextLine());
                    for (int i = 1; i <= n; i++) {
                        numerosIngresados.add(i);
                    }
                    System.out.println("Generados " + n + " números desde 1 hasta " + n + ".");
                } catch (NumberFormatException e) {
                    System.err.println("Entrada no válida. Debe ingresar un número entero.");
                }
                break;

            default:
                System.out.println("Opción inválida.");
        }
        return numerosIngresados;
    }

    /**
     * Procesa la lista de números usando hilos productores/consumidores para
     * identificar números primos y agregarlos a la lista compartida.
     */
    private static void procesarNumerosConThreads(List<Integer> numeros, PrimeList topic) throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        int numHilos = 2;

        // Productor
        for (int numero : numeros) {
            queue.put(numero);
        }
        // Señal de finalización
        for (int i = 0; i < numHilos; i++) {
            queue.put(-1);
        }

        // Consumidores
        List<Thread> hilos = new ArrayList<>();
        for (int i = 1; i <= numHilos; i++) {
            Thread hilo = new Thread(new PrimesThread(queue, topic, "Hilo-" + i));
            hilos.add(hilo);
            hilo.start();
        }

        // Esperar a que los hilos terminen
        for (Thread hilo : hilos) {
            hilo.join();
        }
    }

    /**
     * Muestra en consola la cantidad y la lista de números primos encontrados.
     */
    private static void mostrarResultados(PrimeList topic) {
        System.out.println("\n✅ Proceso completado.");
        System.out.println("🔢 Total de números primos encontrados: " + topic.getPrimesCount());
        System.out.println("📋 Lista de primos: " + topic);
    }

    /**
     * Pregunta al usuario si desea guardar los resultados y, de ser así, delega
     * la escritura a gestorTXT.
     */
    private static void guardarResultados(Scanner scanner, PrimeList topic) {
        System.out.print("\n¿Desea guardar los resultados en un archivo TXT? (S/N): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("S")) {
            System.out.print("Ingrese el mensaje encriptado que desea guardar: ");
            String mensaje = scanner.nextLine();
            
            System.out.print("Ingrese el nombre para el archivo de texto (sin puntos ni extensión): ");
            String nombreArchivo = scanner.nextLine();
            
            gestorTXT escritor = new gestorTXT();
            try {
                escritor.escribirEnArchivo(nombreArchivo, mensaje, topic);
                System.out.println("📁 Archivo 'src/archivos/" + nombreArchivo + ".txt' guardado exitosamente.");
            } catch (IOException e) {
                System.err.println("Error al guardar el archivo: " + e.getMessage());
            }
            
        }
    }
}