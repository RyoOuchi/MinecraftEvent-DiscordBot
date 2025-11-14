FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src ./src

RUN ./gradlew clean shadowJar -x test --no-daemon

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/build/libs/MinecraftBossFightBot-1.0.jar app.jar

ENV DISCORD_TOKEN=""

CMD ["java", "-jar", "app.jar"]