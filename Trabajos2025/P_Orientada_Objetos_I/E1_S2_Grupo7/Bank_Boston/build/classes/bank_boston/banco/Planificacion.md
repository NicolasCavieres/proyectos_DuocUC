# Sistema de Gestión Clientes y Cuentas del Banco de Boston
Por Nicolás Cavieres

//

---
Menu Principal
-> Saludo Inicial -> Mostrar saludo del Banco

// 

   |
   |-> 1. Acceso Cliente. (nombre, apellido paterno y rut(sin puntos con guión))
   |     |-> Si existe y tiene cuentas:
   |     |   |-> Depósitos 
   |     |   |-> Giros 
   |     |   |-> Consultas de saldo
   |     |   |-> Visualizar datos personales
   |     |   |-> Salir
   |     |-> Si existe y no tiene cuentas -> Mostrar datos personales
   |
   |-> 2. Nuevo Cliente.
   |     |-> 1. Registrar cliente
   |           |-> ¿Desea cuenta corriente? [S/N] → Si S → Instanciar CuentaCorriente automáticamente
   |           |-> ¿Desea cuenta ahorro?    [S/N] → Si S → Instanciar CuentaAhorro automáticamente
   |           |-> ¿Desea cuenta crédito?   [S/N] → Si S → Instanciar CuentaCredito automáticamente
   |           |-> Agregar cliente a RegistroClientes
   |     |-> 2. Salir
   |
   |-> 3. Salir



======= DATOS =======

lista_Clientes -> Array {Cliente1, Cliente2, ...}
Cliente -> Array {RUT:, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, DOMICILIO, 
                COMUNA, TELEFONO, CUENTA_CORRIENTE, CUENTA_AHORRO, CUENTA_CREDITO}

Cuenta Corriente -> Array {numeroCorriente, saldoCorriente}
Cuenta Ahorro -> Array {numeroAhorro, saldoAhorro}
Cuenta Credito -> Array {numeroCredito, saldoCredito}


======= CLASES PRINCIPALES =======

+-------------------------+
|   Bank_Boston           |
+-------------------------+
| + opcion                |
+-------------------------+
| + mostrarMenu()         |
+-------------------------+


+-------------------------+
|   Validar               |
+-------------------------+
| +                       |
+-------------------------+
| + ValidarSaldo()        |  -> int positivo y != 0
| + validarAbono()        |  -> int positivo y != 0  
| + validarGiro()         |  -> int positivo y != 0
| + validarRUT()          |  -> cadena de 11 o 12 caractéres, donde hay punto y guión ej1: 11.111.111-1 ej2:1.111.111-1
| + validarTelefono()     |  -> cadena de 11 carácteres ej: 11111111111
| + validarCadena()       |  -> S o N / V o F
| + validarDireccion()    |  -> Que no esté vacío
+-------------------------+


+------------------------------------+
|   abstract class Cuentas           |   
+------------------------------------+
| + numeroCuenta                     |
| + saldo                            |
+------------------------------------+
| + Cuentas(numeroCuenta, saldo)     | 
| + getNumeroCuenta()                |  
| + getSaldo()                       |   
| + @Override transferir(double)     |  
| + @Override depositar(double)      |  
| + @Override girar(double)          |  
| + @Override mostrarTipoCuenta()    |  
+------------------------------------+

    +------------------------------------------------+
    |      class CuentaCorriente extends Cuentas     |
    +------------------------------------------------+
    | + CuentaCorriente(String numero, double saldo) | 
    | + @Override depositar(double monto)            |
    | + @Override girar(double monto)                |
    | + @Override mostrarTipoCuenta()                |
    +------------------------------------------------+

    +------------------------------------------------+
    |       class CuentaAhorro extends Cuentas       |
    +------------------------------------------------+
    | + CuentaAhorro(String numero, double saldo)    |
    | + @Override depositar(double monto)            |
    | + @Override girar(double monto)                |
    | + @Override mostrarTipoCuenta()                |
    +------------------------------------------------+

    +------------------------------------------------+
    |      class CuentaCredito extends Cuentas       |
    +------------------------------------------------+
    | + CuentaCredito(String numero, double saldo)   |
    | + @Override depositar(double monto)            |
    | + @Override girar(double monto)                |
    | + @Override mostrarTipoCuenta()                |
    +------------------------------------------------+


+------------------------------------------------------+
|   class Cliente implements IMostrable                |
+------------------------------------------------------+
| - rut: String                                        |
| - nombre: String                                     |
| - apellidoPaterno: String                            |
| - apellidoMaterno: String                            |
| - domicilio: String                                  |
| - comuna: String                                     |
| - telefono: String                                   |
| - cuentaCorriente: Cuentas                           |
| - cuentaAhorro: Cuentas                              |
| - cuentaCredito: Cuentas                             |
+------------------------------------------------------+
| + Cliente(...)                                       |
| + setCuentaCorriente(Cuentas)                        |
| + setCuentaAhorro(Cuentas)                           |
| + setCuentaCredito(Cuentas)                          |
| + mostrarCuentas()                                   |
| + @Override mostrarInformacion()                     |
+------------------------------------------------------+



======= Interfaz =======

+---------------------------------+
|     interface IMostrable        |
+---------------------------------+
| + mostrarInformacion(): void    |
+---------------------------------+