/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.comiccollector.excepciones;

/**
 *
 * @author ariel
 */
public enum TipoError {
    ERROR_ARCHIVO,           // Fallos al guardar/leer de archivos
    ERROR_SISTEMA,           // Errores inesperados del sistema (NullPointer, etc.)
    FORMATO_INVALIDO,        // RUT, edad, formato incorrecto
    SIN_EXISTENCIAS,         // Búsquedas sin resultados (ID/RUT/nombre)
    OPCION_NO_DISPONIBLE,    // Opciones de menú inválidas
    VALIDACION_FALLIDA,      // Lógica de negocio incumplida
    OPERACION_NO_PERMITIDA,  // Acceso no autorizado
    DATOS_INCOMPLETOS,       // Campos obligatorios vacíos
    CONEXION_FALLIDA,        // Fallos de red o base de datos
    DUPLICADO,               // Intento de crear un registro que ya existe
    TIEMPO_LIMITE,           // Timeout en operaciones largas
    RECURSO_BLOQUEADO,       // Archivo, registro o recurso en uso
    NO_IMPLEMENTADO          // Funcionalidad aún no implementada
}