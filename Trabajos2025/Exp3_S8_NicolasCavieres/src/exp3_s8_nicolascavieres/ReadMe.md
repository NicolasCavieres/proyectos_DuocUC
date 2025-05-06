# Sistema de Gestión de Entradas de Teatro
Por Nicolás Cavieres

Este programa gestiona la compra, reserva, modificación y visualización de 
entradas para funciones teatrales. Los usuarios pueden acceder a promociones, 
visualizar disponibilidad, y realizar operaciones según el tipo de cliente.

En ésta nueva versión he actualizado y reestructurado las Clases anteriormente 
creadas, para que tengan un mejor orden acorde al diagrama del menú
y al flujo que tendría el usuario.

---
Menu Principal
-> Saludo Inicial -> Mostrar precios y descuentos
   |
   |-> 1. Comprar entradas  # CLASE COMPRARENTRADA
   |     |-> Pedir nombre del cliente 
   |     |-> Crear identificador del cliente 
   |     |-> Pedir cantidad de entradas a comprar 
   |     |-> Inicio loop while
   |         |-> Mostrar plano teatro actualizado
   |         |-> Seleccionar el asiento
   |         |-> Ingresar edad del espectador
   |         |-> Mostrar resumen por entrada.
   |     // Fin loop while
   |     |-> Mostral el resumen del total de entradas del loop while
   |     |-> ¿Desea reservar o comprar? 
   |         |-> 1. Reservar
   |            |-> Confirmar Reserva (S/N)
   |                |-> Actualizar variables en ModificarReserva
   |                |-> Iniciar TimerTask // pasados 10 minutos se borra la reserva
   |                |-> Mostrar resumen de la reserva 
   |         |-> 2. Comprar
   |            |-> Confirmar compra (S/N)
   |                |-> Actualizar variables en ModificarVenta
   |                |-> Imprimir boleta
   |
   |-> 2. Modificar una reserva # CLASE MODIFICARRESERVA
   |     |-> Pedir reservaID
   |            |-> Submenú para modificar venta
   |
   |-> 3. Modificar una venta # CLASE MODIFICARVENTA
   |     |-> Pedir ventaID
   |            |-> Submenú para modificar venta
   |
   |-> 4. Ver asientos disponibles # CLASE ASIENTOSTEATRO
   |     |-> Mostrar disponibilidad del Teatro
   |
   |-> 5. Ver Resumen de Tienda # CLASE RESUMENTIENDA
   |     |-> Submenú para ver Resumen de la Tienda.
   |
   |-> 6. Salir.


======= CLASES PRINCIPALES =======
* Éstas clases gestionan o participan directamente en alguna parte del Menú Principal.

+-------------------------+
|   MAIN                  |
+-------------------------+
| - teatro                |
| - ocupados              |
+-------------------------+
| + iniciar()             |
| + mostrarMenu()         |
+-------------------------+

+-------------------------------+
|   ComprarEntrada              |
+-------------------------------+
| - teatro                      |
| - reservas                    |
| - ventas                      |
+-------------------------------+
| + procesarCompra()            |   
| + seleccionarAsiento()        |
| + mostrarResumenParcial()     |
| + mostrarResumenFinal()       |
| + procesarTransaccion()       |
+-------------------------------+

+-------------------------------+
|  ModificarReserva             |
+-------------------------------+
| - listaReservas               |
| - asientosReservados          |
| - listaReservas               |
| - reservaID                   | 
+-------------------------------+
| + modificarReservaMenu()      |
| + agregarReserva()            |
| + modificarAsientosSubmenu()  |
| + agregarAsiento()            |
| + quitarAsiento()             |
| + recalcularTotal()           |
| + programarCancelacion()      |
| + cancelarReserva()           |
| + confirmarComoVenta()        |
| + imprimirResumenReserva()    |
| + cancelarTimer()             | 
| + buscarReservaPorID()        | 
+-------------------------------+

+-------------------------------+
|  ModificarVenta               |
+-------------------------------+
| - listaVentas                 |
| - asientosVendidos            |
| - ventaID                     |
+-------------------------------+
| + agregarVenta()              |
| + modificarVenta()            |
| + agregarAsientoAVenta()      |
| + qyutarAsientodeVenta()      |
| + recalcularTotal()           |
| + cancelarVenta()             |
| + imprimirBoleta()            |
| + buscarVentaPorID()          | 
+-------------------------------+

+-------------------------------+
|   AsientosTeatro              |
+-------------------------------+
| - planoTeatro                 |
| - asientosReservados          |
| - asientosVendidos            |
+-------------------------------+
| + mostrarTeatro()             |
| + ocuparAsiento()             |
| + ocuparAsientos()            |
| + marcarComoVendido()         |
| + liberarAsientosVendidos()   |
| + verificarDisponibilidad()   |
| + liberarAsiento()            |
| + liberarAsientos()           |
+-------------------------------+

+------------------------------------+
|         ResumenTienda              |
+------------------------------------+
| + reservas                         |
| + ventas                           |
| + totalReservados                  |
| + totalVendidos                    |
| + totalIngresos                    |
| + totalDescuentos                  |
+------------------------------------+
| + mostrarMenuResumen()             |
| + mostrarBoletasIndividuales()     |
| + mostrarResumenGeneral()          |
+------------------------------------+


======= CLASES SECUNDARIAS =======
* Éstas clases gestionan o participan en métodos contenidos dentro de otras Clases


+------------------------------+
|   SaludoInicial              |
+------------------------------+
| -                            |
+------------------------------+
| + mostrarSaludo()            |
+------------------------------+

+----------------------------------+
|   TipoCliente                    |
+----------------------------------+
| - edadCliente                    |
| - tipoCliente                    |
+----------------------------------+
| + determinarTipoCliente()        |
+----------------------------------+

+-----------------------------+
|   GeneradorID               |
+-----------------------------+
| -                           |
+-----------------------------+
| + generarID()               |
+-----------------------------+

+------------------------------+
|   ConfiguracionPrecios       |
+------------------------------+
| - listaPreciosBase           |
| - listaDescuentos            |
+------------------------------+
| + calcularPrecio()           |
| + validarLetra()             |
| + mensajeIntentosSuperados() |
+------------------------------+

+------------------------------+
|   ValidarInputUsuario        |
+------------------------------+
| - cantidadDeErrores          |
+------------------------------+
| + validarNumero()            |
| + validarLetra()             |
| + mensajeIntentosSuperados() |
+------------------------------+






