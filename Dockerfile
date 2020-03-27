FROM gradle:jdk11 AS BUILD
WORKDIR /material2
COPY . .
RUN gradle bootJar

FROM openjdk:11-jre
WORKDIR /material2
COPY --from=BUILD /material2/build/libs/*.jar material2.jar
COPY wait-for-it.sh .
RUN chmod +x wait-for-it.sh
CMD ["./wait-for-it.sh", "mysql:3306", "--timeout=30", "--", "java", "-jar", "material2.jar"]
#ENTRYPOINT ./wait-for-it.sh -t 10 mysql:3306 -- java -jar material2.jar
