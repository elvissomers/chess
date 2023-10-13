FROM maven:3.8.6-amazoncorretto-20 AS build

COPY pom.xml /app/
COPY src /app/src

WORKDIR /app/

RUN mvn clean package -DskipTests

FROM amazoncorretto:20
COPY --from=build /app/target/chess-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=main,dev","-jar","/app.jar"]