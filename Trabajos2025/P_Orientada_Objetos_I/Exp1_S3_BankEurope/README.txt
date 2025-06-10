# Diagrama de Archivos y Paquetes


BankEurope/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── bankeurope/
│                   ├── bankeuropeapp/
│                   │   └── BankEuropeApp.java
│                   ├── controlador/
│                   │   ├── RegistrarCliente.java
│                   │   ├── RegistrarCuenta.java
│                   │   └── MovimientosCuenta.java
│                   ├── modelo/
│                   │   ├── Cliente.java
│                   │   ├── Clientes.java
│                   │   ├── CuentaBancaria.java
│                   │   ├── CuentaCorriente.java
│                   │   ├── CuentaAhorro.java
│                   │   └── CuentaDigital.java
│                   ├── utils/
│                   │   ├── Menus.java
│                   │   └── ValidarInput.java
│                   └── vista/
│                       ├── InfoCliente.java
│                       └── InfoCuenta.java
│
├── README.md
└── pom.xml

# Diagrama de Menús de Usuario

MENÚ PRINCIPAL
|
|-> 1. Acceso Cliente
|      |
|      |-> Solicita Nombre y RUT
|      |-> Si cliente existe y tiene cuentas:
|             |
|             |-> MENÚ CLIENTE
|                 |-> 1. Depósitos
|                 |      |-> Selección de cuenta
|                 |      |-> Ingreso de monto
|                 |-> 2. Giros
|                 |      |-> Selección de cuenta
|                 |      |-> Ingreso de monto
|                 |-> 3. Visualizar datos personales
|                 |      |-> Muestra todos los datos y saldos
|                 |-> 4. Salir (vuelve al menú principal)
|      |-> Si cliente no existe o no tiene cuentas:
|             |-> Mensaje informativo y vuelve al menú principal
|
|-> 2. Nuevo Cliente
|      |-> Solicita datos personales (RUT, nombre, apellidos, domicilio, comuna, teléfono)
|      |-> Pregunta por creación de cuentas (corriente, ahorro, digital)
|      |-> Crea cliente y cuentas seleccionadas
|      |-> Mensaje de éxito o error
|
|-> 3. Salir
       |-> Termina el programa

-------------------------------------------------
# Resumen de Clases y Funciones

[BankEuropeApp] (main)
  - main(String[] args)
  - Inicia la aplicación y el menú principal

[Menus]
  - menuPrincipal(Clientes)
  - menuAccesoCliente(Clientes)
  - elegirCuenta(Cliente): CuentaBancaria
  - Gestiona los menús y la interacción principal con el usuario

[ValidarInput]
  - validarNumero(Scanner, int, int): int
  - validarOpcionSN(Scanner): String
  - validarRut(String): boolean
  - validarCadena(Scanner): String
  - validarAbono(Scanner): int
  - validarSaldo(Scanner): boolean
  - validarGiro(Scanner, int): boolean
  - validarEsNumero(Scanner): boolean
  - validarTelefono(Scanner): String
  - Métodos utilitarios para validar entradas del usuario

[RegistrarCliente]
  - registrar(): Cliente
  - Solicita y valida los datos del cliente y sus cuentas

[RegistrarCuenta]
  - crearCuentaCorriente(Scanner): CuentaCorriente
  - crearCuentaAhorro(Scanner): CuentaAhorro
  - crearCuentaDigital(Scanner): CuentaDigital
  - generarNumeroCuenta(): int
  - Crea cuentas bancarias para el cliente

[MovimientosCuenta]
  - depositar(CuentaBancaria)
  - girar(CuentaBancaria)
  - Permite depósitos y giros en cuentas

[Cliente]
  - rut: String
  - nombre: String
  - apellidoPaterno: String
  - apellidoMaterno: String
  - domicilio: String
  - comuna: String
  - telefono: String
  - cuentaCorriente: CuentaCorriente
  - cuentaAhorro: CuentaAhorro
  - cuentaDigital: CuentaDigital
  - Métodos getter/setter
  - tieneCuentas(): boolean
  - mostrarInformacionCliente(): void

[Clientes]
  - lista: ArrayList<Cliente>
  - agregarCliente(Cliente)
  - buscarCliente(String, String): Cliente
  - getLista(): ArrayList<Cliente>
  - Almacena y gestiona todos los clientes

[CuentaBancaria] (abstract)
  - numeroCuenta: int
  - saldo: int
  - getNumeroCuenta(): int
  - getSaldo(): int
  - depositar(int)
  - girar(int): boolean
  - calcularInteres(int): int
  - getTipo(): String (abstract)
  - mostrarSaldoCuenta(): void
  - Clase base para cuentas

[CuentaCorriente] extends CuentaBancaria
  - getTipo(): String ("Corriente")

[CuentaAhorro] extends CuentaBancaria
  - getTipo(): String ("Ahorro")

[CuentaDigital] extends CuentaBancaria
  - getTipo(): String ("Digital")

[InfoCliente] (interface)
  - mostrarInformacionCliente()
  - Interfaz para mostrar información de cliente

[InfoCuenta] (interface)
  - mostrarSaldoCuenta()
  - Interfaz para mostrar saldo de cuenta