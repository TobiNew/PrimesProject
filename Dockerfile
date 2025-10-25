FROM openjdk:17

WORKDIR /app

COPY ./target/primes-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "primes-0.0.1-SNAPSHOT.jar"]