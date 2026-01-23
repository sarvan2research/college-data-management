# ---------- Build stage ----------
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# Copy project files
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build the Spring Boot jar
RUN ./mvnw clean package -DskipTests

# ---------- Run stage ----------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Railway exposes PORT dynamically
EXPOSE 8080

# Start Spring Boot
CMD ["java", "-jar", "app.jar"]
