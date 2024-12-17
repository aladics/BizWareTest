FROM eclipse-temurin:21

WORKDIR /app

COPY build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
# ENTRYPOINT ["/bin/bash", "-c"] # Important: Use -c to execute a string
# CMD ["tail -f /dev/null"]