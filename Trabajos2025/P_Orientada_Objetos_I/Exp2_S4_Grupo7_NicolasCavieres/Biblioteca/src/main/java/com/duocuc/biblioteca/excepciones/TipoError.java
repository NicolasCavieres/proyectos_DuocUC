/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.duocuc.biblioteca.excepciones;

/**
 *
 * @author ariel
 */
public enum TipoError {
    FORMATO_INVALIDO,        // Para RUT, teléfono, email, etc.
    DATO_INVALIDO,           // Para validaciones generales
    DATO_DUPLICADO,          // Para usuarios o libros duplicados

    NO_ENCONTRADO,           // Para usuarios o libros no encontrados
    NO_DISPONIBLE,           // Para libros no disponibles

    ERROR_ARCHIVO,           // Para problemas de lectura/escritura
    ERROR_SISTEMA            // Para errores generales del sistema
}