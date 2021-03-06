FROM maven:alpine

RUN apk --update add git
RUN adduser -D newsbot && mkdir /home/newsbot/.m2

ADD . /home/newsbot

RUN chown -R newsbot:newsbot /home/newsbot

USER newsbot
ENV HOME /home/newsbot
ENV MAVEN_CONFIG /home/newsbot/.m2

WORKDIR /home/newsbot

RUN mvn -e install

COPY src/main/resources/newsbot.properties /home/newsbot

CMD ["java","-jar","target/NewsBotIRC-0.2.1-SNAPSHOT.jar"]
