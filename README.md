# ecommerce-test

Este es un sistema de comercio electrónico para propósitos de prueba. Tiene 3 servicios, customer-query y product-query que exponen API para obtener información, y un worker suscrito a un tópico kafka para procesar una orden.

## Índice
- [Instalación](#instalación)
- [Base de Datos](#base-de-datos)
- [Uso](#uso)

## Instalación

Para levantar la aplicación en el entorno local, asegúrate de tener instalados los siguientes requisitos:

- Linux ó MacOS
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

La aplicación utiliza MongoDB como base de datos.

### Acceso a Mongo DB

Puedes acceder a MongoDB usando las siguientes credenciales:

- **Host:** localhost
- **Puerto:** 27017
- **Usuario:** test
- **Contraseña:** testPWD
- **database:** ecommerce

![Tables](https://github.com/juanmaabanto/ecommerce-test/blob/main/screenshots/tables.png)

## USO

Una vez los contenedores se encuentren corriendo, en la misma raíz del repositorio ejecutar otro comando make para que se publique un mensaje en el topico

```sh
   make producer
   ```
