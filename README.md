# 🚀 Integración Continua con Jenkins y Docker

Este proyecto está configurado para ejecutar pruebas e2e utilizando Jenkins y Docker. A continuación, se detallan los pasos para ejecutar las pruebas e2e, los comandos necesarios y los requisitos del entorno.

## 🧪 Pruebas de End-to-End

Las pruebas e2e aseguran que los componentes de la aplicación funcionen correctamente en conjunto. En este proyecto, utilizamos Maven para gestionar y ejecutar las pruebas.

### Ejecución de Pruebas

Para ejecutar las pruebas e2e, utiliza el siguiente comando:

```sh
mvn clean verify
```

## 📦 Ejecución de Pruebas en Docker
```sh
docker run --rm -v $(pwd):/usr/src/app -w /usr/src/app -e BASE_URI=${BASE_URI} maven:3.8.8-eclipse-temurin-17-alpine mvn clean verify
```