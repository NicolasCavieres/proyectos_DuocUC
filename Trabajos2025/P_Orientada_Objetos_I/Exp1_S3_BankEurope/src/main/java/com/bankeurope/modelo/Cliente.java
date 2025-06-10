/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bankeurope.modelo;

import com.bankeurope.vista.InfoCliente;

/**
 * Representa a un cliente del banco y sus datos personales y cuentas.
 * @author ariel
 */
public class Cliente implements InfoCliente {
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String comuna;
    private String telefono;
    private CuentaCorriente cuentaCorriente;
    private CuentaAhorro cuentaAhorro;
    private CuentaDigital cuentaDigital;

    /**
     * Constructor para crear un cliente con todos sus datos.
     */
    public Cliente(String rut, String nombre, String apellidoPaterno, String apellidoMaterno, String domicilio, String comuna, String telefono) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.domicilio = domicilio;
        this.comuna = comuna;
        this.telefono = telefono;
    }

    // Métodos getter para obtener los datos del cliente
    public String getRut() { return rut; }
    public String getNombre() { return nombre; }
    public String getApellidoPaterno() { return apellidoPaterno; }
    public String getApellidoMaterno() { return apellidoMaterno; }
    public String getDomicilio() { return domicilio; }
    public String getComuna() { return comuna; }
    public String getTelefono() { return telefono; }

    // Métodos getter para las cuentas
    public CuentaCorriente getCuentaCorriente() { return cuentaCorriente; }
    public CuentaAhorro getCuentaAhorro() { return cuentaAhorro; }
    public CuentaDigital getCuentaDigital() { return cuentaDigital; }

    // Métodos setter para asignar cuentas al cliente
    public void setCuentaCorriente(CuentaCorriente cuentaCorriente) { this.cuentaCorriente = cuentaCorriente; }
    public void setCuentaAhorro(CuentaAhorro cuentaAhorro) { this.cuentaAhorro = cuentaAhorro; }
    public void setCuentaDigital(CuentaDigital cuentaDigital) { this.cuentaDigital = cuentaDigital; }

    /**
     * Indica si el cliente tiene al menos una cuenta.
     */
    public boolean tieneCuentas() {
        return cuentaCorriente != null || cuentaAhorro != null || cuentaDigital != null;
    }

    /**
     * Muestra toda la información personal y cuentas del cliente.
     */
    @Override
    public void mostrarInformacionCliente() {
        System.out.println("RUT: " + rut);
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido Paterno: " + apellidoPaterno);
        System.out.println("Apellido Materno: " + apellidoMaterno);
        System.out.println("Domicilio: " + domicilio);
        System.out.println("Comuna: " + comuna);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Cuentas:");
        if (cuentaCorriente != null) cuentaCorriente.mostrarSaldoCuenta();
        if (cuentaAhorro != null) cuentaAhorro.mostrarSaldoCuenta();
        if (cuentaDigital != null) cuentaDigital.mostrarSaldoCuenta();
        if (cuentaCorriente == null && cuentaAhorro == null && cuentaDigital == null)
            System.out.println("Sin cuentas asociadas.");
    }
}
