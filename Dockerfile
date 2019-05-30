FROM openjdk:8-jdk-alpine
RUN apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/configserver
ADD build/libs/ConfigService-0.0.1-SNAPSHOT.jar /usr/local/configserver
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh

