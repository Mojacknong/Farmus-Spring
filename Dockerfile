FROM openjdk:17-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/farmus-spring-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} farmus-spring.jar

ENTRYPOINT ["java","-jar","/farmus-spring.jar"]