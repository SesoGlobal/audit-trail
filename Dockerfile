# Use official OpenJDK 17 slim image as base
FROM  openjdk:17.0.1-jdk-slim-buster


# label for the image
LABEL Description="Seso Audit Trail" Version="0.0.1"

# the version of the archive
ARG VERSION=0.0.1-SNAPSHOT

# Copy the packaged JAR file into the container
COPY target/audit-trail-${VERSION}.jar /app.jar


# Command to run the application
ENV JAVA_OPTS="-XX:+UseSerialGC  -Xss512k  -XX:MaxRAM=300m"
CMD java $JAVA_OPTS -jar app.jar