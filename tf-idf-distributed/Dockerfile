FROM openjdk:14-jdk-alpine
COPY ./build/libs/tf-idf-distributed-0.0.1-SNAPSHOT.jar /usr/app/tf-idf-distributed-0.0.1-SNAPSHOT.jar
WORKDIR /usr/app
COPY resources ./resources/
EXPOSE 8080
CMD ["java","-jar","tf-idf-distributed-0.0.1-SNAPSHOT.jar","--spring.cloud.zookeeper.connectString=zoo-service.default"]