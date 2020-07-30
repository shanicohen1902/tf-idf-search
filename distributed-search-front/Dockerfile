FROM openjdk:14-jdk-alpine
COPY ./target/distributed-search-front-0.0.1-SNAPSHOT-jar-with-dependencies.jar /usr/app/distributed-search-front-0.0.1-SNAPSHOT-jar-with-dependencies.jar
WORKDIR /usr/app
EXPOSE 8087
CMD ["java","-jar","distributed-search-front-0.0.1-SNAPSHOT-jar-with-dependencies.jar","zookeeper:2181","8087"]