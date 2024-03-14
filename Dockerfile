FROM openjdk:23-jdk-oraclelinux7
MAINTAINER Asif Bakht
COPY target/payment-0.0.1-SNAPSHOT.jar payment-method.jar
ENV CONTAINER_PORT=9998
EXPOSE $CONTAINER_PORT
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/payment-method.jar"]

