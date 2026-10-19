# Project Multitasking

Proyecto Java desarrollado con Maven para estudiar y comparar distintas estrategias de procesamiento de datos, utilizando un conjunto de clientes almacenado en un archivo CSV.

## Funcionalidades

- **Procesamiento secuencial:** procesa los registros uno tras otro.
- **Procesamiento paralelo:** distribuye el procesamiento entre varias tareas para aprovechar múltiples núcleos del procesador.
- **Lectura de clientes:** carga los datos desde `customers.csv`.
- **Comparación de rendimiento:** permite analizar los tiempos de ejecución de ambas modalidades.

## Tecnologías

- Java
- Maven
- CSV para el almacenamiento de los datos de prueba

## Requisitos

- JDK compatible con la versión de Java configurada en `pom.xml`.
- Maven, o un IDE compatible con proyectos Maven.

## Configuración y ejecución

1. Clonar el repositorio.
2. Abrir el proyecto con un IDE compatible con Maven o desde la terminal.
3. Verificar que `customers.csv` se encuentre en la ruta esperada por el lector.
4. Ejecutar las pruebas:

   ```bash
   mvn clean verify
   ```

5. Ejecutar la aplicación mediante el mecanismo configurado en el proyecto.

## Datos de prueba

El archivo `customers.csv` contiene datos ficticios utilizados para evaluar el comportamiento del procesamiento secuencial y paralelo.

## Objetivo

Comprender las diferencias entre el procesamiento secuencial y paralelo, y evaluar cómo varía el rendimiento según la cantidad de registros y las características del entorno de ejecución.

## Versión

**0.1.0** — Primera entrega funcional de procesamiento secuencial y paralelo: Agrupar los clientes por país.
