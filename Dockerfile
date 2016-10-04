FROM clojure:alpine

MAINTAINER Mateusz Probachta <mateusz.probachta@gmail.com>

RUN mkdir -p /app
ADD . /app

WORKDIR /app

EXPOSE 3000

RUN lein uberjar
CMD ["java", "-jar", "target/uberjar/fb-bot.jar"]
