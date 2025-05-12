/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eft_s9_nicolas_cavieres;

/**
 *
 * @author nikoc
 */
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;

public class IngresarCliente {

    // Datos del cliente (comprador)
    private String nombreCliente;
    private String clienteID;

    // Datos del espectador
    private String nombreEspectador;
    private int edadEspectador;
    private int anioNacimiento;
    private int mesNacimiento;
    private int diaNacimiento;
    private String sexoEspectador;
    private boolean esEstudianteEspectador;

    /**
     * Captura el nombre del cliente y genera su ID.
     * 
     * @param scanner Objeto Scanner para entrada del usuario.
     */
    public void capturarDatosCliente(Scanner scanner) {
        System.out.print("¿Cuál es su nombre?\n-> ");
        nombreCliente = ValidarInput.validarTexto(scanner, "Ingrese su nombre:");
        if (nombreCliente == null) {
            System.out.println("No se ingresó un nombre válido. Volviendo al Menú Principal...");
            return;
        }        
        // Generar el clienteID a partir de las primeras 3 letras y un número aleatorio de 3 dígitos
        String tresLetras = nombreCliente.substring(0, Math.min(3, nombreCliente.length())).toUpperCase();
        int numeroRandom = (int) (Math.random() * 900) + 100; // Rango: 100-999.
        clienteID = tresLetras + numeroRandom;
    }

    /**
     * Captura la información de cada espectador.
     * 
     * @param scanner Objeto Scanner para entrada del usuario.
     * @param indice Número de la entrada actual en el proceso de compra.
     */
    public void capturarDatosEspectador(Scanner scanner, int indice) {
        System.out.println("\nEspectador Nº " + indice + ":");

        nombreEspectador = ValidarInput.validarTexto(scanner, "Ingrese el nombre del asistente Nº " + indice + ":");
        if (nombreEspectador == null) {
           System.out.println("No se ingresó un nombre válido. Volviendo al Menú Principal...");
            return;
        }
        
        edadEspectador = ValidarInput.validarNumero(scanner, "Ingrese la edad de " + nombreEspectador + ":", 0, 120);
        if (edadEspectador == -1) {
            System.out.println("Edad inválida. Volviendo al Menú Principal...");
            return;
        }
        
        System.out.println("Ingrese la fecha de nacimiento de " + nombreEspectador);
        anioNacimiento = ValidarInput.validarNumero(scanner, "Año de nacimiento:", 1900, Calendar.getInstance().get(Calendar.YEAR));
        if (anioNacimiento == -1) return;
        mesNacimiento = ValidarInput.validarNumero(scanner, "Mes de nacimiento (1-12):", 1, 12);
        if (mesNacimiento == -1) return;
        diaNacimiento = ValidarInput.validarNumero(scanner, "Día de nacimiento (1-31):", 1, 31);
        if (diaNacimiento == -1) return;

        int edadValida = validarEdadEspectador(scanner, anioNacimiento, mesNacimiento, diaNacimiento, edadEspectador);
        if (edadValida == -1) {
            // Se agotaron los intentos, se retorna al menú principal.
            return;
        }
        edadEspectador = edadValida;
        
        String sexoTemp = ValidarInput.validarLetra(scanner, 
                "Ingrese el sexo de " + nombreEspectador + " (H/M):", 
                new String[]{"H", "M"});
        if (sexoTemp == null) {
            System.out.println("No se ingresó un sexo válido. Volviendo al Menú Principal...");
            return;
        }        
        sexoEspectador = sexoTemp;
        
        String estudiaTemp = ValidarInput.validarLetra(scanner, 
                "¿" + nombreEspectador + " se encuentra estudiando? (S/N):", 
                new String[]{"S", "N"});
        if (estudiaTemp == null) {
            System.out.println("No se ingresó una respuesta válida para 'estudiando'. Volviendo al Menú Principal...");
            return;
        }        
        esEstudianteEspectador = estudiaTemp.equalsIgnoreCase("S");
        
        System.out.println("\nDatos ingresados correctamente para " + nombreEspectador + ".");
    }

    /**
     * Valida que la edad ingresada sea coherente con la fecha de nacimiento.
     * Permite hasta 5 intentos para corregir la edad.
     * 
     * @param scanner       Objeto Scanner para leer la entrada.
     * @param anio          Año de nacimiento.
     * @param mes           Mes de nacimiento (1-12).
     * @param dia           Día de nacimiento (1-31).
     * @param edadIngresada Edad inicialmente ingresada.
     * @return La edad corregida si es coherente o -1 si se agotan los intentos.
     */
    public int validarEdadEspectador(Scanner scanner, int anio, int mes, int dia, int edadIngresada) {
        Calendar fechaNacimiento = Calendar.getInstance();
        fechaNacimiento.set(anio, mes - 1, dia); // Los meses en Calendar son 0-indexados.
        Calendar fechaActual = Calendar.getInstance();
        int edadCalculada = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
        
        // Ajuste por meses y días
        if (fechaNacimiento.get(Calendar.MONTH) > fechaActual.get(Calendar.MONTH) ||
           (fechaNacimiento.get(Calendar.MONTH) == fechaActual.get(Calendar.MONTH) &&
            fechaNacimiento.get(Calendar.DAY_OF_MONTH) > fechaActual.get(Calendar.DAY_OF_MONTH))) {
            edadCalculada--;
        }
        
        int intentosRestantes = 5;
        while (edadCalculada != edadIngresada && intentosRestantes > 0) {
            System.out.println("\nError: La edad ingresada no coincide con la fecha de nacimiento.");
            System.out.println("Intentos restantes: " + intentosRestantes);
            edadIngresada = ValidarInput.validarNumero(scanner, "Ingrese nuevamente la edad correcta:", 0, 120);
            if (edadIngresada == -1) {
                System.out.println("\nHas superado el número de intentos permitidos.");
                System.out.println("Volviendo al Menú Principal...\n");
                return -1; // Condición para romper el loop
            }
            intentosRestantes--;
        }
        // Devuelve el valor corregido de la edad (que debe coincidir con la edad calculada)
        return edadIngresada;
    }

    /**
     * Genera un cliente ID basado en las primeras 3 letras del nombre y 3 números aleatorios.
     * 
     * @param nombre Nombre del cliente.
     * @return Cliente ID generado.
     */
    public static String generarClienteID(String nombre) {
        String letras = nombre.replaceAll("[^a-zA-Z]", "").substring(0, Math.min(3, nombre.length())).toUpperCase();
        int numeros = new Random().nextInt(900) + 100; // Genera un número aleatorio entre 100 y 999
        return letras + numeros;
    }

    // Getters para obtener información del cliente
    public String getNombreCliente() { return nombreCliente; }
    public String getClienteID() { return clienteID; }
    public String getNombreEspectador() { return nombreEspectador; }
    public int getEdadEspectador() { return edadEspectador; }
    public int getAnioNacimiento() { return anioNacimiento; }
    public int getMesNacimiento() { return mesNacimiento; }
    public int getDiaNacimiento() { return diaNacimiento; }
    public String getSexoEspectador() { return sexoEspectador; }
    public boolean isEsEstudianteEspectador() { return esEstudianteEspectador; }
}