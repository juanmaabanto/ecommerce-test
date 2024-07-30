# ecommerce-test

Este es un sistema de comercio electrónico para propósitos de prueba.

## Índice
- [Instalación](#instalación)
- [Uso](#uso)
- [Base de Datos](#base-de-datos)

## Instalación

Para levantar la aplicación en el entorno local, asegúrate de tener instalados los siguientes requisitos:

- Docker
- Docker Compose
- Make

### Pasos para la instalación

1. Clona este repositorio:

   ```sh
   git clone https://github.com/juanmaabanto/ecommerce-test.git
   cd ecommerce-test
   ```

2. Ejecuta el makefile:

   ```sh
   make up
   ```

3. Verificar instalación:

![Docker Desktop](https://github.com/juanmaabanto/ecommerce-test/blob/main/screenshots/docker.png)

## Base de Datos

La aplicación utiliza MongoDB como base de datos. Aquí tienes una vista de las tablas:

### Acceso a Mongo DB

Puedes acceder a MongoDB usando las siguientes credenciales:

- **Host:** localhost
- **Puerto:** 27017
- **Usuario:** test
- **Contraseña:** testPWD
- **database:** ecommerce

![Tables](https://github.com/juanmaabanto/ecommerce-test/blob/main/screenshots/tables.png)