FROM openjdk:23-jdk-slim

WORKDIR /app

# Copiar el código fuente
COPY . .

# Construir el proyecto
RUN ./mvnw clean install -DskipTests

# Esperar 40 segundos antes de iniciar la aplicación
CMD ["sh", "-c", "sleep 40 && java -jar target/productProject-0.0.1-SNAPSHOT.jar"]