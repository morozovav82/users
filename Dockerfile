FROM frolvlad/alpine-java:jdk8.202.08-slim
COPY target/users-2.0.jar /
CMD ["java", "-jar", "users-2.0.jar"]
