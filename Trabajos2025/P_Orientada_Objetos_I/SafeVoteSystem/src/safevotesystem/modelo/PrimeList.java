/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package safevotesystem.modelo;

/**
 * Representa una lista especializada que solo admite números primos.<br>
 * Extiende {@link java.util.ArrayList} añadiendo verificación de primalidad y
 * sincronización para permitir su uso seguro desde múltiples hilos.
 *
 * @author ariel
 */
import java.util.ArrayList;

public class PrimeList extends ArrayList<Integer> {

    /**
     * Comprueba si un número es primo aplicando la prueba de divisibilidad hasta
     * su raíz cuadrada (<code>O(√n)</code>). Un número es primo si solo es
     * divisible por 1 y por sí mismo.
     *
     * @param n número a evaluar.
     * @return {@code true} si el número es primo; {@code false} en caso contrario.
     */
    public boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        int limite = (int) Math.sqrt(n);
        for (int i = 3; i <= limite; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * Añade un número a la lista siempre que sea primo. En caso contrario lanza
     * {@link IllegalArgumentException}. La operación está sincronizada para
     * permitir inserciones concurrentes.
     */
    @Override
    public synchronized boolean add(Integer number) {
        if (!isPrime(number)) {
            throw new IllegalArgumentException("Solo se permiten números primos.");
        }
        boolean agregado = super.add(number);
        notifyAll(); // Notifica a los hilos que esperan
        return agregado;
    }

    @Override
    public synchronized Integer remove(int index) {
        return super.remove(index);
    }

    /**
     * Devuelve de forma atómica la cantidad de números primos almacenados.
     */
    public synchronized int getPrimesCount() {
        return this.size();
    }

    /**
     * Hace que el hilo emisor espere hasta que la lista contenga al menos un
     * elemento. Útil cuando se utiliza como <em>topic</em> en entornos
     * productor-consumidor.
     */
    public synchronized void esperarSiVacia() {
        while (this.isEmpty()) {
            try {
                wait(); // Espera si no hay elementos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}