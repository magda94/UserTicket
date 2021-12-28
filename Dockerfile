FROM openjdk:11-jre-slim
RUN mkdir /app
EXPOSE 8080
COPY build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]