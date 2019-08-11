FROM azul/zulu-openjdk-alpine:11-jre
#FROM openjdk:11-jre-alpine
MAINTAINER Petr Sheshenya <pyotr.sh@gmail.com>

# Add the service itself
ARG VERSION
ARG JAR_FILE
COPY target/${JAR_FILE} /usr/share/service/app.jar
WORKDIR /usr/share/service/
# COPY target/*.jar /usr/share/service/app.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/service/app.jar"]


# label for the image
LABEL Description="Demo Service" Version="${VERSION}"
# https://spring.io/blog/2018/11/08/spring-boot-in-a-container