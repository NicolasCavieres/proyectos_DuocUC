========================================
1. DIAGRAMA DE FLUJO DEL MENÚ (Usuario)
========================================

Menu Principal
├── 1. Registrar Usuario
│   ├── Ingresar datos usuario
│   ├── Validar formato y duplicados
│   ├── Usuario registrado
│   └── Volver al Menú Principal
│
├── 2. Registrar Libro
│   ├── Ingresar datos libro
│   ├── Validar duplicados
│   ├── Libro registrado
│   └── Volver al Menú Principal
│
├── 3. Buscar Libro
│   ├── Ingresar nombre del libro
│   ├── Buscar libro en colección
│   ├── Mostrar resultados o mensaje de error
│   └── Volver al Menú Principal
│
├── 4. Pedir Libro
│   ├── Ingreso del Usuario (validar con nombre y rut)
│   ├── Buscar Libro (verificar disponibilidad)
│   ├── Confirmar Préstamo
│   ├── Registrar en CSV
│   └── Volver al Menú Principal
│
├── 5. Devolver Libro
│   ├── Ingreso del Usuario (validar con nombre y rut)
│   ├── Seleccionar Libro del Usuario a devolver
│   ├── Confirmar Devolución
│   ├── Registrar en CSV
│   └── Volver al Menú Principal
│
└── 6. Salir

========================================
2. DIAGRAMA DE JERARQUÍA DE ARCHIVOS
========================================

Biblioteca/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── duocuc/
│                   └── biblioteca/
│                       ├── Biblioteca.java
│
│                       ├── excepciones/
│                       │   └── ExtensionExcepciones.java
│
│                       ├── modelo/
│                       │   ├── Libro.java
│                       │   └── Usuario.java
│                       
│                       ├── servicio/
│                       │   ├── Busqueda.java
│                       │   └── Prestamo.java
│
│                       ├── util/
│                       │   └── GestorCSV.java
│
│                       └── visor/
│                           ├── InfoLibro.java
│                           ├── InfoUsuario.java
│                           └── Menu.java
│
├── README.txt



======================================================================
          TABLA DE CLASES Y COMPONENTES DEL SISTEMA DE BIBLIOTECA
======================================================================

| Componente           | Ubicación (Paquete) | Variables / Métodos Principales                                                                                                                                                                                          |
|----------------------|---------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ExtensionExcepciones | excepciones         | - Enum: TipoError { LIBRO_NO_ENCONTRADO, LIBRO_YA_PRESTADO, GENERAL }  
                                               - Constructores que reciben TipoError y mensaje (opcionalmente causa) 
                                               - Método: getTipo()                                                                                                                                      |
| Libro                | modelo              | - Atributos: nombre (String), autor (String), año (int), disponibilidad (boolean), ubicación (String)  
                                               - Métodos: Getters/Setters, registrar(), listar()                                                                                                        |
| Usuario              | modelo              | - Atributos: nombre (String), apellido (String), rut (String), teléfono (String), correo (String), <librosprestados> (ArrayList<Libro>)  
                                               - Métodos: Getters/Setters, registrar(), listar()                                                                                                        |
| Busqueda             | servicio            | - Métodos: buscarPorTitulo(ArrayList<Libro> libros, String titulo), buscarPorAutor(ArrayList<Libro> libros, String autor)                                                                             |
| Prestamo             | servicio            | - Métodos: registrarPrestamo(HashMap<String, Usuario> usuarios, ArrayList<Libro> libros, String rutUsuario, String nombreLibro), devolverPrestamo(...)                                               |
| GestorCSV            | util                | - Métodos: cargarUsuarios(String archivo) / guardarUsuarios(String archivo, HashMap<String, Usuario> usuarios)  
                                               - Métodos: leerDatos(), guardarDatos(),                                                        |
| InfoLibro            | visor               | - Método: mostrarInformacionLibro(Libro libro)                                                                                                                                                        |
| InfoUsuario          | visor               | - Método: mostrarInformacionUsuario(Usuario usuario)                                                                                                                                                  |
| Menu                 | visor               | - Métodos: mostrarMenu(), leerOpcion(), otros métodos de interacción  
                                               - Utiliza bloques try/catch para manejo de errores                                                                                                       |
| Biblioteca           | raíz                | - Atributos: HashMap<String, Usuario> usuarios; ArrayList<Libro> libros; listas auxiliares (librosDisponibles, librosOcupados)  
                                               - Métodos: main()                                                                                                          |

======================================================================
TABLA DE ERRORES DE INPUT POR FUNCIONALIDAD
========================================

REGISTRO USUARIO
---------------
RUT:
- Cadena vacía → "El RUT no puede estar vacío"
- Formato inválido → "Formato de RUT inválido. Use: 12345678-9"
- Letras no válidas → "El RUT solo debe contener números y guión"
- RUT duplicado → "Ya existe un usuario con ese RUT"

Nombre/Apellido:
- Cadena vacía → "El nombre/apellido no puede estar vacío"
- Contiene números → "El nombre/apellido no puede contener números"

Teléfono:
- Formato inválido → "Formato de teléfono inválido. Use: +56912345678"
- Contiene letras → "El teléfono solo debe contener números"

Email:
- Formato inválido → "Formato de email inválido"
- Sin @ → "El email debe contener @"

REGISTRO LIBRO
-------------
Nombre/Autor:
- Cadena vacía → "El nombre/autor no puede estar vacío"

Año:
- No numérico → "El año debe ser un número"
- Año futuro → "El año no puede ser mayor al actual"
- Año muy antiguo → "El año no puede ser menor a 1500"

BÚSQUEDA LIBRO
-------------
Título:
- Cadena vacía → "Ingrese un término de búsqueda"
- Muy corto → "El término debe tener al menos 3 caracteres"

PRÉSTAMO
--------
RUT:
- No existe → "No existe usuario con ese RUT"

Libro:
- No existe → "El libro no existe en la biblioteca"
- No disponible → "El libro no está disponible"

DEVOLUCIÓN
----------
RUT:
- No existe → "No existe usuario con ese RUT"
- Sin préstamos → "El usuario no tiene libros prestados"

Libro:
- No prestado → "El usuario no tiene ese libro prestado"

MENÚ
----
Opción:
- No numérico → "Debe ingresar un número"
- Fuera de rango → "Opción no válida. Elija entre 1

