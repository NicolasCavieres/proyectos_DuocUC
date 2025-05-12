/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eft_s9_nicolas_cavieres;

/**
 *
 * @author nikoc
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IngresarVenta {
    /**
     * Gestiona la compra de entradas (opción 1 del menú principal).  
     * Captura los datos del cliente y, en un bucle, procesa cada espectador.
     * Al finalizar, muestra el resumen global de la venta, confirma la compra,
     * actuliza los asientos y llama a imprimir la boleta generada.
     *
     * @param scanner  Objeto Scanner para la entrada de datos.
     * @param asientos Objeto AsientosTeatro para gestionar la disponibilidad de asientos.
     */
    public void realizarVenta(Scanner scanner, AsientosTeatro asientos) {
        // Capturar datos del cliente (comprador)
        IngresarCliente clienteDatos = new IngresarCliente();
        clienteDatos.capturarDatosCliente(scanner);

        // Solicitar la cantidad de entradas a comprar
        int cantidadEntradas = ValidarInput.validarNumero(scanner, "¿Cuántas entradas desea comprar?", 1, 10);
        if (cantidadEntradas == -1) {
            return;
        }

        // Lista para almacenar el detalle de cada espectador
        List<String[]> listaEspectadores = new ArrayList<>();
        double totalCompra = 0;
        double totalDescuentos = 0;
        
        // Procesar cada entrada
        for (int i = 1; i <= cantidadEntradas; i++) {
            String[] detalle = procesarEspectador(scanner, i, asientos);
            if (detalle != null) {
                listaEspectadores.add(detalle);
                totalCompra += Double.parseDouble(detalle[6]);
                totalDescuentos += Double.parseDouble(detalle[7]);
            }
        }
        
        // Mostrar resumen global de la venta
        System.out.println("\n--- Resumen Total de Venta ---");
        System.out.println("Cliente: " + clienteDatos.getNombreCliente());
        System.out.println("Cliente ID: " + clienteDatos.getClienteID());
        String fechaCompra = LocalDate.now().toString();
        System.out.println("Fecha de compra: " + fechaCompra);
        System.out.println("Total Compra: $" + (int) totalCompra);
        System.out.println("Total Descuentos: $" + (int) totalDescuentos);
        
        System.out.println("\nDetalle de Espectadores:");
        int cont = 1;
        for (String[] detalle : listaEspectadores) {
            System.out.println("Espectador Nº " + cont + ":");
            System.out.println("  Nombre: " + detalle[0]);
            System.out.println("  Edad: " + detalle[1]);
            System.out.println("  Fecha de Nacimiento: " + detalle[2]);
            System.out.println("  Sexo: " + detalle[3]);
            System.out.println("  Estudiante: " + detalle[4]);
            System.out.println("  Asiento: " + detalle[5]);
            // Convertir los montos que vienen como String a double y luego a int
            System.out.println("  Precio Final: $" + (int) Double.parseDouble(detalle[6]));
            System.out.println("  Descuento: $" + (int) Double.parseDouble(detalle[7]));
            cont++;
        }
        
        // Confirmar la venta total
        String confirmarVenta = ValidarInput.validarLetra(scanner,
                "\n¿Confirma su compra? (S/N): ",
                new String[]{"S", "N"});        
        if (!confirmarVenta.equalsIgnoreCase("S")) {
            // Liberar todos los asientos reservados temporalmente si se cancela la venta
            for (String[] detalle : listaEspectadores) {
                asientos.liberarAsiento(detalle[5]);
            }
            System.out.println("Venta cancelada. Volviendo al Menú Principal...");
            return;
        }
        
        // Actualizar los asientos: pasar reservas temporales a asientos vendidos
        for (String[] detalle : listaEspectadores) {
            asientos.venderAsiento(detalle[5]);
        }
        
        // Preparar la cabecera de la venta y el detalle de los espectadores para la boleta
        String[] ventaHeader = {
            clienteDatos.getNombreCliente(),
            clienteDatos.getClienteID(),
            fechaCompra,
            String.valueOf(totalCompra),
            String.valueOf(totalDescuentos)
        };
        
        String[][] matrizEspectadores = new String[listaEspectadores.size()][];
        for (int i = 0; i < listaEspectadores.size(); i++) {
            matrizEspectadores[i] = listaEspectadores.get(i);
        }
        
        // Registrar la venta en la lista global
        ModificarVenta.registrarVenta(ventaHeader, matrizEspectadores);
        System.out.println("Venta registrada correctamente.");
        
        // Imprimir automáticamente la boleta de la venta confirmada
        ModificarVenta.imprimirUltimaVenta();
    }
    
    /**
     * Procesa la información de un espectador individual.
     * Captura sus datos, permite la elección de asiento (reservado temporalmente),
     * calcula el precio final aplicando el descuento (si corresponde) y retorna
     * un arreglo de strings con el detalle del espectador.
     * 
     * @param scanner  Objeto Scanner para la entrada de datos.
     * @param indice   Número de espectador en el proceso.
     * @param asientos Objeto AsientosTeatro para gestionar la selección del asiento.
     * @return Un arreglo con los detalles del espectador o null si los datos no fueron confirmados.
     */
    private String[] procesarEspectador(Scanner scanner, int indice, AsientosTeatro asientos) {
        System.out.println("\n--- Datos del Espectador Nº " + indice + " ---");
        IngresarCliente espectador = new IngresarCliente();
        espectador.capturarDatosEspectador(scanner, indice);
        
        // Verificar la validez de los datos capturados
        String nombreEsp = espectador.getNombreEspectador();
        if (nombreEsp == null || nombreEsp.isEmpty()) {
            return null;
        }
        int edadEsp = espectador.getEdadEspectador();
        int anioNac = espectador.getAnioNacimiento();
        int mesNac = espectador.getMesNacimiento();
        int diaNac = espectador.getDiaNacimiento();
        String fechaNacStr = diaNac + "-" + mesNac + "-" + anioNac;
        String sexoEsp = espectador.getSexoEspectador();
        boolean esEstudiante = espectador.isEsEstudianteEspectador();
        
        // Mostrar el plano actual del teatro (incluyendo asientos reservados temporalmente)
        asientos.mostrarPlanoAsientos(
            asientos.getPlanoTeatro(), 
            asientos.getAsientosVendidos(), 
            asientos.getAsientosTemporales()
        );
        
        // Solicitar el Asiento, con 5 intentos para validar.
        int intentosAsiento = 5;
        String asientoSeleccionado = null;
        boolean asientoValido = false;
        do {
            asientoSeleccionado = ValidarInput.validarAsiento(scanner, "Seleccione el asiento deseado:", asientos.getPlanoTeatro());
            asientoSeleccionado = AsientosTeatro.normalizarAsiento(asientoSeleccionado); // Forzar mayúsculas
            if (asientoSeleccionado == null) {
                System.out.println("Debe ingresar un código de asiento válido.");
            } else if (!asientos.reservarAsientoTemporal(asientoSeleccionado)) {
            } else {
                asientoValido = true;
                break;
            }
            intentosAsiento--;
        } while (!asientoValido && intentosAsiento > 0);
        if (!asientoValido) {
            System.out.println("No se pudo agregar al espectador " + nombreEsp + ", continuando con la compra.");
            return null;
        }
        
        // Obtener el precio base con la lógica centralizada en PreciosTeatro
        double precioBase = PreciosTeatro.obtenerPrecioBase(asientoSeleccionado);
        
        // Determinar los descuentos acumulados
        List<String> descuentosList = new ArrayList<>();
        if (edadEsp < 12) {
            descuentosList.add("niño");
        }
        if (edadEsp >= 65) {
            descuentosList.add("tercera edad");
        }
        if (sexoEsp.equalsIgnoreCase("M")) {
            descuentosList.add("mujer");
        }
        if (esEstudiante) {
            descuentosList.add("estudiante");
        }
        String[] descuentosAplicables = descuentosList.toArray(new String[0]);
        
        double totalParcial = PreciosTeatro.calcularPrecioFinalAcumulativo(descuentosAplicables, precioBase);
        double descuentoParcial = precioBase - totalParcial;
        
        // Mostrar resumen parcial para el espectador
        System.out.println("\nResumen de Espectador Nº " + indice + ":");
        System.out.println("  Nombre: " + nombreEsp);
        System.out.println("  Edad: " + edadEsp);
        System.out.println("  Fecha de Nacimiento: " + fechaNacStr);
        System.out.println("  Sexo: " + sexoEsp);
        System.out.println("  Estudiante: " + (esEstudiante ? "Sí" : "No"));
        System.out.println("  Asiento seleccionado: " + asientoSeleccionado);
        System.out.println("  Precio base: $" + (int) precioBase);
        System.out.println("  Descuento aplicado: " + (int)(descuentoParcial * 100 / precioBase) + "% (Monto: $" + (int)descuentoParcial + ")");
        System.out.println("  Precio final: $" + (int) totalParcial);
        
        // Confirmar la agregación de este espectador a la compra
        String confirmar = ValidarInput.validarLetra(scanner,
                    "¿Desea agregar este espectador a la compra? (S/N):",
                    new String[]{"S", "N"});
        if (!confirmar.equalsIgnoreCase("S")) {
            asientos.liberarAsiento(asientoSeleccionado);
            return null;
        }
        
        // Retornar el detalle del espectador en forma de arreglo de strings
        String[] espectadorDetalle = {
            nombreEsp,
            String.valueOf(edadEsp),
            fechaNacStr,
            sexoEsp,
            esEstudiante ? "Sí" : "No",
            asientoSeleccionado,
            String.valueOf(totalParcial),
            String.valueOf(descuentoParcial)
        };
        return espectadorDetalle;
    }
}