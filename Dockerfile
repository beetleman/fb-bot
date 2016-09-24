FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/fb-boot.jar /fb-boot/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/fb-boot/app.jar"]
