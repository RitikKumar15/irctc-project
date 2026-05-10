FROM amazoncorretto:17-alpine-jdk
EXPOSE 9090
COPY target/irctc-project.jar irctc-project.jar
ENTRYPOINT ["java","-jar","irctc-project.jar"]