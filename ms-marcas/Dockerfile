# 1. Indicamos qué "computador virtual" queremos. Aquí usamos un Linux ultra ligero con Java 17.
FROM eclipse-temurin:21-jdk-alpine

# 2. Creamos una carpeta temporal
VOLUME /tmp

# 3. Copiamos el archivo .jar que acabas de compilar hacia dentro de la caja de Docker
COPY target/*.jar app.jar

# 4. Le decimos a Docker cómo encender la aplicación cuando la caja arranque
ENTRYPOINT ["java","-jar","/app.jar"]