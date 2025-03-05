package com.adaptris.restful.test;

import com.adaptris.testing.DockerComposeFunctionalTest;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

import java.io.File;
import java.net.InetSocketAddress;
import java.time.Duration;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class DefaultFunctionalTest extends DockerComposeFunctionalTest {
  protected static String INTERLOK_SERVICE_NAME = "interlok-1";
  protected static String PROMETHEUS_SERVICE_NAME = "prometheus-1";
  protected static String GRAFANA_SERVICE_NAME = "grafana-1";
  protected static int INTERLOK_PORT = 8080;
  protected static int PROMETHEUS_PORT = 9090;
  protected static int GRAFANA_PORT = 3000;
  protected static WaitStrategy defaultWaitStrategy = Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30));

  protected ComposeContainer setupContainers() {
      return new ComposeContainer(new File("docker-compose.yaml"))
        .withExposedService(INTERLOK_SERVICE_NAME, INTERLOK_PORT, defaultWaitStrategy)
        .withExposedService(PROMETHEUS_SERVICE_NAME, PROMETHEUS_PORT, defaultWaitStrategy)
        .withExposedService(GRAFANA_SERVICE_NAME, GRAFANA_PORT, defaultWaitStrategy);
  }

  protected String getPrometheusEndpoint(String path) {
    InetSocketAddress address = getHostAddressForService(PROMETHEUS_SERVICE_NAME, PROMETHEUS_PORT);
    if (!path.startsWith("/")) path = "/" + path;
    return "http://" + address.getHostString() + ":" + address.getPort() + path;
  }

  @Test
  public void test_startup() throws Exception{
    get(getPrometheusEndpoint("/api/v1/query?query=service_count_total"))
      .then().body("status", equalTo("success"));
  }


}
