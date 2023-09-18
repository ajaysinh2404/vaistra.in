FROM maven:3.8.5-openjdk-17 As build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/master-0.0.1-SNAPSHOT.jar master.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","master.jar"]