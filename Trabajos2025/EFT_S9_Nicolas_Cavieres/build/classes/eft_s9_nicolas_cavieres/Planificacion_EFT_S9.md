



Inicio Programa
   |-> Saludo Inicial
   |-> inputConTimeout(): "¿Quieres saber de nuestras promociones?"
       |-> Iniciar TimerTask 20s -> default N
       |-> S -> mostrar promociones -> TimerTask 30s -> default N
       |-> N -> pasar a Menu Principal

Menu Principal
-> mostrar Menú Principal
-> opcion: "¿Que te gustaría hacer hoy?"
   |-> 1. Comprar entradas.
   |     |-> nombreCliente: "Cual es su nombre"
   |     |-> clienteID: (3 letras de su nombre) + (3 numeros random)
   |     |-> cantidadAComprar: "Cuandas entradas desea comprar"
   |     |   |-> Iniciar loop while para cada entrada
   |     |       |-> nombreEspectador[i]: "Ingrese el nombre del asistente nºi"
   |     |       |-> edadEspectador[i]: "Ingrese la edad de nombreEspectador[i]"
   |     |       |-> fechaDeNacimiento[i]: 
   |     |       |      |-> año[i]: "Ingrese el año de nacimiento de nombreEspectador[i]"
   |     |       |      |-> mes[i]: "Ingrese el mes de nacimiento de nombreEspectador[i]"
   |     |       |      |-> dia[i]: "Ingrese el dia de nacimiento de nombreEspectador[i]"
   |     |       |-> sexoEspectador[i]: "Ingrese el sexo de nombreEspectador[i]"
   |     |       |-> esEstudiante[i]: "¿nombreEspectador[i] se encuentra estudiando?"
   |     |       |-> Mostrar plano teatro (marcando los temporalmente ocupados)
   |     |       |-> asientoEspectador[i]: Seleccionar el asiento
   |     |       |-> totalParcialEspectador[i]: Determinar el precio del asiento
   |     |       |-> Mostrar resumen por entrada.
   |     |       |-> Confirmar Agregar a compra. S/N
   |     |   |-> Fin loop while
   |     |-> Mostral el resumen total de entradas y precio final.
   |     |-> Confirmar compra (S/N)
   |         |-> N
   |         |   |-> liberar asientos temporalmenteOcupados
   |         |   |-> Mensaje de despedida
   |         |   |-> Volver al Menu Principal
   |         |-> S
   |         |   |-> Actualizar asientos (temporalmenteOcupados -> asientosOcupados)
   |         |   |-> Imprimir boleta
   |         |   |-> Mensaje de despedida
   |         |   |-> Volver al Menu Principal
   |
   |-> 2. Modificar una venta
   |         |-> clienteID: "Ingrese el ID de su compra"
   |         |-> nombbreCliente: Ingrese el nombre del comprador
   |         |-> Validar los datos de la compra
   |             |-> Agregar Asientos
   |             |-> Quitar Asientos
   |             |-> Reimprimir mi compra
   |             |-> Cancelar mi compra
   |
   |-> 3. Ver asientos disponibles
   |         |-> Mostrar plano actualizado de asientos desocupados y ocupados
   |
   |-> 4. Ver Resumen de Tienda
   |     |-> Mensaje de saludo
   |     |-> Pedir contraseña de tienda (máximo 5 intentos)
   |            |-> opcion: "¿Que le gustaría revisar?
   |                |-> 1. Reimprimir todas las ventas
   |                |-> 2. Resumen del total de ventas
   |            
   |-> 5. Salir
   |     |-> Finalizar programa




======= ESTRUCTURA DE CLASES =======

+---------------------------+
|         MAIN              |
+---------------------------+
| + mostrarPromociones()    |
+---------------------------+
          

+----------------------------+
|   AsientosTeatro           |
+----------------------------+
| - planoTeatro: String[][]  |
| - asientosVendidos: Array  |
| - asientosTemporales: Array|
+----------------------------+
| + mostrarPlanoAsientos()   |
| + reservarAsientoTemporal()|
| + venderAsiento()          |
| + liberarAsiento()         |
| + normalizarAsiento()      |
+----------------------------+
           

+------------------------------+
|   ValidarInput               |
+------------------------------+
| - cantidadDeErrores: int     |
+------------------------------+
| + validarNumero()            |
| + validarLetra()             |
| + validarAsiento()           |
| + validarTexto()             |
| + leerInputConTimeout()      |
| + validarLetraConTimeout()   |
| + mensajeIntentosSuperados() |
+------------------------------+
         

+----------------------------------+
|   SaludosMoro                    |
+----------------------------------+
| + saludoInicial()                |
| + mensajeDespedida()             |
| + mostrarDescuentosDisponibles() |
| + mensajeBoletaGenerada()        |
| + mensajeSinAsientosDisponibles()|
+----------------------------------+
 

+-----------------------------------+
|   PreciosTeatro                   |  
+-----------------------------------+
| - PRECIO_VIP: int                 |
| - PRECIO_PALCO: int               |
| - PRECIO_PLATEA_BAJA: int         |
| - PRECIO_PLATEA_ALTA: int         |
| - PRECIO_GALERIA: int             |
| - DESCUENTO_NINOS: double         |
| - DESCUENTO_MUJERES: double       |
| - DESCUENTO_ESTUDIANTES: double   |
| - DESCUENTO_TERCERA_EDAD: double  |
| - descuentoAcumulado: double      |
+-----------------------------------+
| + obtenerPrecioBase()             |
| + calcularPrecioFinalAcumulativo()| 
+-----------------------------------+
  

+----------------------------------+
|   IngresarCliente                |
+----------------------------------+
| - nombreCliente: String          |
| - clienteID: String              |
| - nombreEspectador: String       |
| - anioNacimiento: int            |
| - mesNacimiento: int             |
| - diaNacimiento: int             |
| - sexoEspectador: String         |
| - esEstudianteEspectador: boolean|
+----------------------------------+
| + capturarDatosCliente()         |
| + capturarDatosEspectador()      |
| + validarEdadEspectador()        |
| + generarClienteID()             |
+----------------------------------+


+-------------------------+
|   IngresarVenta         |
+-------------------------+
| + realizarVenta()       |
| + procesarEspectador()  |
+-------------------------+
   

+-------------------------+
|   ModificarVenta        |
+-------------------------+
| - ventas: Array         |
+-------------------------+
| + registrarVenta()      |
| + modificarVenta()      |
| + imprimirVenta()       |
| + imprimirUltimaVenta() |
| + agregarAsientos()     |
| + quitarAsientos()      |
| + cancelarCompra()      |
+-------------------------+
|   Venta                 |
+-------------------------+
| + recalcularTotales()   |
| + reimprimirBoleta()    |
+-------------------------+
           

+-------------------------+
|   ResumenTienda         |
+-------------------------+
| - CONTRASENA: MORO2025  |
+-------------------------+
| + mostrarResumenTienda()|
+-------------------------+

