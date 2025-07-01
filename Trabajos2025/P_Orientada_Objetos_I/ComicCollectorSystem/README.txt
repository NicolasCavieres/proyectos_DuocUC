** PLANIFICACION DEL PROGRAMA Y JERARQUIA DE ARCHIVOS

ComicCollectorSystem/
│
├── src/
│   └── com/
│       └── comiccollector/
│           ├── ComicApp.java
│           ├── control/
│           │   ├── TransaccionesComic.java
│           │   └── TransaccionesUsuario.java
│           ├── exceptions/
│           │   ├── ExtensionExcepciones.java
│           │   ├── TipoError.java
│           ├── interfaces/
│           │   ├── IMostrarInfoComic.java
│           │   └── IMostrarInfoUsuario.java
│           ├── io/
│           │   ├── GestorCSV.java
│           │   └── GestorTXT.java
│           ├── menu/
│           │   └── Menu.java
│           ├── model/
│           │   ├── Comics.java
│           │   ├── Usuarios.java
│           │   ├── Reservas.java
│           │   └── Compras.java
│           ├── manager/
│           │   ├── GestorComic.java
│           │   └── GestorUsuario.java
│           └── util/
│               └── Utils.java
│
├── data/
│   ├── comics.csv
│   └── usuarios.txt
│
├── lib/
│   └── (librerías externas si usas alguna)
│
├── build/
│
└── build.xml

** ESTRUCTURA DE DATOS

▶ Comics <ArrayList> {
    - String titulo
    - String autor
    - int numeroCopiasDisponibles
    - String genero            // Fantasia, Terror, Romance, etc.
    - int anio
    - boolean disponible       // true si hay stock
    - double precio
}

▶ Usuarios <HashMap> {
    - int idUsuario
    - String nombre
    - String apellido
    - int edad
    - String sexo              // M, F, otro
    - String rut
    - String direccion
    - List<Comics> comicsComprados          // lista con los titutlos
    - List<Comics> comicsReservados         // lista con los titutlos
}

▶ Compras <HashSet> {
    - int idCompra
    - LocalDate fecha
    - LocalTime hora
    - double monto
    - Usuarios usuarioComprador
    - List<Comics> librosComprados
}

▶ Reservas <TreeSet> {
    - LocalDate fecha
    - LocalTime hora
    - String titulo
    - String autor
    - Usuarios usuarioReservador
}

** ESTRUCTURA DE ARCHIVOS

▶ comics.csv
Encabezado:
titulo;autor;numero_copias;genero;anio;disponibilidad;precio

Ejemplo:
Batman: Year One;Frank Miller;10;Drama;1987;true;8500
Watchmen;Alan Moore;5;Ciencia ficción;1986;true;9900

▶ usuarios.txt
Formato de cada línea:
IDusuario|nombre|apellido|edad|sexo|rut|direccion|[comics_comprados]|[comics_reservados]

Ejemplo:
101|Ana|Torres|28|F|18.123.456-7|Av. Siempre Viva 742|[Batman: Year One, Watchmen]|[V de Vendetta]


+--------------------------------------------------+
| ComicApp                                         |
| Punto de entrada de la aplicación                |
+--------------------------------------------------+
| + main()                                         |
+--------------------------------------------------+

+--------------------------------------------------+
| TransaccionesComic                               |
| Opera ventas, reservas y devoluciones de cómics  |
+--------------------------------------------------+
| + comprarComic()                                 |
| + reservarComic()                                |
| + anularReservaComic()                           |
+--------------------------------------------------+

+--------------------------------------------------+
| GestorComic                                      |
| Gestiona lista de cómics en memoria              |
| listas: <comicsReservados> <comicsComprados> <lComics>|
+--------------------------------------------------+
| + cargar(List<Comics> lista)                     |
| + listar()                                       |
| + buscarPorTitulo()                              |
| + verCatalogo()                                  |
+--------------------------------------------------+

