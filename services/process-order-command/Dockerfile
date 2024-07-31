FROM gradle:jdk21-jammy AS build

WORKDIR /home/gradle/project

COPY build.gradle settings.gradle /home/gradle/project/
COPY src /home/gradle/project/src

RUN gradle build --no-daemon


FROM openjdk:24-jdk-slim

WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/*.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]