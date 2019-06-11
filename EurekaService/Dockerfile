FROM openjdk:8-jdk-alpine
RUN apk update && apk upgrade
RUN mkdir -p /usr/local/eurekaservice
ADD build/libs/EurekaService-0.0.1-SNAPSHOT.jar /usr/local/eurekaservice
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh