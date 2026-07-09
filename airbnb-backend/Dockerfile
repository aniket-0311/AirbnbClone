# ---------- Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Copy all files from the current directory to the container's working directory
COPY . .

RUN mvn clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jre

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /target/airbnb-clone-back-0.0.1-SNAPSHOT.jar airbnb.jar

EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "airbnb.jar"]
