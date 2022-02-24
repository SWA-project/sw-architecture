FROM gradle:7.4.0-jdk11-alpine AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME
  
COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src
    
RUN gradle build || return 0
COPY . .
RUN gradle clean build
    
FROM adoptopenjdk/openjdk11:alpine-jre
ENV ARTIFACT_NAME=order-service-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
EXPOSE 9192
ENV REQUEST_ORIGIN=http://localhost:3001

WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

ENTRYPOINT exec java -jar ${ARTIFACT_NAME}