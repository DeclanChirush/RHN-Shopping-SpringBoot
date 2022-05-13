FROM openjdk:8-jdk-alpine

EXPOSE 8080

WORKDIR /app

COPY . .

RUN chmod 755 /app/mvnw

RUN ./mvnw dependency:go-offline -B

RUN ./mvnw package -DskipTests

RUN ls -al

ENTRYPOINT ["java","-jar","target/CTSE-Assigment3-User-Auth-0.0.1-SNAPSHOT.jar"]