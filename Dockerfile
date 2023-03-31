FROM maven:3.9.0-amazoncorretto-11

WORKDIR /home

ADD pom.xml /home
RUN mvn verify clean --fail-never

COPY src /home/src

RUN mvn -v
RUN mvn -f /home/pom.xml -DoutputDirectory=/home/target/ clean package

EXPOSE 8080

ENTRYPOINT ["java","-jar","/home/target/bookstore.jar"]
