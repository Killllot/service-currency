FROM openjdk:17-alpine
#Основа 11 Java
ARG JAR_FILE=build/libs/currencyRateClient.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]