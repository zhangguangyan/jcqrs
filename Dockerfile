
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS="-Dplay.http.secret.key=\"1234\""
ENV JAVA_OPTS=$JAVA_OPTS
COPY build/stage/playBinary /opt/jcqrs
EXPOSE 9000
CMD [ "/opt/jcqrs/bin/playBinary" ]