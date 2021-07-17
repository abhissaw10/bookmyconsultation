FROM openjdk:14-jdk-alpine
MAINTAINER upgrad
ADD ./target/user-service-0.0.1-SNAPSHOT.jar /opt/app/user-service.jar
WORKDIR /opt/app
ENV PATH="${PATH}:${JAVA_HOME}/bin"
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/opt/app/user-service.jar"]