FROM openjdk:17-alpine
#Основа 11 Java
ARG JAR_FILE=build/libs/cbrRate-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]