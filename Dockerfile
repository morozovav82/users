FROM frolvlad/alpine-java:jdk8.202.08-slim
COPY target/users-1.5.jar /
CMD ["java", "-jar", "users-1.5.jar"]
