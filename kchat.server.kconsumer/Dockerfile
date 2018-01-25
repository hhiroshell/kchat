FROM store/oracle/serverjre:8
MAINTAINER Hiroshi Hayakawa <hhiroshell@gmail.com>

ARG IMAGE_VERSION
ARG JAR_FILE

LABEL version=${IMAGE_VERSION}

ADD target/${JAR_FILE} /usr/share/kchat/kconsumer.jar

CMD ["/usr/bin/java", "-jar", "/usr/share/kchat/kconsumer.jar"]
