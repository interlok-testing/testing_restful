FROM azul/zulu-openjdk-alpine:11-jre

RUN apk add --no-cache --update ca-certificates bash curl unzip su-exec && \
    addgroup -S interlok && \
    adduser -S interlok -G interlok && \
    mkdir -p /opt/interlok && \
    chown interlok:interlok /opt/interlok && \
    curl https://raw.githubusercontent.com/adaptris/docker-interlok-base/develop/scripts/suexec-docker-entrypoint.sh -o /docker-entrypoint.sh && \
    chmod +x /docker-entrypoint.sh

COPY --chown=interlok:interlok ./build/distribution /opt/interlok

WORKDIR /opt/interlok/

ENTRYPOINT ["/docker-entrypoint.sh"]