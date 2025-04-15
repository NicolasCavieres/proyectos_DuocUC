/*
 * Plano del pseudocódigo a utilizar.
    Menú principal (main()) 
    |
    ├──> 1. Comprar entrada (ventaEntradas())
    │       |
    │       ├──> Solicita cantidad de entradas
    │       └──> Repite para cada entrada:
    │             ├──> mostrarPlanoTeatro()
    │             ├──> Solicita código de asiento
    │             ├──> Determina ubicación por código (VIP, Platea, General)
    │             ├──> Verifica si asiento está disponible
    │             ├──> Solicita edad del espectador (tipoCliente())
    │             ├──> Confirma que los datos estén correctos
    │             └──> calcularPrecioFinal()
    │       ├──> resumenCompra() → Despliega resumen con precio total
    │       └──> Registra entradas en planoTeatro
    │
    ├──> 2. Ver promociones (mostrarPromociones())
    │       └──> Muestra texto con descuentos por tipo de cliente y promociones por cantidad
    │
    ├──> 3. Buscar entrada (buscarEntrada())
    │       └──> Solicita tipo de búsqueda (número, ubicación o tipo de entrada)
    │             └──> Recorre planoTeatro y muestra coincidencias
    │
    ├──> 4. Eliminar entrada (eliminarEntrada())
    │       ├──> Solicita número de asiento, ubicación y tipo de entrada
    │       ├──> Verifica si existe
    │       ├──> Confirma los datos
    │       └──> Elimina entrada y actualiza estado a "libre" en planoTeatro
    │
    └──> 5. Salir

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package e4_s5_nicolascavieres;
import java.util.Scanner;
import java.time.LocalTime;


/**
 *
 * @author nikoc
 */
public class E4_S5_NicolasCavieres {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlanoTeatro teatro = new PlanoTeatro();

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== Menú Principal ===");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Ver promociones");
            System.out.println("3. Buscar entrada");
            System.out.println("4. Eliminar entrada");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción:\n-> ");

           int opcion = ValidarInputUsuario.validarNumero(scanner, "Seleccione una opción", 1, 5);


            switch (opcion) {
                case 1 -> BilleteraDeEntradas.ventaEntradas(teatro, scanner);
                case 2 -> {
                    VerPromociones.mostrar();  // Ejecutar Clase VerPromociones.java
                    System.out.println("\n¿Qué deseas hacer ahora?");
                    System.out.println("1. Volver al Menú Principal.");
                    System.out.println("2. Salir.");
                     // (Input Usuario, mensaje personalizado, numero más bajo de las opciones, numero más alto de las opciones)
                    int opcionPromo = ValidarInputUsuario.validarNumero(scanner,"Selecciona una opción:",1,2);
                    if (opcionPromo == 2) {
                        mensajeDeSalida();
                        continuar = false; // cierra el programa
                    } else {
                        break; // vuelve al menú principal
                    }
                }
                case 3 -> {
                    System.out.println("\n¿Cómo quieres realizar la búsqueda?");
                    System.out.println("1. Tengo el código del asiento (ej: V1, P3, G4)");
                    System.out.println("2. Buscar por Plano de Asientos (ocupados y disponibles)");
                    System.out.print("Seleccione una opción:\n-> ");
                    int subopcion = scanner.nextInt();
                    scanner.nextLine(); // limpiar buffer
                    switch (subopcion) {
                        case 1 -> {
                            System.out.print("Ingrese el código del asiento: ");
                            String codigo = scanner.nextLine().toUpperCase();
                            BuscarEntrada.buscarPorCodigo(codigo, teatro);
                        }
                        case 2 -> teatro.mostrarTeatro();
                        default -> System.out.println("Opción inválida.");
                    }
                }
                case 4 -> {
                    System.out.println("\nA continuación podrá ver el mapa del teatro.");
                    System.out.println("Los asientos con [X] están ocupados, mientras que los asientos con su respectivo código están disponibles.");
                    teatro.mostrarTeatro();
                    BilleteraDeEntradas.eliminarEntrada(teatro, scanner);
                }
                case 5 -> {
                    continuar = false;
                    mensajeDeSalida();
                }

                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
            // COMPRA ENTRADA
            // VER PROMOCIONES
            // BUSCAR ENTRADAS
            // ELIMINAR ENTRADA
            // SALIR
                    }
    }

    // Métodos auxiliares

    public static void mensajeDeSalida () {
        LocalTime ahora = LocalTime.now();
        int hora = ahora.getHour();
        String saludo;
        if (hora >= 6 && hora < 12) {
            saludo = "un buen día";
        } else if (hora >= 12 && hora < 20) {
            saludo = "una buena tarde";
        } else {
            saludo = "buenas noches";
        }
        System.out.println("\n    Gracias por visitar el Teatro Moro.");
        System.out.println("Que tengas " + saludo + ", y nos vemos pronto.");
        System.out.println("----------[Programa terminado]-------------");
    }
}
