FROM eclipse-temurin:26-jdk

WORKDIR /app

COPY target/WebAppLogin-0.0.1-SNAPSHOT.jar backend.jar

ENTRYPOINT ["java", "-jar", "backend.jar"]