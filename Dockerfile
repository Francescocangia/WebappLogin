FROM eclipse-temurin:26-jdk

WORKDIR /app

COPY target/mio-backend.jar backend.jar

ENTRYPOINT ["java", "-jar", "backend.jar"]