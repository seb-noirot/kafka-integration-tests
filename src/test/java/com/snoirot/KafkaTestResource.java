package com.snoirot;

import com.salesforce.kafka.test.junit5.SharedKafkaTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.Map;
import org.jboss.logging.Logger;

public class KafkaTestResource implements QuarkusTestResourceLifecycleManager {

  public static final Logger LOGGER = Logger.getLogger(KafkaTestResource.class);

  private SharedKafkaTestResource sharedKafkaTestResource;

  @Override
  public Map<String, String> start() {
    sharedKafkaTestResource = new SharedKafkaTestResource().withBrokers(3)
        .withBrokerProperty("auto.create.topics.enable", "true");

    try {
      sharedKafkaTestResource.beforeAll(null);
    } catch (Exception e) {
      LOGGER.error("Error starting kafka broker", e);
    }
    return Map.of( "kafka.bootstrap.servers", sharedKafkaTestResource.getKafkaConnectString());
  }

  @Override
  public void stop() {
    if (sharedKafkaTestResource != null) {
      sharedKafkaTestResource.afterAll(null);
    }
  }
}
