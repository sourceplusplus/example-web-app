FROM openjdk:17-alpine

ADD ./build/distributions/example-jvm-1.0-SNAPSHOT.zip /example-jvm-1.0-SNAPSHOT.zip
RUN unzip *.zip

ADD ./spp-probe.yml /example-jvm-1.0-SNAPSHOT/agent-libs/spp-probe.yml

CMD ["./example-jvm-1.0-SNAPSHOT/bin/example-jvm"]
