FROM maven:3.9.4-amazoncorretto-20 AS build

COPY pom.xml /app/
COPY src /app/src

WORKDIR /app/

RUN mvn clean package -DskipTests

FROM amazoncorretto:20
COPY --from=build /app/target/chess.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]