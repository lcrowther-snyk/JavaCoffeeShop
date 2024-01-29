FROM maven:3.9.6-amazoncorretto-21
RUN mkdir /usr/src/project
COPY . /usr/src/project
WORKDIR /usr/src/project
RUN mvn package -DskipTests
CMD mvn spring-boot:run
