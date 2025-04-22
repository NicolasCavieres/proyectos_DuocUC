# Sistema de Gestión de Entradas de Teatro
Por Nicolás Cavieres

Este programa gestiona la compra, reserva, modificación y visualización de 
entradas para funciones teatrales. Los usuarios pueden acceder a promociones, 
visualizar disponibilidad, y realizar operaciones según el tipo de cliente.

---
Menu Principal
   |
   |-> 1. Comprar entradas
   |     |-> Ingresar nombre → nombreCliente
   |     |-> Ingresar cantidad de entradas a comprar → cantidadEntradasVenta
   |     // Inicio loop while
   |         |-> Mostrar plano teatro actualizado
   |         |-> Ingresar tipo de asiento [V, P, G] → tipoAsiento
   |         |-> Ingresar número(s) de asiento → numeroAsiento
   |         |-> Ingresar edad → tipoCliente / tipoDescuento
   |         |-> Mostrar total parcial a pagar → valorEntradaVenta
   |     // Fin loop while
   |     |-> ¿Desea reservar o comprar directo? 
   |         |-> 1. Reservar
   |            |-> Confirmar Reserva (S/N)
   |                |-> Guardar datos en listaReservas[i]
   |                |-> Iniciar TimerTask
   |                |-> Mostrar resumen de la reserva
   |         |-> 2. Comprar
   |            |-> Confirmar compra (S/N)
   |                |-> Guardar datos en listaVentas[i]
   |                |-> VariablesEstadisticas.totalReservasCompradas++
   |            |-> Imprimir boleta
   |
   |-> 2. Ver promociones
   |     |-> Mostrar precios y descuentos disponibles
   |     |-> Preguntar siguiente acción:
   |         |-> 1. Volver al menú principal
   |         |-> 2. Salir del sistema
   |
   |-> 3. Modificar una reserva
   |     |-> Ingresar código de reserva
   |     |-> Mostrar detalles actuales
   |     |-> ¿Desea cambiar cantidad/asientos?
   |     |-> Aplicar cambios → actualizar lista y total
   |     |-> Imprimir resumen actualizado
   |
   |-> 4. Modificar una venta
   |     |-> Ingresar código de venta
   |     |-> Mostrar detalles actuales
   |     |-> ¿Desea cambiar cantidad/asientos?
   |     |-> Aplicar cambios → actualizar lista y total
   |     |-> Imprimir boleta actualizada
   |
   |-> 5. Ver asientos disponibles
   |
   |-> 6. Salir del sistema
         |-> Llamar a VariablesEstadisticas.mostrarResumenFinal()
         |-> Terminar ejecución

---

+-------------------------+
|   PlanoTeatro           |
+-------------------------+
| - teatro                |
| - ocupados              |
+-------------------------+
| + mostrarTeatro()       |
| + ocuparAsiento()       |
| + liberarAsiento()      |
+-------------------------+

+----------------------------------+
|   TipoCliente                    |
+----------------------------------+
| - tipoCliente                    |
| - descuento                      |
| - edadCliente                    |
| - tipo                           |
+----------------------------------+
| + determinarTipoCliente()        |
| + determinarDescuento()          |
+----------------------------------+

+-----------------------------+
|      VerPromociones         |
+-----------------------------+
| - precioEntradaVIP          |
| - precioEntradaPlatea       |
| - precioEntradaGeneral      |
| - descuentoEstudiante       |
| - descuentoTerceraEdad      |
+-----------------------------+
| + mostrar()                 |
+-----------------------------+

+------------------------------+
|   ValidarInputUsuario        |
+------------------------------+
| - cantidadDeErrores          |
+------------------------------+
| + validarNumero()            |
| + validarLetra()             |
| + mensajeIntentosSuperados() |
+------------------------------+

+---------------------------+
|  EntradasReservadas       |
+---------------------------+
| - listaReservas           |
| - reservaID               |
+---------------------------+
| + agregarReserva()        |
| + buscarReserva()         |
| + confirmarCompra()       |
| + cancelarReserva()       |
| + imprimirReserva()       |
| + agregarAsiento()        |
| + quitarAsiento()         |
| + calcularTotalReserva()  |
| + mostrarResumenReserva() |
+---------------------------+

+---------------------------+
|  EntradasVendidas         |
+---------------------------+
| - listaVentas             |
| - ventaID                 |
+---------------------------+
| + registrarVenta()        |
| + buscarVenta()           |
| + imprimirBoleta()        |
| + agregarAsiento()        |
| + quitarAsiento()         |
| + cancelarVenta()         |
| + calcularTotalVenta()    |
+---------------------------+

+-------------------------------+
|   GestionarEntradas           |
+-------------------------------+
| - PRECIO_VIP                  |
| - PRECIO_PLATEA               |
| - PRECIO_GENERAL              |
+-------------------------------+
| + procesarVenta()             |
| + seleccionarAsiento()        |
| + calcularPrecioEntrada()     |
| + mostrarResumenParcial()     |
| + aplicarDescuentoCantidad()  |
| + mostrarResumenFinal()       |
| + procesarTransaccion()       |
+-------------------------------+

+------------------------------------+
|     VariablesEstadisticas          |
+------------------------------------+
| + cantidadDeInteracciones          |
| + totalReservasCompradas           |
| + totalVentasReservasCanceladas    |
+------------------------------------+
| + mostrarResumenFinal()            |
+------------------------------------+
