/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exp3_s8_nicolascavieres;
/**
 *
 * @author nikoc
 */
public class SaludoInicial {
    public static void mostrarSaludo() {
        System.out.println("""
            \n============================================
                     Bienvenido al Teatro Moro ^o^
                Parte del plan de Cultura de Santiago.
            ==============================================
            
              Precios de entradas:
                - VIP:     $%d
                - Platea:  $%d
                - General: $%d
                        
              Descuentos disponibles:
                - Estudiantes:  %d%%
                - Tercera edad: %d%%
            
             Selecciona una opcion del menu para comenzar
            ==============================================
            """.formatted(
                (int) ConfiguracionPrecios.obtenerPrecioBase("VIP"),
                (int) ConfiguracionPrecios.obtenerPrecioBase("Platea"),
                (int) ConfiguracionPrecios.obtenerPrecioBase("General"),
                (int) (ConfiguracionPrecios.obtenerDescuento("Estudiante") * 100),
                (int) (ConfiguracionPrecios.obtenerDescuento("Tercera Edad") * 100)
            ));
    }
}