+--------------------------------------------------+
| Comics                                           |
| Manejo de datos de lista lComics y sus elementos  |
+--------------------------------------------------+
| + getTitulo() / setTitulo()                      |
| + getNumeroCopiasDisponibles()                   |
|   / setNumeroCopiasDisponibles(int)              |
| + estaDisponible()                               |
| + toString()                                     |
+--------------------------------------------------+

+--------------------------------------------------+
| TransaccionesUsuario                             |
| Crea, modifica y elimina usuarios                |
+--------------------------------------------------+
| + crearUsuario()                                 |
| + agregarUsuario()                               |
| + modificarUsuario()                             |
| + eliminarUsuario()                              |
+--------------------------------------------------+

+--------------------------------------------------+
| GestorUsuario                                    |
| listas: <lUsuarios>                              |
+--------------------------------------------------+
| + cargar(Map<Integer,Usuarios> map)              |
| + buscarUsuario()                                |
| + verReservasUsuario()                           |
| + verComprasUsuario()                            |
| + verListadoDeUsuario()                          |
+--------------------------------------------------+

+--------------------------------------------------+
| Usuarios                                         |
| Manejo de datos de lista lUsuarios y sus elementos|
+--------------------------------------------------+
| + getIdUsuario() / setIdUsuario()                |
| + asignarCompra()                                |
| + asignarReserva()                               |
| + toString()                                     |
+--------------------------------------------------+

+--------------------------------------------------+
| GestorCSV                                        |
| Lee/escribe cómics en CSV                        |
+--------------------------------------------------+
| + leerComics()                                   |
| + escribirComics()                               |
+--------------------------------------------------+

+--------------------------------------------------+
| GestorTXT                                        |
| Lee/escribe usuarios en TXT                      |
+--------------------------------------------------+
| + leerUsuarios()                                 |
| + escribirUsuarios()                             |
+--------------------------------------------------+

+--------------------------------------------------+
| Compras                                          |
| Registro único de ventas        |
+--------------------------------------------------+
| + getIdCompra() / setIdCompra(int)               |
| + getFecha(): LocalDate                          |
| + getHora(): LocalTime                           |
| + getMonto(): double                             |
| + equals(Object o)                               |
| + hashCode()                                     |
+--------------------------------------------------+

+--------------------------------------------------+
| Reservas                                         |
| Registro de reservas ord. por fecha y hora       |
+--------------------------------------------------+
| + getFecha(): LocalDate                          |
| + getHora(): LocalTime                           |
| + compareTo(Reservas o)                          |
| + equals(Object o)                               |
| + hashCode()                                     |
+--------------------------------------------------+

+--------------------------------------------------+
| IMostrarInfoComic                                |
| Formato/mostrar datos de comics                  |
+--------------------------------------------------+
| + mostrarInfo(Comics c)                          |
+--------------------------------------------------+

+--------------------------------------------------+
| IMostrarInfoUsuario                              |
| Formato/mostrar datos de usuarios                |                
+--------------------------------------------------+
| + mostrarInfo(Usuarios u)                        |
+--------------------------------------------------+

+--------------------------------------------------+
| ExtensionExcepciones                             |
| Excepción por extensión inválida                 |
+--------------------------------------------------+
| + ExtensionExcepciones(String mensaje)           |
+--------------------------------------------------+

+--------------------------------------------------+
| TipoError                                        |
| Catálogo de tipos de error                       |
+--------------------------------------------------+
| + IO_ERROR                                       |
| + FORMATO_INVALIDO                               |
| + NEGOCIO_VIOLADO                                |
| + OTRO                                           |
+--------------------------------------------------+

+--------------------------------------------------+
| Menu                                             |
| Mensajes de consola e interaccion con usuario    |
+--------------------------------------------------+
| + mostrar()                                      |
| + leerOpcion(): int                              |
| + ejecutarOpcion(int op)                         |
+--------------------------------------------------+

+--------------------------------------------------+
| Utils                                            |
| Métodos extras                                   |
+--------------------------------------------------+
| + generarIDUsuario()                             |
| + generarIDCompra()                              |
| + validarRut()                                   |
+--------------------------------------------------+
