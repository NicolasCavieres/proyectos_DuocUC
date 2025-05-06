/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exp3_s8_nicolascavieres;

/**
 *
 * @author nikoc
 */
import java.util.Random;

public class GeneradorID {
    public static String generarID() {
        return String.valueOf(1000 + new Random().nextInt(9000)); // Genera un número aleatorio de 4 dígitos
    }
}
