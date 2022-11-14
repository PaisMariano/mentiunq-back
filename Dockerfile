FROM openjdk:8-jdk
MAINTAINER mentiunq
RUN adduser --system --group mentiunq
USER mentiunq:mentiunq
ADD target/mentiUnq-0.0.1-SNAPSHOT.jar mentiUnq-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/mentiUnq-0.0.1-SNAPSHOT.jar"]
