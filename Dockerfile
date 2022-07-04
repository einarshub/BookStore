FROM openjdk:11
ADD target/BookStore.jar BookStore.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "BookStore.jar"]
