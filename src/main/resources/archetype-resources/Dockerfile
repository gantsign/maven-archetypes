FROM openjdk:8-jre-alpine
ENTRYPOINT ["java", "-jar", "/usr/share/${artifactId}/${artifactId}.jar"]

ADD target/lib /usr/share/${artifactId}/lib
ADD target/${artifactId}.jar /usr/share/${artifactId}/${artifactId}.jar
