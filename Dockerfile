FROM eclipse-temurin:21

WORKDIR /app

COPY build/libs/*-SNAPSHOT.jar app.jar

RUN addgroup -S bizware && adduser -S bizware -G bizware

RUN chown bizware:bizware /app

USER bizware:bizware

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]