#
# Build stage
#
FROM maven:3-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
MAINTAINER Petr Sheshenya <pyotr.sh@gmail.com>
COPY --from=build /home/app/target/spring-boot-features.jar /usr/share/service/app.jar
EXPOSE 8080
WORKDIR /usr/share/service/
ENTRYPOINT ["java", "-jar", "/usr/share/service/app.jar"]