# Interlok Restful Services Testing

[![license](https://img.shields.io/github/license/interlok-testing/testing_restful.svg)](https://github.com/interlok-testing/testing_restful/blob/develop/LICENSE)
[![Actions Status](https://github.com/interlok-testing/testing_restful/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/interlok-testing/testing_restful/actions/workflows/gradle-build.yml)

Project tests;
 - interlok-apache-http
 - interlok-json
 - interlok-rest-base
 - interlok-rest-metrics-profiler
 - interlok-rest-metrics-jvm
 - interlok-rest-provider-prometheus

## What it does

The configuration will fire two http requests, one of which will fire every 30 seconds the other every 5 seconds.  Each http request will trigger another channel in the workflow, the first channel configured to always fail processing thereby returning a http server error response, the second always returning a 200 OK response.
The automated message generation is then profiled for message counts and performance, the statistics being made available to Prometheus to scrape and Grafana to display.  Finally, with each profiled metric being made available we will also be gathering many JVM metrics (memory, garbage collection, CPU usage etc) which we will also be making available for scraping.

![restful diagram](/restful.png "restful diagram")

## Getting started

First we'll build the Interlok runtime;

```shell
> gradle clean install
```
This will generate a full Interlok installation in the "./build/distribution" directory.

Next we'll launch Interlok, Prometheus and Grafana as docker containers;  

```
docker-compose up -d
```

## The endpoints

You can reach the Interlok dashboard here; [Interlok](http://localhost:8081/interlok/login.html)
You can reach the Prometheus dashboard here; [Prometheus](http://localhost:9090)
You can reach the Grafana dashboard here; [Grafana](http://localhost:3000) and both the metric displays here; [JVM](http://localhost:3000/d/K9kmttsGk/jvm?orgId=1&refresh=30s) and [Profiler](http://localhost:3000/d/XyHj4tsMk/interlok?orgId=1&refresh=30s)
