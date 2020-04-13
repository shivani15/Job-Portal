# build and test
FROM maven:3.5.3-jdk-8-alpine as builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /build/src/
RUN mvn package

# runnable image
FROM openjdk:8-jre-alpine
WORKDIR /root/
COPY --from=builder /build/target/jobportal-1.0-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./jobportal-1.0-SNAPSHOT.jar"]
