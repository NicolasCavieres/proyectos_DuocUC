# 🛡️ SafeVoteSystemApp - Verificador de Números Primos

## Descripción del proyecto

Este programa verifica si los numeros de una lista de números, provenientes de un archivo CSV o generados automáticamente desde cero hasta el límite superior ingresado por el usuario, son números primos o no. Utiliza dos hilos para procesar los números concurrentemente, y almacena los números primos en una lista, permitiendo exportarla junto con un mensaje encriptado en un archivo de texto.


## Estructura de carpetas esperada

SafeVoteSystem/
├── src/
│   ├── archivos/         
│   │   ├── test.csv      
│   │   └── resultados.txt 
│   ├── safevotesystem/
│   │   ├── SafeVoteSystemApp.java
│   │   ├── io/
│   │   │   ├── gestorCSS.java
│   │   │   └── gestorTXT.java
│   │   ├── modelo/
│   │   │   └── PrimeList.java
│   │   └── tareas/
│   │       └── PrimesThread.java


## 📝 Cómo usar archivos CSV

1. Crea un archivo de texto con extensión ".csv".
2. Escribe los números separados por coma. Ejemplo: 2,3,4,5,6
3. Guarda el archivo dentro de la carpeta "src/archivos/"
4. Al ejecutar el programa, cuando se te pregunte el nombre del archivo CSV, solo escribe el nombre sin ".csv". 
   Ejemplo: test. -> El programa abrirá: src/archivos/test.csv

## 📝 Cómo exportar el archivo txt

1. Cuando el programa pregunte el nombre que quieras dar al archivo, escribe solo el nombre.
   Ejemplo: resultados -> El programa creara: src/archivos/resultados.txt
2. Si ya existe un archivo con ese nombre, éste se sobreescribirá.


## Autor

**NICOLAS ARIEL** - Proyecto de evaluación Sumativa - Analista Programador.
