/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eft_s9_nicolas_cavieres;

/**
 *
 * @author nikoc
 */
public class SaludosMoro {

    public static void saludoInicial() {
        System.out.println(
            """
            =================== Bienvenido/a al Teatro Moro ===================
            
            A continuación serás dirigido al Menú Principal, doonde podrás
            revisar la disponibilidad de asientos, comprar entradas, modificar
            tu compra y obtener descuentos para nuestra función.
                                                                """);
        System.out.println(" - Secciones disponibles y precios:");
        System.out.println("- VIP:           $" + (int) PreciosTeatro.PRECIO_VIP);
        System.out.println("- Palco:         $" + (int) PreciosTeatro.PRECIO_PALCO);
        System.out.println("- Platea Baja:   $" + (int) PreciosTeatro.PRECIO_PLATEA_BAJA);
        System.out.println("- Platea Alta:   $" + (int) PreciosTeatro.PRECIO_PLATEA_ALTA);
        System.out.println("- Galería:       $" + (int) PreciosTeatro.PRECIO_GALERIA);
    }

    public static void mensajeDespedida() {
        System.out.println(
            """
            Gracias por usar nuestro sistema de ventas.
            Esperamos que disfrute del espectáculo. ¡Hasta pronto!
            """
        );
    }

    public static void mensajeErrorEntrada() {
        System.out.println(
            """
            ¡Entrada no válida!
            Por favor, asegúrate de ingresar una opción correcta.
            """
        );
    }

    public static void mostrarDescuentosDisponibles() {
        System.out.println("🎁 Descuentos aplicables (solo se aplica el mayor disponible):");
        System.out.println("- Niños (<12 años):     " + (int)(PreciosTeatro.DESCUENTO_NINOS * 100) + "%");
        System.out.println("- Mujeres:              " + (int)(PreciosTeatro.DESCUENTO_MUJERES * 100) + "%");
        System.out.println("- Estudiantes:          " + (int)(PreciosTeatro.DESCUENTO_ESTUDIANTES * 100) + "%");
        System.out.println("- Tercera Edad (65+):   " + (int)(PreciosTeatro.DESCUENTO_TERCERA_EDAD * 100) + "%");
    }

    public static void mensajeBoletaGenerada() {
        System.out.println(
            """
            ¡Boleta generada con éxito!
            Gracias por tu compra. Puedes revisar tu entrada más abajo.
            """
        );
    }

    public static void mensajeSinAsientosDisponibles() {
        System.out.println(
            """
            Lo sentimos, no quedan asientos disponibles por ahora.
            Por favor, vuelve más tarde.
            """
        );
    }
}