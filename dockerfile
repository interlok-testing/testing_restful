FROM adoptopenjdk/openjdk11
RUN apt-get -y -q update && \
 apt-get install --no-install-recommends -y curl bash unzip ca-certificates gosu && \
 groupadd -r interlok && \
 useradd -r -g interlok interlok && \
 rm -rf /var/lib/apt/lists/* && \
 mkdir -p /opt/interlok/logs && \
 gosu nobody true && \
 chown interlok:interlok /opt/interlok && \
 curl https://raw.githubusercontent.com/adaptris/docker-interlok-base/develop/scripts/gosu-docker-entrypoint.sh -o /docker-entrypoint.sh && \
 chmod +x /docker-entrypoint.sh
COPY --chown=interlok:interlok ./build/distribution /opt/interlok
WORKDIR /opt/interlok/
ENTRYPOINT ["/docker-entrypoint.sh"]