FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8085
WORKDIR /bot
COPY /target/bot-0.0.1-SNAPSHOT.jar searcher.jar
ENTRYPOINT ["java", "-jar", "searcher.jar"]