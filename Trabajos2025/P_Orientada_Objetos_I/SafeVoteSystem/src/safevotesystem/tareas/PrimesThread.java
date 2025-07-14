/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package safevotesystem.tareas;
/**
 * Representa un consumidor dentro del patrón Productor-Consumidor.<br>
 * Extrae números de la {@link java.util.concurrent.BlockingQueue}, determina si
 * son primos y los publica en la {@link safevotesystem.modelo.PrimeList}
 * compartida.
 *
 * Su objetivo es paralelizar el proceso de verificación de números primos para
 * mejorar el rendimiento de la aplicación.
 *
 * @author ariel
 */
import safevotesystem.modelo.PrimeList;
import java.util.concurrent.BlockingQueue;

public class PrimesThread implements Runnable {

    private final BlockingQueue<Integer> queue; // Queue compartida
    private final PrimeList topic;              // Topic: lista compartida
    private final String nombre;

    /**
     * Crea un nuevo hilo consumidor.
     *
     * @param queue  cola desde la que se consumen los números.
     * @param topic  lista compartida donde se publican los primos hallados.
     * @param nombre identificador legible usado en los logs.
     */
    public PrimesThread(BlockingQueue<Integer> queue, PrimeList topic, String nombre) {
        this.queue = queue;
        this.topic = topic;
        this.nombre = nombre;
    }

    /**
     * Ciclo principal de consumo: toma números de la cola, verifica su
     * primalidad y, si corresponde, los añade al <em>topic</em>.
     * La señal de terminación se detecta cuando se recibe el valor <code>-1</code>.
     */
    @Override
    public void run() {
        try {
            while (true) {
                Integer numero = queue.take(); // bloquea hasta que hay un número

                if (numero == -1) break; // señal de finalización

                synchronized (topic) {
                    if (topic.isPrime(numero)) {
                        topic.add(numero);
                        System.out.println("[" + nombre + "] Publicó primo: " + numero);
                    } else {
                        System.out.println("[" + nombre + "] No es primo: " + numero);
                    }
                }

                Thread.sleep(50); // simula procesamiento
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}